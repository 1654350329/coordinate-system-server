package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.entity.RoleManage;
import com.tree.clouds.coordination.model.vo.RoleManagePageVO;
import com.tree.clouds.coordination.service.RoleManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色管理表 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/role-manage")
@Api(value = "role-manage", tags = "角色管理模块")
public class RoleManageController {
    @Autowired
    private RoleManageService roleManageService;

    @PostMapping("/roleManagePage")
    @ApiOperation(value = "角色模块分页查询")
    @Log("角色模块分页查询")
    public Result roleManagePage(@RequestBody RoleManagePageVO roleManagePageVO) {
        IPage<RoleManage> page = roleManageService.roleManagePage(roleManagePageVO);
        return Result.succ(page);
    }

    @PostMapping("/addRole")
    @ApiOperation(value = "添加角色")
    @Log("添加角色")
    public Result addRole(@RequestBody RoleManage roleManage) {
        roleManageService.save(roleManage);
        return Result.succ(true);
    }

    @PostMapping("/updateRole")
    @ApiOperation(value = "修改角色")
    @Log("修改角色")
    public Result updateRole(@RequestBody RoleManage roleManage) {
        roleManageService.updateById(roleManage);
        return Result.succ(true);
    }

    @PostMapping("/deleteRole/{roleId}")
    @ApiOperation(value = "刪除角色")
    @Log("刪除角色")
    public Result deleteRole(@PathVariable String roleId) {
        roleManageService.removeById(roleId);
        return Result.succ(true);
    }
}

