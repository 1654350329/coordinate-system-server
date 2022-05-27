package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.AppraisalReviewBO;
import com.tree.clouds.coordination.model.entity.AppraisalReview;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 复核表 Mapper 接口
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface AppraisalReviewMapper extends BaseMapper<AppraisalReview> {

    IPage<AppraisalReviewBO> appraisalReviewPage(IPage<AppraisalReviewBO> page, @Param("appraisalReviewPageVO") AppraisalReviewPageVO appraisalReviewPageVO, int type);
}
