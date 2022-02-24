package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.entity.EvaluationSheet;
import com.tree.clouds.coordination.model.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评估表 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface EvaluationSheetService extends IService<EvaluationSheet> {

    /**
     * 创建待审表
     *
     * @param ids
     */
    void addEvaluationSheet(List<String> ids);

    /**
     * 评估分页
     *
     * @param evaluationSheetPageVO
     * @return
     */
    IPage<EvaluationSheet> evaluationSheetPage(EvaluationSheetPageVO evaluationSheetPageVO);

    /**
     * 抽签
     *
     * @param drawVO
     * @return
     */
    void draw(DrawVO drawVO);

    /**
     * 重置抽签
     *
     * @param writingBatchId
     * @return
     */
    Boolean drawRebuild(String writingBatchId);

    /**
     * 设置组长
     *
     * @param evaluationSheetDetailVO
     * @return
     */
    Boolean drawGroupLeader(EvaluationSheetDetailVO evaluationSheetDetailVO);

    /**
     * 移除专家参评
     *
     * @param evaluationSheetDetailVO
     * @return
     */
    Boolean removeExpert(EvaluationSheetDetailVO evaluationSheetDetailVO);

    /**
     * 添加参评
     *
     * @param evaluationSheetDetailVO
     * @return
     */
    Boolean addEvaluation(EvaluationSheetDetailVO evaluationSheetDetailVO);

    /**
     * 发布评估
     *
     * @param evaluationReleaseVO
     * @return
     */
    Boolean releaseEvaluation(EvaluationReleaseVO evaluationReleaseVO);

    Map<String, IPage<ExpertVO>> drawInfo(DrawInfoVO drawInfoVO);

    Boolean isCompleteStatus(String writingBatchId);

    void updateCompleteStatus(String writingBatchId);

    IPage<WritingListBO> writingListPage(IPage<WritingListBO> page, WritingListPageVO writingListPageVO);

    /**
     * 更新附件上传状态
     *
     * @param writingBatchId
     */
    void updateUpload(String writingBatchId);

    EvaluationSheet getByWritingBatchId(String writingBatchId);

    List<writingListUserVO> getUserId(String writingBatchId);

    void joinEvaluationSheet(String writingBatchId, List<String> ids);

    List<String> writingBatch();

    IPage<DataReportBO> reportDetailPage(WritingBatchVO writingBatchVO);

    EvaluationSheet evaluationSheetDetail(String id);

    IPage<ExpertDetailVO> expertDetailPage(WritingBatchVO writingBatchVO);
}
