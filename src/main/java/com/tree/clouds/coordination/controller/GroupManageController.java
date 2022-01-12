package com.tree.clouds.coordination.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.common.aop.Log;
import com.tree.clouds.coordination.model.entity.GroupManage;
import com.tree.clouds.coordination.model.vo.GroupManagePageVO;
import com.tree.clouds.coordination.service.GroupManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 分组管理 前端控制器
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/group-manage")
@Api(value = "group-manage", tags = "分组管理模块")
public class GroupManageController {

    @Autowired
    private GroupManageService groupManageService;

    @PostMapping("/groupManagePage")
    @ApiOperation(value = "分组管理模块分页查询")
    @Log("分组管理模块分页查询")
    public Result groupManagePage(@RequestBody GroupManagePageVO groupManagePageVO) {
        IPage<GroupManage> page = groupManageService.groupManagePage(groupManagePageVO);
        return Result.succ(page);
    }

    @PostMapping("/addGroupRole")
    @ApiOperation(value = "添加分组")
    @Log("添加分组")
    public Result addGroupRole(@RequestBody GroupManage groupRole) {
        groupManageService.save(groupRole);
        return Result.succ(true);
    }

    @PostMapping("/updateGroupRole")
    @ApiOperation(value = "修改分组")
    @Log("修改分组")
    public Result updateGroupRole(@RequestBody GroupManage groupRole) {
        groupManageService.updateById(groupRole);
        return Result.succ(true);
    }

    @PostMapping("/deleteGroupRole/{groupId}")
    @ApiOperation(value = "刪除分组")
    @Log("刪除分组")
    public Result deleteGroupRole(@PathVariable String groupId) {
        groupManageService.removeById(groupId);
        return Result.succ(true);
    }
}

