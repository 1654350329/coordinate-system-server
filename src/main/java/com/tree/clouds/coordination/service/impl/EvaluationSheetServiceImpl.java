package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.mapper.EvaluationSheetDetailMapper;
import com.tree.clouds.coordination.mapper.EvaluationSheetMapper;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.entity.EvaluationSheet;
import com.tree.clouds.coordination.model.entity.EvaluationSheetDetail;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.model.vo.*;
import com.tree.clouds.coordination.service.*;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import com.tree.clouds.coordination.utils.QiniuUtil;
import com.tree.clouds.coordination.utils.SmsUtil;
import com.tree.clouds.coordination.utils.Word2PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * 评估表 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class EvaluationSheetServiceImpl extends ServiceImpl<EvaluationSheetMapper, EvaluationSheet> implements EvaluationSheetService {

    @Autowired
    private WritingBatchService writingBatchService;

    @Autowired
    private DataReportService dataReportService;

    @Autowired
    private UserManageService userManageService;

    @Autowired
    private EvaluationSheetDetailMapper evaluationSheetDetailMapper;

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private QiniuUtil qiniuUtil;
    @Autowired
    private SmsUtil smsUtil;

    @Override
    @Transient
    public void addEvaluationSheet(List<String> ids) {
        //修改资料上报任务状态
        List<DataReport> dataReports = dataReportService.listByIds(ids);
        check(dataReports);
        dataReportService.updateDataExamine(ids, DataReport.EXAMINE_PROGRESS_EIGHT, null);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String m = month < 10 ? "0" + month : String.valueOf(month);
        String numberValue = this.baseMapper.getNumber(year, month, dataReports.get(0).getSort());
        int number = (numberValue == null ? 0 : Integer.parseInt(numberValue));
        String writingBatchId = year + "" + m + "" + (dataReports.get(0).getSort() == 0 ? "工" : "病") + "-" + (number + 1);
        EvaluationSheet evaluationSheet = new EvaluationSheet();
        evaluationSheet.setYear(year);
        evaluationSheet.setSort(dataReports.get(0).getSort());
        evaluationSheet.setMonth(month);
        evaluationSheet.setNumber(number + 1);
        evaluationSheet.setWritingBatchId(writingBatchId);
        evaluationSheet.setEvaluationNumber(ids.size());
        List<String> times = dataReports.stream().map(DataReport::getCreatedTime).sorted().collect(Collectors.toList());
        evaluationSheet.setCycleTime(DateUtil.format(DateUtil.parse(times.get(0)), "YYYY-MM-dd") + " -- " + DateUtil.format(DateUtil.parse(times.get(times.size() - 1)), "YYYY-MM-dd"));
        this.baseMapper.insert(evaluationSheet);
        this.writingBatchService.saveBatchInfo(writingBatchId, ids);
    }

    @Override
    public void joinEvaluationSheet(String writingBatchId, List<String> ids) {
        List<DataReport> dataReports = dataReportService.listByIds(ids);
        //判断是否符合加入待审表
        check(dataReports);
        int sort = writingBatchId.contains("工") ? 0 : 1;
        if (sort != dataReports.get(0).getSort()) {
            throw new BaseBusinessException(500, "与现在加入的工伤病类型必须一致");
        }
        dataReportService.updateDataExamine(ids, DataReport.EXAMINE_PROGRESS_EIGHT, null);
        EvaluationSheet evaluationSheet = this.getByWritingBatchId(writingBatchId);
        evaluationSheet.setEvaluationNumber(evaluationSheet.getEvaluationNumber() + ids.size());
        this.writingBatchService.saveBatchInfo(writingBatchId, ids);
        this.appraiseService.saveAppraise(ids, writingBatchId);
    }

    private void check(List<DataReport> dataReports) {
        for (DataReport dataReport : dataReports) {
            if (!dataReports.get(0).getSort().equals(dataReport.getSort())) {
                throw new BaseBusinessException(500, "创建待审类型必须一致");
            }
            if (writingBatchService.getEvaluationSheetStatus(dataReport.getReportId())) {
                throw new BaseBusinessException(500, "该上报任务已加入待审表,不能重复加入!");
            }
        }
    }

    @Override
    public List<String> writingBatch() {
        return this.baseMapper.writingBatch();
    }

    @Override
    public IPage<DataReportBO> reportDetailPage(WritingBatchVO writingBatchVO) {
        return dataReportService.dataReportPage(writingBatchVO);
    }


    @Override
    public IPage<EvaluationSheet> evaluationSheetPage(EvaluationSheetPageVO evaluationSheetPageVO) {
        IPage<EvaluationSheet> page = evaluationSheetPageVO.getPage();
        page = this.baseMapper.evaluationSheetPage(page, evaluationSheetPageVO);
        return page;
    }

    @Override
    public EvaluationSheet evaluationSheetDetail(String id) {
        EvaluationSheet evaluationSheet = this.getById(id);
        evaluationSheet.setCreatedUser(this.userManageService.getById(evaluationSheet.getCreatedUser()).getUserName());
        return evaluationSheet;
    }

    @Override
    public IPage<ExpertDetailVO> expertDetailPage(WritingBatchVO writingBatchVO) {
        return this.evaluationSheetDetailMapper.expertDetailPage(writingBatchVO.getPage(), writingBatchVO);
    }

    @Override
    public void updateExpertUsers(List<String> ids, String writingBatchId) {
        EvaluationSheet evaluationSheet = this.getByWritingBatchId(writingBatchId);
        evaluationSheet.setExpertUsers(String.join(",", ids));
        this.updateById(evaluationSheet);
    }

    @Override
    @Transactional
    public void draw(DrawVO drawVO) {
        EvaluationSheet evaluationSheet = this.getByWritingBatchId(drawVO.getWritingBatchId());
        if (drawVO.getExpertType() == 1) {
            evaluationSheet.setExpertNumber(drawVO.getUserIds().size());
            evaluationSheet.setDrawStatus(1);
        } else {
            evaluationSheet.setAlternativeExpertNumber(drawVO.getUserIds().size());
            evaluationSheet.setDrawStatus(2);
        }
        evaluationSheet.setDrawTime(DateUtil.now());
        this.updateById(evaluationSheet);
        for (String userId : drawVO.getUserIds()) {
            UserManage userManage = userManageService.getById(userId);
            EvaluationSheetDetail sheetDetail = new EvaluationSheetDetail();
            sheetDetail.setUserId(userManage.getUserId());
            sheetDetail.setExpertType(drawVO.getExpertType());
            sheetDetail.setEvaluationId(evaluationSheet.getEvaluationId());
            if (drawVO.getExpertType() == 1) {
                sheetDetail.setParticipationStatus(1);//专家组默认参与
            }
            this.evaluationSheetDetailMapper.insert(sheetDetail);
        }

    }

    /**
     * 数组分页
     *
     * @param currentPage
     * @param pageSize
     * @param data
     * @return
     */
    public List<ExpertVO> getData(Long currentPage, Long pageSize, List<ExpertVO> data) {
        if (currentPage == null) {
            currentPage = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        long fromIndex = (currentPage - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();//空数组
        }
        if (fromIndex < 0) {
            return Collections.emptyList();//空数组
        }
        long toIndex = currentPage * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList((int) fromIndex, (int) toIndex);
    }

    @Override
    public Boolean drawRebuild(String writingBatchId) {
        EvaluationSheet evaluationSheet = this.baseMapper.getByWritingBatchId(writingBatchId);
        evaluationSheet.setAlternativeExpertNumber(0);
        evaluationSheet.setExpertNumber(0);
        this.baseMapper.updateById(evaluationSheet);

        QueryWrapper<EvaluationSheetDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(EvaluationSheetDetail.EVALUATION_ID, evaluationSheet.getEvaluationId());
        this.evaluationSheetDetailMapper.delete(queryWrapper);
        return true;
    }

    @Override
    public Boolean drawGroupLeader(EvaluationSheetDetailVO detailVO) {
        //获取组长信息
        EvaluationSheetDetail groupInfo = this.evaluationSheetDetailMapper.getGroupByEvaluationId(detailVO.getEvaluationId());

        if (groupInfo != null && groupInfo.getUserId().equals(detailVO.getUserId())) {
            throw new BaseBusinessException(500, "已是参评专家组组长!");
        }
        if (groupInfo != null && !groupInfo.getUserId().equals(detailVO.getUserId())) {
            groupInfo.setGroupLeader(0);
            this.evaluationSheetDetailMapper.updateById(groupInfo);
        }
        EvaluationSheetDetail detail = this.evaluationSheetDetailMapper.getByEvaluationIdAndUserId(detailVO.getEvaluationId(), detailVO.getUserId());
        detail.setGroupLeader(1);
        this.evaluationSheetDetailMapper.updateById(detail);
        return true;
    }

    @Override
    public Boolean removeExpert(EvaluationSheetDetailVO detailVO) {
        EvaluationSheetDetail detail = this.evaluationSheetDetailMapper.getByEvaluationIdAndUserId(detailVO.getEvaluationId(), detailVO.getUserId());
        detail.setRemark(detailVO.getRemark());
        detail.setParticipationStatus(0);
        this.evaluationSheetDetailMapper.updateById(detail);
        return true;
    }

    @Override
    public Boolean addEvaluation(EvaluationSheetDetailVO detailVO) {
        EvaluationSheet evaluationSheet = this.getById(detailVO.getEvaluationId());
        //已参评总人数
        int evaluationCount = this.evaluationSheetDetailMapper.selectEvaluationCount(detailVO.getEvaluationId());
        if (evaluationCount <= evaluationSheet.getExpertNumber()) {
            EvaluationSheetDetail detail = this.evaluationSheetDetailMapper.getByEvaluationIdAndUserId(detailVO.getEvaluationId(), detailVO.getUserId());
            detail.setParticipationStatus(1);
            detail.setExpertType(1);
            this.evaluationSheetDetailMapper.updateById(detail);
        } else {
            throw new BaseBusinessException(500, "参评专家组已满!");
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean releaseEvaluation(EvaluationReleaseVO evaluationReleaseVO) {
        EvaluationSheet evaluationSheet = this.baseMapper.getByWritingBatchId(evaluationReleaseVO.getWritingBatchId());
        if (evaluationSheet.getReleaseStatus() == 1) {
            throw new BaseBusinessException(400, "已发布,不能重复发布!");
        }
        int evaluationCount = this.evaluationSheetDetailMapper.selectEvaluationCount(evaluationSheet.getEvaluationId());
        if (evaluationSheet.getDrawStatus() == 0) {
            throw new BaseBusinessException(500, "行文批次号:" + evaluationSheet.getWritingBatchId() + "未抽签,不许发布");
        }
        if (evaluationSheet.getExpertNumber() == 0) {
            throw new BaseBusinessException(500, "行文批次号:" + evaluationSheet.getWritingBatchId() + "参评专家至少一人!");
        }
        if (evaluationCount != evaluationSheet.getExpertNumber()) {
            throw new BaseBusinessException(500, "行文批次号:" + evaluationSheet.getWritingBatchId() + "参评专家组人数未达到!");
        }
        evaluationSheet.setReleaseStatus(1);
        this.updateById(evaluationSheet);

        List<String> reportIds = writingBatchService.getReportByWritingBatchId(evaluationReleaseVO.getWritingBatchId());
        //修改审核进度待鉴定
        dataReportService.updateDataExamine(reportIds, DataReport.EXAMINE_PROGRESS_THREE, null);
        List<EvaluationSheetDetail> sheetDetails = this.evaluationSheetDetailMapper.getByEvaluationId(evaluationSheet.getEvaluationId());
        //获取参评专家id
        List<String> userIds = sheetDetails.stream().filter(evaluationSheetDetail -> evaluationSheetDetail.getParticipationStatus() == 1).map(EvaluationSheetDetail::getUserId).collect(Collectors.toList());

        appraiseService.saveAppraise(reportIds, evaluationReleaseVO.getWritingBatchId());

        List<UserManage> userManages = userManageService.listByIds(userIds);
        List<String> phones = userManages.stream().map(UserManage::getPhoneNumber).collect(Collectors.toList());
        //发送短信通知专家
        CompletableFuture.runAsync(() -> {
            try {
                FileInfoVO fileInfoVO = wordBuild(userManages, evaluationReleaseVO,qiniuUtil);
                //保存路径
                fileInfoService.saveFileInfo(Collections.singletonList(fileInfoVO), evaluationSheet.getEvaluationId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = String.format("【南平市】经研究，定于%s，在%s召开相关类别的因病和工伤职工劳动能力鉴定会，时间半天。", evaluationReleaseVO.getReleaseTime(), evaluationReleaseVO.getReleaseAddress());
            smsUtil.endMs(content, phones);
        });

        return true;
    }

    public Boolean isCompleteStatus(String writingBatchId) {
        List<String> reportIds = writingBatchService.getReportByWritingBatchId(writingBatchId);
        List<DataReport> dataReports = dataReportService.listByIds(reportIds);
        List<DataReport> collect = dataReports.stream().filter(dataReport -> dataReport.getExamineProgress() < DataReport.EXAMINE_PROGRESS_SIX).collect(Collectors.toList());
        return CollUtil.isEmpty(collect);
    }

    @Override
    public void updateCompleteStatus(String writingBatchId) {
        EvaluationSheet evaluationSheet = this.baseMapper.getByWritingBatchId(writingBatchId);
        evaluationSheet.setCompleteStatus(1);
        evaluationSheet.setCycleTime(DateUtil.format(DateUtil.parse(evaluationSheet.getCreatedTime()), "YYY-MM-dd") + " -- " + DateUtil.format(new Date(), "YYY-MM-dd"));
        this.updateById(evaluationSheet);
    }

    @Override
    public EvaluationSheet getByWritingBatchId(String writingBatchId) {
        return this.baseMapper.getByWritingBatchId(writingBatchId);
    }

    @Override
    public List<writingListUserVO> getUserId(String writingBatchId) {
        return this.baseMapper.getUserId(writingBatchId);
    }


    @Override
    public IPage<WritingListBO> writingListPage(IPage<WritingListBO> page, WritingListPageVO writingListPageVO) {
        return this.baseMapper.writingListPage(page, writingListPageVO);
    }

    @Override
    public void updateUpload(String writingBatchId) {
        EvaluationSheet evaluationSheet = this.baseMapper.getByWritingBatchId(writingBatchId);
        evaluationSheet.setUploadStatus(1);
        this.updateById(evaluationSheet);
    }


    @Override
    @Transient
    public Map<String, IPage<ExpertVO>> drawInfo(DrawInfoVO drawInfoVO) {
        EvaluationSheet evaluationSheet = this.baseMapper.getByWritingBatchId(drawInfoVO.getWritingBatchId());
        if (evaluationSheet == null) {
            throw new BaseBusinessException(400, "行文编号不存在");
        }
        QueryWrapper<EvaluationSheetDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(EvaluationSheetDetail.EVALUATION_ID, evaluationSheet.getEvaluationId());
        queryWrapper.isNull(EvaluationSheetDetail.REMARK);
        List<EvaluationSheetDetail> details = this.evaluationSheetDetailMapper.selectList(queryWrapper);
        List<ExpertVO> expertVOList = new ArrayList<>();//参评专家
        List<ExpertVO> expertList = new ArrayList<>();//备选专家
        for (EvaluationSheetDetail detail : details) {
            UserManage userManage = userManageService.getById(detail.getUserId());
            ExpertVO expertVO = BeanUtil.toBean(userManage, ExpertVO.class);
            expertVO.setExpertType(detail.getExpertType());
            expertVO.setGroupLeader(detail.getGroupLeader());
            expertVO.setParticipationStatus(detail.getParticipationStatus());
            expertVO.setRemark(detail.getRemark());
            if (detail.getExpertType() == 1) {
                expertVOList.add(expertVO);
            } else {
                expertList.add(expertVO);
            }
        }
        Map<String, IPage<ExpertVO>> iPages = new HashMap<>();
        if (drawInfoVO.getExpertType() == null || drawInfoVO.getExpertType() == 0) {
            IPage<ExpertVO> page = drawInfoVO.getPage();
            page.setRecords(getData(page.getCurrent(), page.getSize(), expertVOList));
            page.setTotal(expertVOList.size());
            iPages.put("ExpertList", page);
            IPage<ExpertVO> ExpertVOPage = drawInfoVO.getPage();
            ExpertVOPage.setRecords(getData(page.getCurrent(), page.getSize(), expertList));
            ExpertVOPage.setTotal(expertVOList.size());
            iPages.put("UnExpertList", ExpertVOPage);
        }
        if (drawInfoVO.getExpertType() == 1) {
            IPage<ExpertVO> page = drawInfoVO.getPage();
            page.setRecords(getData(page.getCurrent(), page.getSize(), expertVOList));
            page.setTotal(expertVOList.size());
            iPages.put("ExpertList", page);
        }
        if (drawInfoVO.getExpertType() == 2) {
            IPage<ExpertVO> ExpertVOPage = drawInfoVO.getPage();
            ExpertVOPage.setRecords(getData(ExpertVOPage.getCurrent(), ExpertVOPage.getSize(), expertList));
            ExpertVOPage.setTotal(expertVOList.size());
            iPages.put("UnExpertList", ExpertVOPage);
        }
        return iPages;
    }

    public FileInfoVO wordBuild(List<UserManage> userManages, EvaluationReleaseVO evaluationReleaseVO,QiniuUtil qiniuUtil) throws IOException {
        Calendar now = Calendar.getInstance();
        List<Map<String, Object>> listDate = new ArrayList<>();
        for (int i = 0; i < userManages.size(); i++) {
            Map<String, Object> dateMap = new LinkedHashMap<>();
            dateMap.put("index", i + 1);
            dateMap.put("name", userManages.get(i).getUserName());
            dateMap.put("titleGrade", userManages.get(i).getTitleGrade());
            dateMap.put("unit", userManages.get(i).getUnit());
            dateMap.put("remark", userManages.get(i).getRemark());
            listDate.add(dateMap);
        }
        HashMap<String, Object> info = new HashMap<String, Object>() {{
            put("listDate", listDate);
            put("year", now.get(Calendar.YEAR));//当前年
            put("month", (now.get(Calendar.MONTH) + 1));//当前月
            put("date", now.get(Calendar.DAY_OF_MONTH));//当前日
            put("time", evaluationReleaseVO.getReleaseTime());//时间
            put("address", evaluationReleaseVO.getReleaseAddress());//地址
            put("number", userManages.size());//人数

        }};

        InputStream in = this.getClass().getResourceAsStream("/file.docx");
        //渲染表格  动态行
        HackLoopTableRenderPolicy policy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder()
                .bind("listDate", policy).build();
        XWPFTemplate template = XWPFTemplate.compile(in, config).render(info);
        //=================生成文件保存在临时目录下=================
        // 生成的word格式
        String formatSuffix = ".docx";
        // 拼接后的文件名
        String fileName = "通知书" + evaluationReleaseVO.getWritingBatchId() + formatSuffix;//文件名  带后缀

        template.writeToFile(Constants.TMP_HOME + fileName);
        String filePath = Word2PdfUtil.doc2Img(Constants.TMP_HOME + fileName, Constants.TMP_HOME);
        //上传文件
        String fileKey = qiniuUtil.fileUpload(filePath);
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setType("7");
        fileInfoVO.setFilePath(fileKey);
        fileInfoVO.setFileName(fileName);
        return fileInfoVO;

    }

}
