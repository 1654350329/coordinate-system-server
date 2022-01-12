package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.listener.UserManagerExcelListener;
import com.tree.clouds.coordination.mapper.SysRoleMenuMapper;
import com.tree.clouds.coordination.mapper.UserManageMapper;
import com.tree.clouds.coordination.model.bo.UserManageBO;
import com.tree.clouds.coordination.model.entity.RoleManage;
import com.tree.clouds.coordination.model.entity.SysMenu;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.model.vo.UserManagePageVO;
import com.tree.clouds.coordination.model.vo.UserManageVO;
import com.tree.clouds.coordination.service.GroupUserService;
import com.tree.clouds.coordination.service.RoleUserService;
import com.tree.clouds.coordination.service.SysMenuService;
import com.tree.clouds.coordination.service.UserManageService;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import com.tree.clouds.coordination.utils.MultipartFileUtil;
import com.tree.clouds.coordination.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import static com.tree.clouds.coordination.utils.DownloadFile.getEncoder;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
@Slf4j
public class UserManageServiceImpl extends ServiceImpl<UserManageMapper, UserManage> implements UserManageService {

    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private GroupUserService groupUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public void rebuildPassword(List<String> ids) {
        // 加密后密码
        String password = bCryptPasswordEncoder.encode("111111");

        List<UserManage> userManages = this.listByIds(ids);
        userManages.forEach(userManage -> userManage.setPassword(password));
        this.updateBatchById(userManages);
    }

    @Override
    public void userStatus(List<String> ids, int status) {
        List<UserManage> userManages = this.listByIds(ids);
        userManages.forEach(userManage -> userManage.setAccountStatus(status));
        this.updateBatchById(userManages);
    }

    @Override
    public void importUser(MultipartFile multipartFile) {
        try {
            File file = MultipartFileUtil.multipartFileToFile(multipartFile);
            EasyExcel.read(file, UserManage.class, new UserManagerExcelListener()).sheet(0).doRead();
        } catch (Exception e) {
            log.error("用户信息导入异常是:{}", e.getMessage());
            log.error("异常栈：", e);
            throw new BaseBusinessException(500, "用户信息导入异常");
        }
        //获取数据
        List<UserManage> userManages = UserManagerExcelListener.getDatas();
        this.saveBatch(userManages);
    }

    @Override
    public void exportUser(List<String> ids, HttpServletResponse response) {
        List<UserManage> userManages = this.listByIds(ids);
        String fileName = "用户信息模板.xlsx";
        String filename = getEncoder(fileName, null);
        //设置响应头，控制浏览器下载该文件
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "FileName");
        response.setHeader("FileName", filename);
        try {
            response.setHeader("FileName", URLEncoder.encode(fileName, "UTF-8"));
            EasyExcel.write(response.getOutputStream(), UserManage.class).autoCloseStream(Boolean.TRUE).sheet(fileName)
                    .doWrite(userManages);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public IPage<UserManageVO> userManagePage(UserManagePageVO userManagePageVO) {
        IPage<UserManageVO> page = userManagePageVO.getPage();
        IPage<UserManageVO> userManageVOIPage = this.baseMapper.userManagePage(page, userManagePageVO);
        List<UserManageVO> records = userManageVOIPage.getRecords();
        for (UserManageVO record : records) {
            List<RoleManage> roleManages = roleUserService.getRoleByUserId(record.getUserId());
            List<String> roleNames = roleManages.stream().map(RoleManage::getRoleName).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < roleNames.size(); i++) {
                if (i == roleNames.size() - 1) {
                    stringBuilder.append(roleNames.get(i));
                } else {
                    stringBuilder.append(roleNames.get(i)).append("-");
                }
            }
            record.setRoleName(stringBuilder.toString());
        }
        return userManageVOIPage;
    }

    @Override
    public UserManage getUserByAccount(String account) {
        QueryWrapper<UserManage> wrapper = new QueryWrapper<>();
        wrapper.eq(UserManage.ACCOUNT, account);
        return this.getOne(wrapper);
    }

    @Override
    public String getUserAuthorityInfo(String userId) {

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";
        if (redisUtil.hasKey("GrantedAuthority:" + userId)) {
            authority = (String) redisUtil.get("GrantedAuthority:" + userId);
        } else {
            //获取角色
            List<RoleManage> roles = this.roleUserService.getRoleByUserId(userId);
            authority = roles.stream().map(RoleManage::getRoleCode).collect(Collectors.joining(","));

            List<Long> menuIds = sysRoleMenuMapper.getNavMenuIds(userId);
            List<SysMenu> menus = this.sysMenuService.listByIds(menuIds);
            String perms = menus.stream().map(SysMenu::getPerms).collect(Collectors.joining(","));
            authority = authority.concat(",").concat(perms);
        }
        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String userId) {
        redisUtil.del("GrantedAuthority:" + userId);
    }

    @Override
    public void addUserManage(UserManageBO userManageBO) {
        UserManage userManage = BeanUtil.toBean(userManageBO, UserManage.class);
        this.save(userManage);
        //添加分组
        groupUserService.saveGroupUser(userManage.getUserId(), userManageBO.getGroupId());
        //绑定角色
        roleUserService.addRole(userManageBO.getRoleIds(), userManage.getUserId());

    }

    @Override
    public void updateUserManage(UserManageBO userManageBO) {
        UserManage userManage = BeanUtil.toBean(userManageBO, UserManage.class);
        this.updateById(userManage);
        //角色先删后增
        roleUserService.removeRole(userManage.getUserId());
        roleUserService.addRole(userManageBO.getRoleIds(), userManage.getUserId());
    }

    @Override
    public void deleteUserManage(String userId) {
        this.removeById(userId);
        roleUserService.removeRole(userId);
        groupUserService.removeUserByUserId(userId);
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<UserManage> sysUsers = this.baseMapper.listByMenuId(menuId);

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUserId());
        });
    }
}
