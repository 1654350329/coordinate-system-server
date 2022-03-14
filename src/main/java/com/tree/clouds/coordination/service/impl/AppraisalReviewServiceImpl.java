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
import com.tree.clouds.coordination.utils.BaseBusinessException;
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
        if (review.getAppraisalReviewStatus().equals(2) || (review.getAppraisalReviewResult() != null && review.getAppraisalReviewResult() == 0)) {
            throw new BaseBusinessException(400, "已完成鉴定,不允许修改!");
        }
        AppraisalReview appraisalReview = new AppraisalReview();
        appraisalReview.setAppraiseResultId(appraisalReviewVO.getAppraiseResultId());
        appraisalReview.setRemark(appraisalReviewVO.getRemark());//最终意见
        appraisalReview.setAppraisalReviewResult(appraisalReviewVO.getAppraisalReviewResult());
        //同意
        if (appraisalReviewVO.getAppraisalReviewResult() == 1) {
            if (review.getAppraisalReviewStatus().equals(0)) {
                this.dataReportService.updateDataExamine(Collections.singletonList(review.getReportId()), DataReport.EXAMINE_PROGRESS_FOUR, null);
                appraisalReview.setAppraisalReviewStatus(1);
                appraisalReview.setAppraisalReviewTimeOne(appraisalReviewVO.getAppraisalReviewTime());
                appraisalReview.setAppraisalReviewUserOne(LoginUserUtil.getUserId());
                appraisalReview.setAppraisalReviewResultOne(appraisalReviewVO.getAppraisalReviewResult());
                appraisalReview.setRemarkOne(appraisalReviewVO.getRemark());
                this.updateById(appraisalReview);
            }
            if (review.getAppraisalReviewStatus().equals(1)) {
                this.dataReportService.updateDataExamine(Collections.singletonList(review.getReportId()), DataReport.EXAMINE_PROGRESS_FIVE, null);
                appraisalReview.setAppraisalReviewStatus(2);
                appraisalReview.setAppraisalReviewTimeTwo(appraisalReviewVO.getAppraisalReviewTime());
                appraisalReview.setAppraisalReviewResultTwo(appraisalReviewVO.getAppraisalReviewResult());
                appraisalReview.setAppraisalReviewUserTwo(LoginUserUtil.getUserId());
                appraisalReview.setRemarkTwo(appraisalReviewVO.getRemark());
                this.updateById(appraisalReview);
                //添加到审签
                ReviewSignature reviewSignature = new ReviewSignature();
                reviewSignature.setWritingBatchId(review.getWritingBatchId());
                reviewSignature.setReportId(review.getReportId());
                reviewSignature.setAppraiseNumber(review.getAppraiseNumber());
                reviewSignature.setReviewStatus(0);
                reviewSignatureService.save(reviewSignature);
            }
        } else if (appraisalReviewVO.getAppraisalReviewResult() == 0) {
            //一核驳回
            if (review.getAppraisalReviewStatus().equals(0)) {
                appraisalReview.setAppraisalReviewStatus(1);
                appraisalReview.setAppraisalReviewTimeOne(appraisalReviewVO.getAppraisalReviewTime());
                appraisalReview.setAppraisalReviewUserOne(LoginUserUtil.getUserId());
                appraisalReview.setAppraisalReviewResultOne(appraisalReviewVO.getAppraisalReviewResult());
                appraisalReview.setRemarkOne(appraisalReviewVO.getRemark());
            }
            //二核驳回
            if (review.getAppraisalReviewStatus().equals(1)) {
                review.setAppraisalReviewStatus(2);
                appraisalReview.setAppraisalReviewTimeTwo(appraisalReviewVO.getAppraisalReviewTime());
                appraisalReview.setAppraisalReviewResultTwo(appraisalReviewVO.getAppraisalReviewResult());
                appraisalReview.setAppraisalReviewUserTwo(LoginUserUtil.getUserId());
                appraisalReview.setRemarkTwo(appraisalReviewVO.getRemark());
            }
            //反驳打回初审状态
            this.dataReportService.updateDataExamine(Collections.singletonList(review.getReportId()), DataReport.EXAMINE_PROGRESS_ZERO, appraisalReviewVO.getRemark());
            appraisalReview.setAppraisalReviewResult(0);

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
