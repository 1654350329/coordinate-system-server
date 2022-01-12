package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.DataExamineBo;
import com.tree.clouds.coordination.model.entity.DataExamine;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 资料初审 Mapper 接口
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
public interface DataExamineMapper extends BaseMapper<DataExamine> {

    IPage<DataExamineBo> dataExaminePage(IPage<DataExamineBo> page, @Param("dataReportPageVO") DataReportPageVO dataReportPageVO);
}
