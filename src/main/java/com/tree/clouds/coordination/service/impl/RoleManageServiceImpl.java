package com.tree.clouds.coordination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.RoleManageMapper;
import com.tree.clouds.coordination.mapper.RoleUserMapper;
import com.tree.clouds.coordination.model.entity.RoleManage;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.model.vo.RoleManagePageVO;
import com.tree.clouds.coordination.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色管理表 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class RoleManageServiceImpl extends ServiceImpl<RoleManageMapper, RoleManage> implements RoleManageService {

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Override
    public List<UserManage> getUserInfoByRole(String roleName) {
        return roleUserMapper.getUserInfoByRole(roleName);
    }

    @Override
    public IPage<RoleManage> roleManagePage(RoleManagePageVO roleManagePageVO) {
        IPage<RoleManage> page = roleManagePageVO.getPage();
        return this.baseMapper.roleManagePage(page, roleManagePageVO);
    }

}
