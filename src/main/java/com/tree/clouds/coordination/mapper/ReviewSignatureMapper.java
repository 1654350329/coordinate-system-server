package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.ReviewSignatureBO;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.entity.ReviewSignature;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 认定审签 Mapper 接口
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface ReviewSignatureMapper extends BaseMapper<ReviewSignature> {

    IPage<ReviewSignatureBO> reviewSignaturePage(IPage<ReviewSignatureBO> page, @Param("reviewPageVO") AppraisalReviewPageVO reviewPageVO);

    IPage<WritingResultBO> writingResultPage(IPage<WritingResultBO> page, @Param("writingResultPageVO") WritingResultPageVO writingResultPageVO);

}
