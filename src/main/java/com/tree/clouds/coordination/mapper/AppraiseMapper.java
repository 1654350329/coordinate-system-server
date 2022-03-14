package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.AppraiseBO;
import com.tree.clouds.coordination.model.entity.Appraise;
import com.tree.clouds.coordination.model.vo.AppraisePageVO;
import com.tree.clouds.coordination.model.vo.ExpertGroupInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 鉴定表 Mapper 接口
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface AppraiseMapper extends BaseMapper<Appraise> {

    IPage<AppraiseBO> appraisePage(IPage<AppraiseBO> page, @Param("appraisePageVO") AppraisePageVO appraisePageVO);

    String getAppraiseNumber(@Param("time") String time, @Param("type") String type);

    List<ExpertGroupInfo> expertGroupInfo(@Param("reportId") String reportId);
}
