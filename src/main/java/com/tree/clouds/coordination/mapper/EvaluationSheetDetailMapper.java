package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.entity.EvaluationSheetDetail;
import com.tree.clouds.coordination.model.vo.ExpertDetailVO;
import com.tree.clouds.coordination.model.vo.WritingBatchVO;

import java.util.List;

/**
 * <p>
 * 评估详细表 Mapper 接口
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface EvaluationSheetDetailMapper extends BaseMapper<EvaluationSheetDetail> {


    /**
     * 获取参与评估人数
     *
     * @param evaluationId
     * @return
     */
    default int selectEvaluationCount(String evaluationId) {
        QueryWrapper<EvaluationSheetDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(EvaluationSheetDetail.EVALUATION_ID, evaluationId);
        queryWrapper.eq(EvaluationSheetDetail.PARTICIPATION_STATUS, 1);
        return this.selectCount(queryWrapper);
    }

    /**
     * 根据评估id与用户id获取信息
     *
     * @param evaluationId
     * @param userId
     * @return
     */
    default EvaluationSheetDetail getByEvaluationIdAndUserId(String evaluationId, String userId) {
        QueryWrapper<EvaluationSheetDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(EvaluationSheetDetail.EVALUATION_ID, evaluationId);
        queryWrapper.eq(EvaluationSheetDetail.USER_ID, userId);
        return this.selectOne(queryWrapper);
    }

    /**
     * 根据评估id获取信息
     *
     * @param evaluationId
     * @return
     */
    default List<EvaluationSheetDetail> getByEvaluationId(String evaluationId) {
        QueryWrapper<EvaluationSheetDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(EvaluationSheetDetail.EVALUATION_ID, evaluationId);
        return this.selectList(queryWrapper);
    }

    /**
     * 获取组长
     *
     * @param evaluationId
     * @return
     */
    default EvaluationSheetDetail getGroupByEvaluationId(String evaluationId) {
        QueryWrapper<EvaluationSheetDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(EvaluationSheetDetail.EVALUATION_ID, evaluationId);
        queryWrapper.eq(EvaluationSheetDetail.GROUP_LEADER, 1);
        return this.selectOne(queryWrapper);
    }

    IPage<ExpertDetailVO> expertDetailPage(IPage<WritingBatchVO> page, WritingBatchVO writingBatchVO);
}
