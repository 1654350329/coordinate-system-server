package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tree.clouds.coordination.model.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2021-04-05
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<Long> getNavMenuIds(@Param("userId") String userId);
}
