package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.ReviewSignatureBO;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.entity.ReviewSignature;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import com.tree.clouds.coordination.model.vo.ReviewSignatureVO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;

/**
 * <p>
 * 认定审签 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface ReviewSignatureService extends IService<ReviewSignature> {

    IPage<ReviewSignatureBO> reviewSignaturePage(AppraisalReviewPageVO appraisePageVO);

    Boolean addReviewSignature(ReviewSignatureVO reviewSignatureVO);

    IPage<WritingResultBO> writingResultPage(IPage<WritingResultBO> page, WritingResultPageVO writingResultPageVO);

    ReviewSignature getByReportId(String reportId);
}
