package com.tree.clouds.coordination.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tree.clouds.coordination.common.Result;
import com.tree.clouds.coordination.model.bo.SysMenuDto;
import com.tree.clouds.coordination.model.entity.SysMenu;
import com.tree.clouds.coordination.model.entity.SysRoleMenu;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.service.SysMenuService;
import com.tree.clouds.coordination.service.SysRoleMenuService;
import com.tree.clouds.coordination.service.UserManageService;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-04-05
 */
@RestController
@RequestMapping("/sys/menu")
@Api(value = "menu", tags = "菜单管理模块")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private UserManageService userManageService;

    @GetMapping("/nav")
    @ApiOperation(value = "当前用户的菜单和权限信息")
    public Result nav() {

        UserManage userByAccount = userManageService.getUserByAccount(LoginUserUtil.getUserAccount());

        // 获取权限信息
        String authorityInfo = userManageService.getUserAuthorityInfo(userByAccount.getUserId());// ROLE_admin,ROLE_normal,sys:user:list,....
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

        // 获取导航栏信息
        List<SysMenuDto> navs = sysMenuService.getCurrentUserNav();

        return Result.succ(MapUtil.builder()
                .put("authoritys", authorityInfoArray)
                .put("nav", navs)
                .map()
        );
    }

    @GetMapping("/info/{id}")
//    @PreAuthorize("hasAuthority('sys:menu:list')")
    @ApiOperation(value = "菜单信息")
    public Result info(@PathVariable(name = "id") Long id) {
        return Result.succ(sysMenuService.getById(id));
    }

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('sys:menu:list')")
    @ApiOperation(value = "菜单树")
    public Result list() {

        List<SysMenu> menus = sysMenuService.tree();
        return Result.succ(menus);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    @ApiOperation(value = "菜单保存")
    public Result save(@Validated @RequestBody SysMenu sysMenu) {

//        sysMenu.setCreated(LocalDateTime.now());

        sysMenuService.save(sysMenu);
        return Result.succ(sysMenu);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @ApiOperation(value = "菜单更新")
    public Result update(@Validated @RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);

        // 清除所有与该菜单相关的权限缓存
        userManageService.clearUserAuthorityInfoByMenuId(sysMenu.getId());
        return Result.succ(sysMenu);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @ApiOperation(value = "菜单删除")
    public Result delete(@PathVariable("id") Long id) {

        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return Result.fail("请先删除子菜单");
        }

        // 清除所有与该菜单相关的权限缓存
        userManageService.clearUserAuthorityInfoByMenuId(id);

        sysMenuService.removeById(id);

        // 同步删除中间关联表
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
        return Result.succ("");
    }
}
