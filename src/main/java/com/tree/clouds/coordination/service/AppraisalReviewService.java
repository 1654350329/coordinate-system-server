package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.AppraisalReviewBO;
import com.tree.clouds.coordination.model.entity.AppraisalReview;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import com.tree.clouds.coordination.model.vo.AppraisalReviewVO;

/**
 * <p>
 * 复核表 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface AppraisalReviewService extends IService<AppraisalReview> {

    IPage<AppraisalReviewBO> appraisalReviewPage(AppraisalReviewPageVO appraisePageVO);

    Boolean addAppraisalReview(AppraisalReviewVO appraisalReviewVO);

    AppraisalReview getByReportId(String reportId);
}
