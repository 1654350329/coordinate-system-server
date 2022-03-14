package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.entity.WritingBatch;

import java.util.List;

/**
 * <p>
 * 文书编号;(一个文书编号有多个资料上报) 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface WritingBatchService extends IService<WritingBatch> {

    /**
     * 根据行文批号获取资料上报主键
     *
     * @param writingBatchId
     * @return
     */
    List<String> getReportByWritingBatchId(String writingBatchId);

    void saveBatchInfo(String writingBatchId, List<String> ids);

    /**
     * 判断上报任务是否已加入待审表
     *
     * @param reportId
     * @return
     */
    boolean getEvaluationSheetStatus(String reportId);

    void removeByReportId(String reportId);

    String getByReportId(String reportId);
}
