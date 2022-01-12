package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.vo.WritingListDetailVO;
import com.tree.clouds.coordination.model.vo.WritingListPageVO;
import com.tree.clouds.coordination.model.vo.WritingListVO;

public interface WritingListService {
    IPage<WritingListBO> writingListPage(WritingListPageVO writingListPageVO);

    void writingListUpload(WritingListVO writingListVO);

    WritingListDetailVO writingListDetail(String writingBatchId);
}
