package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.AppraiseMapper;
import com.tree.clouds.coordination.model.bo.AppraiseBO;
import com.tree.clouds.coordination.model.entity.*;
import com.tree.clouds.coordination.model.vo.*;
import com.tree.clouds.coordination.service.*;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 鉴定表 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class AppraiseServiceImpl extends ServiceImpl<AppraiseMapper, Appraise> implements AppraiseService {

    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private DataReportService dataReportService;
    @Autowired
    private AppraisalReviewService appraisalReviewService;
    @Autowired
    private ReviewSignatureService reviewSignatureService;
    @Autowired
    private AppraiseService appraiseService;
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private MessageInfoService messageInfoService;

    @Override
    public IPage<AppraiseBO> appraisePage(AppraisePageVO appraisePageVO) {
        IPage<AppraiseBO> page = appraisePageVO.getPage();
        return this.baseMapper.appraisePage(page, appraisePageVO);
    }

    @Override
    @Transient
    public void addAppraise(AppraiseVO appraiseVO) {
        Appraise appraise = this.getById(appraiseVO.getAppraiseId());
        if (appraise.getAppraiseStatus() == 1) {
            throw new BaseBusinessException(400, "已完成鉴定无法修改!");
        }
        //修改审核进度为到复核一
        dataReportService.updateDataExamine(Collections.singletonList(appraise.getReportId()), DataReport.EXAMINE_PROGRESS_THREE, null);
        DataReport report = new DataReport();
        report.setReportId(appraise.getReportId());
        report.setSickCondition(appraiseVO.getSickCondition());
        dataReportService.updateById(report);
        //更新鉴定状态
        Appraise app = BeanUtil.toBean(appraiseVO, Appraise.class);
        app.setAppraiseStatus(1);
        app.setAppraiseId(appraiseVO.getAppraiseId());
        this.updateById(app);
        //添加到鉴定复合表
        AppraisalReview appraisalReview = new AppraisalReview();
        appraisalReview.setAppraisalReviewStatus(0);
        appraisalReview.setWritingBatchId(appraise.getWritingBatchId());
        appraisalReview.setAppraiseNumber(appraise.getAppraiseNumber());
        appraisalReview.setReportId(appraise.getReportId());
        appraisalReviewService.save(appraisalReview);
        //保存文件信息
        appraiseVO.getFileInfoVO().forEach(fileInfoVO -> fileInfoVO.setType("3"));
        fileInfoService.saveFileInfo(appraiseVO.getFileInfoVO(), appraise.getAppraiseId());
    }

    @Override
    public Appraise getByWritingBatchId(String writingBatchId) {
        QueryWrapper<Appraise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Appraise.WRITING_BATCH_ID, writingBatchId);
        return this.getOne(queryWrapper);
    }

    @Override
    public Integer getAppraiseNumber(String time, String type) {
        String appraiseNumber = this.baseMapper.getAppraiseNumber(time, type);
        if (appraiseNumber == null) {
            return 0;
        }
        return Integer.parseInt(appraiseNumber) + 1;
    }

    @Override
    public Appraise getByReportId(String reportId) {
        QueryWrapper<Appraise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Appraise.REPORT_ID, reportId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<ExpertGroupInfo> expertGroupInfo(String reportId) {
        return this.baseMapper.expertGroupInfo(reportId);
    }

    @Override
    public AppraiseInfoVO appraiseInfoVO(String reportId) {
        DataReport report = dataReportService.getById(reportId);
        AppraiseInfoVO appraiseInfo = new AppraiseInfoVO();
        //鉴定意见
        Appraise appraise = appraiseService.getByReportId(reportId);
        if (appraise == null) {
            return appraiseInfo;
        }
        appraise.setSickCondition(report.getSickCondition());
        List<FileInfo> fileInfoList = fileInfoService.getByBizIdsAndType(appraise.getAppraiseId(), "3");
        if (CollUtil.isNotEmpty(fileInfoList)) {
            List<String> filePathS = fileInfoList.stream().map(FileInfo::getFilePath).collect(Collectors.toList());
            appraise.setFiles(filePathS);
        }
        appraiseInfo.setAppraise(appraise);
        //复核信息
        AppraisalReview appraisalReview = appraisalReviewService.getByReportId(reportId);
        if (appraisalReview == null) {
            return appraiseInfo;
        }
        List<AppraisalReviewExpertVO> appraisalReviewExpertVOS = new ArrayList<>();
        if (appraisalReview.getAppraisalReviewStatus() == 1) {
            AppraisalReviewExpertVO appraisalReviewExpertVO = BeanUtil.toBean(appraisalReview, AppraisalReviewExpertVO.class);
            appraisalReviewExpertVO.setAppraisalReviewResult(appraisalReview.getAppraisalReviewResultOne());
            appraisalReviewExpertVO.setAppraisalReviewTime(appraisalReview.getAppraisalReviewTimeOne());
            if (appraisalReview.getAppraisalReviewUserOne() != null) {
                appraisalReviewExpertVO.setAppraisalReviewUser(userManageService.getById(appraisalReview.getAppraisalReviewUserOne()).getUserName());
            }
            appraisalReviewExpertVO.setRemark(appraisalReview.getRemarkOne());
            appraisalReviewExpertVOS.add(appraisalReviewExpertVO);
        }
        if (appraisalReview.getAppraisalReviewStatus() == 2) {
            AppraisalReviewExpertVO reviewExpertVO = BeanUtil.toBean(appraisalReview, AppraisalReviewExpertVO.class);
            reviewExpertVO.setAppraisalReviewStatus(1);
            reviewExpertVO.setAppraisalReviewResult(appraisalReview.getAppraisalReviewResultOne());
            reviewExpertVO.setAppraisalReviewTime(appraisalReview.getAppraisalReviewTimeOne());
            if (appraisalReview.getAppraisalReviewUserOne() != null) {
                reviewExpertVO.setAppraisalReviewUser(userManageService.getById(appraisalReview.getAppraisalReviewUserOne()).getUserName());
            }
            reviewExpertVO.setRemark(appraisalReview.getRemarkOne());
            appraisalReviewExpertVOS.add(reviewExpertVO);

            AppraisalReviewExpertVO appraisalReviewExpertVO = BeanUtil.toBean(appraisalReview, AppraisalReviewExpertVO.class);
            appraisalReviewExpertVO.setAppraisalReviewResult(appraisalReview.getAppraisalReviewResultTwo());
            appraisalReviewExpertVO.setAppraisalReviewTime(appraisalReview.getAppraisalReviewTimeTwo());
            if (appraisalReview.getAppraisalReviewUserTwo() != null) {
                appraisalReviewExpertVO.setAppraisalReviewUser(userManageService.getById(appraisalReview.getAppraisalReviewUserTwo()).getUserName());
            }
            appraisalReviewExpertVO.setRemark(appraisalReview.getRemarkOne());
            appraisalReviewExpertVOS.add(appraisalReviewExpertVO);
        }
        appraiseInfo.setAppraisalReviewExpertVOS(appraisalReviewExpertVOS);
        ReviewSignature reviewSignature = reviewSignatureService.getByReportId(reportId);
        if (reviewSignature == null) {
            return appraiseInfo;
        }
        if (reviewSignature.getReviewUser() != null) {
            reviewSignature.setReviewUser(userManageService.getById(reviewSignature.getReviewUser()).getUserName());
        }
        MessageInfo messageInfo = messageInfoService.getOne(new QueryWrapper<MessageInfo>().eq(MessageInfo.REPORT_ID, reportId));
        if (messageInfo != null) {
            List<FileInfo> fileInfos = fileInfoService.getByBizIdsAndType(messageInfo.getMessageId(), "6");
            if (CollUtil.isNotEmpty(fileInfos)) {
                reviewSignature.setResultFile(fileInfos.get(0).getFilePath());
            }
        }
        appraiseInfo.setReviewSignature(reviewSignature);
        return appraiseInfo;
    }

    @Override
    public void saveAppraise(List<String> reportIds, String writingBatchId) {
        //生成认定编号
        Calendar now = Calendar.getInstance();
        String year = now.get(Calendar.YEAR) + "";
        int month = now.get(Calendar.DAY_OF_MONTH);
        String m = month < 10 ? "0" + month : String.valueOf(month);
        String type = writingBatchId.contains("工") ? "工" : "病";
        Integer appraiseNumber = appraiseService.getAppraiseNumber(year + "-" + m, type);
        //添加到鉴定信息表
        List<Appraise> appraises = new ArrayList<>();
        for (String reportId : reportIds) {
            appraiseNumber = appraiseNumber + 1;
            Appraise appraise = new Appraise();
            appraise.setWritingBatchId(writingBatchId);
            appraise.setAppraiseStatus(0);
            appraise.setReportId(reportId);
            appraise.setAppraiseNumber(String.format("%04d", appraiseNumber));//认定编号
            appraises.add(appraise);
        }
        this.saveBatch(appraises);
    }
}
