package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.UserManageBO;
import com.tree.clouds.coordination.model.vo.PublicIdsReqVO;
import com.tree.clouds.coordination.model.vo.UserManagePageVO;
import com.tree.clouds.coordination.model.vo.UserManageVO;
import com.tree.clouds.coordination.service.UserManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/user-manage")
@Api(value = "user-manage", tags = "用户管理模块")
public class UserManageController {

    @Autowired
    private UserManageService userManageservice;


    @Log("用户模块分页查询")
    @PostMapping("/userManagetrue")
    @ApiOperation(value = "用户模块分页查询")
    @PreAuthorize("hasRole('Admin')")
    public Result userManage(@Validated @RequestBody UserManagePageVO userManagePageVO) {
        IPage<UserManageVO> page = userManageservice.userManagePage(userManagePageVO);
        return Result.succ(page);
    }

    @PostMapping("/addUserManage")
    @ApiOperation(value = "添加用户")
    @Log("添加用户")
    public Result addUserManage(@Validated @RequestBody UserManageBO userManageBO) {
        userManageservice.addUserManage(userManageBO);
        return Result.succ(true);
    }

    @PostMapping("/updateUserManage")
    @ApiOperation(value = "修改用户")
    @Log("修改用户")
    public Result updateUserManage(@Validated @RequestBody UserManageBO userManageBO) {
        userManageservice.updateUserManage(userManageBO);
        return Result.succ(true);
    }

    @PostMapping("/deleteUserManage/{userId}")
    @ApiOperation(value = "刪除用户")
    @Log("刪除用户")
    public Result deleteUserManage(@PathVariable String userId) {
        userManageservice.deleteUserManage(userId);
        return Result.succ(true);
    }

    @PostMapping("/rebuildPassword")
    @ApiOperation(value = "重置密码")
    @Log("重置密码")
    public Result rebuildPassword(@RequestBody PublicIdsReqVO publicIdsReqVO) {
        userManageservice.rebuildPassword(publicIdsReqVO.getIds());
        return Result.succ(true);
    }

    @PostMapping("/userStatus/{status}")
    @ApiOperation(value = "启用或停用用户")
    @Log("启用或停用用户")
    public Result userStatus(@PathVariable int status, @RequestBody PublicIdsReqVO publicIdsReqVO) {
        userManageservice.userStatus(publicIdsReqVO.getIds(), status);
        return Result.succ(true);
    }

    @PostMapping("/importUser")
    @ApiOperation(value = "导入用户")
    @Log("导入用户")
    public Result importUser(@RequestParam("file") MultipartFile file) {
        userManageservice.importUser(file);
        return Result.succ(true);
    }

    @PostMapping("/exportUser")
    @ApiOperation(value = "导出用户")
    @Log("导出用户")
    public void exportUser(@RequestBody PublicIdsReqVO publicIdsReqVO, HttpServletResponse response) {
        userManageservice.exportUser(publicIdsReqVO.getIds(), response);
    }
}

