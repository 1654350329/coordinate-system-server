package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.MessageInfoMapper;
import com.tree.clouds.coordination.model.bo.MessageInfoBO;
import com.tree.clouds.coordination.model.entity.MessageInfo;
import com.tree.clouds.coordination.model.vo.MessageInfoPage;
import com.tree.clouds.coordination.service.MessageInfoService;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 结论送达情况 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements MessageInfoService {

    @Override
    public IPage<MessageInfoBO> messageInfoPage(MessageInfoPage messageInfoPage) {
        IPage<MessageInfoBO> page = messageInfoPage.getPage();
        return this.baseMapper.messageInfoPage(page, messageInfoPage);
    }

    @Override
    public void completionMethod(String messageId, String type) {
        MessageInfo messageInfo = this.getById(messageId);
        if (StrUtil.isNotBlank(messageInfo.getCompletionTime())) {
            throw new BaseBusinessException(500, "已完成送达不可以修改发送方式!");
        }
        messageInfo.setCompletionMethod(type);
        this.updateById(messageInfo);
    }

    @Override
    public void completionTime(String messageId, String time) {
        MessageInfo messageInfo = this.getById(messageId);
        messageInfo.setCompletionTime(time);
        messageInfo.setMessageStatus("1");
        this.updateById(messageInfo);
    }

    @Override
    public String getWritingFile(String reportId) {
        return this.baseMapper.getWritingFile(reportId);

    }
}
