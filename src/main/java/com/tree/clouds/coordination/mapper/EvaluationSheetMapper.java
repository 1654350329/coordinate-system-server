package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.entity.EvaluationSheet;
import com.tree.clouds.coordination.model.vo.EvaluationSheetPageVO;
import com.tree.clouds.coordination.model.vo.WritingListPageVO;
import com.tree.clouds.coordination.model.vo.writingListUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评估表 Mapper 接口
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface EvaluationSheetMapper extends BaseMapper<EvaluationSheet> {

    String getNumber(@Param("year") int year, @Param("month") int month, @Param("sort") int sort);

    IPage<EvaluationSheet> evaluationSheetPage(IPage<EvaluationSheet> page, @Param("evaluationSheetPageVO") EvaluationSheetPageVO evaluationSheetPageVO);

    default EvaluationSheet getByWritingBatchId(String writingBatchId) {
        QueryWrapper<EvaluationSheet> wrapper = new QueryWrapper<>();
        wrapper.eq(EvaluationSheet.WRITING_BATCH_ID, writingBatchId);
        return this.selectOne(wrapper);
    }

    IPage<WritingListBO> writingListPage(IPage<WritingListBO> page, @Param("writingListPageVO") WritingListPageVO writingListPageVO);

    List<writingListUserVO> getUserId(@Param("writingBatchId") String writingBatchId);

    List<String> writingBatch();
}
