package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.mapper.DataReportMapper;
import com.tree.clouds.coordination.model.bo.ReportDetailInfoBO;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.entity.EvaluationSheet;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.model.vo.*;
import com.tree.clouds.coordination.service.*;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import com.tree.clouds.coordination.utils.DownloadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员行文名单 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class WritingListServiceImpl implements WritingListService {

    @Autowired
    private DataReportMapper dataReportMapper;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private WritingBatchService writingBatchService;
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private EvaluationSheetService evaluationSheetService;


    @Override
    public IPage<WritingListBO> writingListPage(WritingListPageVO writingListPageVO) {
        IPage<WritingListBO> page = writingListPageVO.getPage();
        return this.evaluationSheetService.writingListPage(page, writingListPageVO);
    }

    @Override
    public void writingListUpload(WritingListVO writingListVO) {
        FileInfoVO fileInfoVO = writingListVO.getFileInfoVO();
        fileInfoVO.setType("5");
        this.fileInfoService.saveFileInfo(Collections.singletonList(fileInfoVO), writingListVO.getWritingBatchId());
        this.evaluationSheetService.updateUpload(writingListVO.getWritingBatchId());
    }

    @Override
    public WritingListDetailVO writingListDetail(String writingBatchId) {
        WritingListDetailVO writingListDetailVO = new WritingListDetailVO();
        EvaluationSheet evaluationSheet = evaluationSheetService.getByWritingBatchId(writingBatchId);
        writingListDetailVO.setCtime(evaluationSheet.getYear() + "年" + evaluationSheet.getMonth() + "月");
        if (evaluationSheet.getCycleTime() != null) {
            String trim = evaluationSheet.getCycleTime().split("--")[1].trim();
            String[] split = trim.split("-");
            writingListDetailVO.setTime(split[0] + "年" + split[1] + "月" + split[2] + "日");
        }
        List<String> reports = writingBatchService.getReportByWritingBatchId(writingBatchId);
        List<ReportDetailInfoBO> detailInfoBOS = dataReportMapper.getDetailInfo(reports);
        writingListDetailVO.setDataReports(detailInfoBOS);

        Set<String> appraisalReviewUsers = new LinkedHashSet<>();
        List<String> examineUsers = new ArrayList<>();
        List<String> reportUsers = new ArrayList<>();
        List<writingListUserVO> writingListUserVOS = evaluationSheetService.getUserId(writingBatchId);
        for (writingListUserVO writingListUserVO : writingListUserVOS) {
            appraisalReviewUsers.addAll(Arrays.asList(writingListUserVO.getAppraisalReviewUserTwo().split(",")));
            examineUsers.add(writingListUserVO.getExamineUser());
            reportUsers.add(writingListUserVO.getReportUser());
        }

        String appraisalReviewUser = userManageService.listByIds(appraisalReviewUsers).stream().map(UserManage::getUserName).collect(Collectors.joining(" "));
        String examineUser = userManageService.listByIds(examineUsers).stream().map(UserManage::getUserName).collect(Collectors.joining(" "));
        String reportUser = userManageService.listByIds(reportUsers).stream().map(UserManage::getUserName).collect(Collectors.joining(" "));
        writingListDetailVO.setExamineUser(examineUser);
        writingListDetailVO.setDataReportUser(reportUser);
        writingListDetailVO.setAppraisalReviewUser(appraisalReviewUser);
        return writingListDetailVO;
    }

    @Override
    public void writingListExport(List<String> writingBatchIds, HttpServletResponse response) {
        if (writingBatchIds.size() == 0) {
            throw new BaseBusinessException(400, "请钩选要导出的行文名单!");
        }
        String filePath = null;
        FileUtil.del(Constants.TMP_HOME + "行文名单");
        FileUtil.mkdir(Constants.TMP_HOME + "行文名单");
        for (String writingBatchId : writingBatchIds) {
            WritingListDetailVO writingListDetailVO = writingListDetail(writingBatchId);
            String ctime = format(writingListDetailVO.getCtime());
            String fileName = ctime + "劳动能力鉴定等级人员名单" + UUID.fastUUID().toString().substring(0, 4) + ".xlsx";
            Map<String, Object> head = new HashMap<>();
            head.put("ctime", ctime);
            head.put("time", writingListDetailVO.getTime());
            head.put("examineUser", writingListDetailVO.getExamineUser());
            head.put("appraisalReviewUser", writingListDetailVO.getAppraisalReviewUser());
            head.put("dataReportUser", writingListDetailVO.getDataReportUser());
            List<Map<String, Object>> dates = new ArrayList<>();
            for (int i = 0; i < writingListDetailVO.getDataReports().size(); i++) {
                ReportDetailInfoBO infoBO = writingListDetailVO.getDataReports().get(i);
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("index", i + 1);
                data.put("appraiseNumber", infoBO.getAppraiseNumber());
                data.put("nativePlace", infoBO.getNativePlace());
                data.put("unitName", infoBO.getUnitName());
                data.put("identifiedName", infoBO.getIdentifiedName());
                data.put("idCart", infoBO.getIdCart());
                data.put("sort", infoBO.getSort() == 0 ? "工" : "病");
                data.put("sickTime", infoBO.getSickTime());
                data.put("sickCondition", infoBO.getSickCondition());
                data.put("appraiseGrade", infoBO.getAppraiseGrade());
                data.put("appraiseResult", infoBO.getAppraiseResult());
                dates.add(data);
            }

            InputStream resource = this.getClass().getResourceAsStream("/WritingListDetail.xlsx");
            filePath = Constants.TMP_HOME + "行文名单/" + fileName;
            ExcelWriter excelWriter = EasyExcel.write(filePath)
                    .withTemplate(resource)
                    .build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 注意：forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。这会把所有数据放到内存，数据量大时会很耗内存
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(dates, fillConfig, writeSheet);
            excelWriter.fill(head, fillConfig, writeSheet);
            excelWriter.finish();
        }
        if (writingBatchIds.size() == 1) {
            File file = new File(filePath);
            byte[] bytes = DownloadFile.File2byte(file);
            DownloadFile.downloadFile(bytes, file.getName(), response, false);
        }
        File zip = ZipUtil.zip(Constants.TMP_HOME + "\\行文名单");

        byte[] bytes = DownloadFile.File2byte(zip);
        DownloadFile.downloadFile(bytes, zip.getName(), response, false);

    }

    public String format(String text) {
        for (int i = 0; i < 10; i++) {
            text = text.replace((char) ('0' + i),
                    "零一二三四五六七八九".charAt(i));
        }
        return text;
    }
}
