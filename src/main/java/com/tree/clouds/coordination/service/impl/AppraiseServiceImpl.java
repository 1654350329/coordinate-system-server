package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.AppraiseMapper;
import com.tree.clouds.coordination.model.bo.AppraiseBO;
import com.tree.clouds.coordination.model.entity.AppraisalReview;
import com.tree.clouds.coordination.model.entity.Appraise;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.entity.ReviewSignature;
import com.tree.clouds.coordination.model.vo.*;
import com.tree.clouds.coordination.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    public IPage<AppraiseBO> appraisePage(AppraisePageVO appraisePageVO) {
        IPage<AppraiseBO> page = appraisePageVO.getPage();
        return this.baseMapper.appraisePage(page, appraisePageVO);
    }

    @Override
    @Transient
    public void addAppraise(AppraiseVO appraiseVO) {
        Appraise appraise = this.getById(appraiseVO.getAppraiseId());
        //修改审核进度为到复核一
        dataReportService.updateDataExamine(Collections.singletonList(appraise.getReportId()), DataReport.EXAMINE_PROGRESS_FOUR);
        //更新鉴定状态
        Appraise app = BeanUtil.toBean(appraiseVO, Appraise.class);
        app.setAppralseStatus("1");
        app.setAppraiseId(appraiseVO.getAppraiseId());
        this.updateById(app);
        //添加到鉴定复合表
        AppraisalReview appraisalReview = new AppraisalReview();
        appraisalReview.setAppraisalReviewStatus(0);
        appraisalReview.setWritingBatchId(appraise.getWritingBatchId());
        appraisalReview.setAppralseNumber(appraise.getAppralseNumber());
        appraisalReview.setReportId(appraise.getReportId());
        appraisalReviewService.save(appraisalReview);
        //保存文件信息
        FileInfoVO fileInfoVO = appraiseVO.getFileInfoVO();
        fileInfoVO.setType("3");
        fileInfoService.saveFileInfo(Collections.singletonList(fileInfoVO), appraise.getAppraiseId());
    }

    @Override
    public Appraise getByWritingBatchId(String writingBatchId) {
        QueryWrapper<Appraise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Appraise.WRITING_BATCH_ID, writingBatchId);
        return this.getOne(queryWrapper);
    }

    @Override
    public Integer getAppralseNumber(String time) {
        String appralseNumber = this.baseMapper.getAppralseNumber(time);
        if (appralseNumber == null) {
            return 0;
        }
        return Integer.parseInt(appralseNumber) + 1;
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
        AppraiseInfoVO appraiseInfo = new AppraiseInfoVO();
        //复核信息
        AppraisalReview appraise = appraisalReviewService.getByReportId(reportId);
        List<AppraisalReviewExpertVO> appraisalReviewExpertVOS = new ArrayList<>();
        if (appraise.getAppraisalReviewStatus() == 1) {
            AppraisalReviewExpertVO appraisalReviewExpertVO = BeanUtil.toBean(appraise, AppraisalReviewExpertVO.class);
            appraisalReviewExpertVO.setAppraisalReviewResult(appraise.getAppraisalReviewResultOne());
            appraisalReviewExpertVO.setAppraisalReviewTime(appraise.getAppraisalReviewTimeOne());
            appraisalReviewExpertVO.setAppraisalReviewUser(appraise.getAppraisalReviewUserOne());
            appraisalReviewExpertVOS.add(appraisalReviewExpertVO);
        }
        if (appraise.getAppraisalReviewStatus() == 2) {
            AppraisalReviewExpertVO appraisalReviewExpertVO = BeanUtil.toBean(appraise, AppraisalReviewExpertVO.class);
            appraisalReviewExpertVO.setAppraisalReviewResult(appraise.getAppraisalReviewResultTwo());
            appraisalReviewExpertVO.setAppraisalReviewTime(appraise.getAppraisalReviewTimeTwo());
            appraisalReviewExpertVO.setAppraisalReviewUser(appraise.getAppraisalReviewUserTwo());
            appraisalReviewExpertVOS.add(appraisalReviewExpertVO);
        }
        appraiseInfo.setAppraisalReviewExpertVOS(appraisalReviewExpertVOS);
        ReviewSignature reviewSignature = reviewSignatureService.getByReportId(reportId);
        appraiseInfo.setReviewSignature(reviewSignature);
        return appraiseInfo;
    }

}
