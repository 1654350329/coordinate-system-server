package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.WritingBatchMapper;
import com.tree.clouds.coordination.model.entity.WritingBatch;
import com.tree.clouds.coordination.service.WritingBatchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 文书编号;(一个文书编号有多个资料上报) 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class WritingBatchServiceImpl extends ServiceImpl<WritingBatchMapper, WritingBatch> implements WritingBatchService {

    @Override
    public List<String> getReportByWritingBatchId(String writingBatchId) {
        QueryWrapper<WritingBatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WritingBatch.WRITING_BATCH_ID, writingBatchId);
        return this.list(queryWrapper).stream().map(WritingBatch::getReportId).collect(Collectors.toList());
    }

    @Override
    public void saveBatchInfo(String writingBatchId, List<String> ids) {
        List<WritingBatch> writingBatches = ids.stream().map(id -> {
            //处理评估与上报资料中间表
            WritingBatch writingBatch = new WritingBatch();
            writingBatch.setReportId(id);
            writingBatch.setWritingBatchId(writingBatchId);
            return writingBatch;
        }).collect(Collectors.toList());
        this.saveBatch(writingBatches);
    }

    @Override
    public boolean getEvaluationSheetStatus(String reportId) {
        return CollUtil.isNotEmpty(this.list(new QueryWrapper<WritingBatch>().eq(WritingBatch.REPORT_ID, reportId)));
    }

    @Override
    public void removeByReportId(String reportId) {
        QueryWrapper<WritingBatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WritingBatch.REPORT_ID, reportId);
        this.remove(queryWrapper);
    }

    @Override
    public String getByReportId(String reportId) {
        QueryWrapper<WritingBatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WritingBatch.REPORT_ID, reportId);
        WritingBatch writingBatch = this.getOne(queryWrapper);
        if (writingBatch == null) {
            return null;
        }
        return writingBatch.getWritingBatchId();
    }
}
