package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;

public interface WritingResultService {
    IPage<WritingResultBO> writingResultPage(WritingResultPageVO writingResultPageVO);

    void writingBuild(String writingBatchId);
}
