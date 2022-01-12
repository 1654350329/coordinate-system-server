package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.UserManageBO;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.model.vo.UserManagePageVO;
import com.tree.clouds.coordination.model.vo.UserManageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface UserManageService extends IService<UserManage> {

    void rebuildPassword(List<String> ids);

    void userStatus(List<String> ids, int status);

    void importUser(MultipartFile file);

    void exportUser(List<String> ids, HttpServletResponse response);

    IPage<UserManageVO> userManagePage(UserManagePageVO userManagePageVO);

    UserManage getUserByAccount(String account);

    String getUserAuthorityInfo(String userId);

    void addUserManage(UserManageBO userManageBO);

    void updateUserManage(UserManageBO userManageBO);

    void deleteUserManage(String userId);

    void clearUserAuthorityInfo(String userId);

    void clearUserAuthorityInfoByMenuId(Long menuId);
}
