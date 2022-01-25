package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.RestResponse;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.bo.UserManageBO;
import com.tree.clouds.coordination.model.vo.PublicIdReqVO;
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
    @PostMapping("/userManagePage")
    @ApiOperation(value = "用户模块分页查询")
    @PreAuthorize("hasRole('Admin')")
    public RestResponse<IPage<UserManageVO>> userManagePage(@Validated @RequestBody UserManagePageVO userManagePageVO) {
        IPage<UserManageVO> page = userManageservice.userManagePage(userManagePageVO);
        return RestResponse.ok(page);
    }

    @PostMapping("/addUserManage")
    @ApiOperation(value = "添加用户")
    @Log("添加用户")
    public RestResponse<Boolean> addUserManage(@Validated @RequestBody UserManageBO userManageBO) {
        userManageservice.addUserManage(userManageBO);
        return RestResponse.ok(true);
    }

    @PostMapping("/updateUserManage")
    @ApiOperation(value = "修改用户")
    @Log("修改用户")
    public RestResponse<Boolean> updateUserManage(@Validated @RequestBody UserManageBO userManageBO) {
        userManageservice.updateUserManage(userManageBO);
        return RestResponse.ok(true);
    }

    @PostMapping("/deleteUserManage")
    @ApiOperation(value = "刪除用户")
    @Log("刪除用户")
    public RestResponse<Boolean> deleteUserManage(@Validated @RequestBody PublicIdReqVO publicIdReqVO) {
        userManageservice.deleteUserManage(publicIdReqVO.getId());
        return RestResponse.ok(true);
    }

    @PostMapping("/rebuildPassword")
    @ApiOperation(value = "重置密码")
    @Log("重置密码")
    public RestResponse<Boolean> rebuildPassword(@RequestBody PublicIdsReqVO publicIdsReqVO) {
        userManageservice.rebuildPassword(publicIdsReqVO.getIds());
        return RestResponse.ok(true);
    }

    @PostMapping("/userStatus/{status}")
    @ApiOperation(value = "启用或停用用户")
    @Log("启用或停用用户")
    public RestResponse<Boolean> userStatus(@PathVariable int status, @RequestBody PublicIdsReqVO publicIdsReqVO) {
        userManageservice.userStatus(publicIdsReqVO.getIds(), status);
        return RestResponse.ok(true);
    }

    @PostMapping("/importUser")
    @ApiOperation(value = "导入用户")
    @Log("导入用户")
    public RestResponse<Boolean> importUser(@RequestParam("file") MultipartFile file) {
        userManageservice.importUser(file);
        return RestResponse.ok(true);
    }

    @PostMapping("/exportUser")
    @ApiOperation(value = "导出用户")
    @Log("导出用户")
    public void exportUser(@RequestBody PublicIdsReqVO publicIdsReqVO, HttpServletResponse response) {
        userManageservice.exportUser(publicIdsReqVO.getIds(), response);
    }
}

