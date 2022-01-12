package com.tree.clouds.coordination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.GroupManageMapper;
import com.tree.clouds.coordination.model.entity.GroupManage;
import com.tree.clouds.coordination.model.vo.GroupManagePageVO;
import com.tree.clouds.coordination.service.GroupManageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分组管理 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class GroupManageServiceImpl extends ServiceImpl<GroupManageMapper, GroupManage> implements GroupManageService {

    @Override
    public IPage<GroupManage> groupManagePage(GroupManagePageVO groupManagePageVO) {
        IPage<GroupManage> page = groupManagePageVO.getPage();
        return this.baseMapper.groupManagePage(page, groupManagePageVO);
    }
}
