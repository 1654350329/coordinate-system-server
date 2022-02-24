package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.MessageInfoBO;
import com.tree.clouds.coordination.model.entity.MessageInfo;
import com.tree.clouds.coordination.model.vo.MessageInfoPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 结论送达情况 Mapper 接口
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {

    IPage<MessageInfoBO> messageInfoPage(IPage<MessageInfoBO> page, @Param("messageInfoPage") MessageInfoPage messageInfoPage);

    String getWritingFile(String reportId);
}
