package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.MessageInfoBO;
import com.tree.clouds.coordination.model.entity.MessageInfo;
import com.tree.clouds.coordination.model.vo.MessageInfoPage;

/**
 * <p>
 * 结论送达情况 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface MessageInfoService extends IService<MessageInfo> {

    IPage<MessageInfoBO> messageInfoPage(MessageInfoPage messageInfoPage);

    void completionMethod(String messageId, String type);

    void completionTime(String messageId, String time);

    String getWritingFile(String reportId);
}
