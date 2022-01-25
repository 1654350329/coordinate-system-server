package com.tree.clouds.coordination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.AppraisalReviewMapper;
import com.tree.clouds.coordination.model.bo.AppraisalReviewBO;
import com.tree.clouds.coordination.model.entity.AppraisalReview;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.entity.ReviewSignature;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import com.tree.clouds.coordination.model.vo.AppraisalReviewVO;
import com.tree.clouds.coordination.service.AppraisalReviewService;
import com.tree.clouds.coordination.service.DataReportService;
import com.tree.clouds.coordination.service.ReviewSignatureService;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * <p>
 * 复核表 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class AppraisalReviewServiceImpl extends ServiceImpl<AppraisalReviewMapper, AppraisalReview> implements AppraisalReviewService {

    @Autowired
    private DataReportService dataReportService;
    @Autowired
    private ReviewSignatureService reviewSignatureService;

    @Override
    public IPage<AppraisalReviewBO> appraisalReviewPage(AppraisalReviewPageVO appraisePageVO) {
        IPage<AppraisalReviewBO> page = appraisePageVO.getPage();
        return this.baseMapper.appraisalReviewPage(page, appraisePageVO);

    }

    @Override
    public Boolean addAppraisalReview(AppraisalReviewVO appraisalReviewVO) {
        AppraisalReview review = this.getById(appraisalReviewVO.getAppraiseResultId());
        AppraisalReview appraisalReview = new AppraisalReview();
        appraisalReview.setAppraiseResultId(appraisalReviewVO.getAppraiseResultId());
        if (appraisalReviewVO.getAppraisalReviewResult() == 1) {
            //反驳打回
            if (appraisalReviewVO.getAppraisalReviewStatus().equals("1")) {
                this.dataReportService.updateDataExamine(Collections.singletonList(review.getReportId()), DataReport.EXAMINE_PROGRESS_FIVE, null);
                appraisalReview.setAppraisalReviewStatus(1);
                appraisalReview.setAppraisalReviewTimeOne(appraisalReviewVO.getAppraisalReviewTime());
                appraisalReview.setAppraisalReviewUserOne(LoginUserUtil.getUserId());
                appraisalReview.setAppraisalReviewResultOne(appraisalReviewVO.getAppraisalReviewResult());
                this.updateById(appraisalReview);
            }
            if (appraisalReviewVO.getAppraisalReviewStatus().equals("2")) {
                this.dataReportService.updateDataExamine(Collections.singletonList(review.getReportId()), DataReport.EXAMINE_PROGRESS_SIX, null);
                review.setAppraisalReviewStatus(2);
                appraisalReview.setAppraisalReviewTimeTwo(appraisalReviewVO.getAppraisalReviewTime());
                appraisalReview.setAppraisalReviewResultTwo(appraisalReviewVO.getAppraisalReviewResult());
                appraisalReview.setAppraisalReviewUserTwo(LoginUserUtil.getUserId());
                this.updateById(review);
                //添加到审签
                ReviewSignature reviewSignature = new ReviewSignature();
                reviewSignature.setWritingBatchId(review.getWritingBatchId());
                reviewSignature.setReportId(review.getReportId());
                reviewSignature.setAppralseNumber(review.getAppralseNumber());
                reviewSignature.setReviewStatus("0");
                reviewSignatureService.save(reviewSignature);
            }
        } else {
            //反驳打回初审状态
            this.dataReportService.updateDataExamine(Collections.singletonList(review.getReportId()), DataReport.EXAMINE_PROGRESS_ZERO, appraisalReviewVO.getRemark());
            appraisalReview.setAppraisalReviewStatus(0);
            appraisalReview.setRemark(appraisalReviewVO.getRemark());
            this.updateById(appraisalReview);
        }
        return true;
    }

    @Override
    public AppraisalReview getByReportId(String reportId) {
        QueryWrapper<AppraisalReview> wrapper = new QueryWrapper<>();
        wrapper.eq(AppraisalReview.REPORT_ID, reportId);
        return getOne(wrapper);
    }
}
