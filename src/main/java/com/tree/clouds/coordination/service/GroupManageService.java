package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.entity.GroupManage;
import com.tree.clouds.coordination.model.vo.GroupManagePageVO;

/**
 * <p>
 * 分组管理 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface GroupManageService extends IService<GroupManage> {

    IPage<GroupManage> groupManagePage(GroupManagePageVO groupManagePageVO);
}
