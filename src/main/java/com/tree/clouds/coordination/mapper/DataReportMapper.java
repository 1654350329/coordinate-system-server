package com.tree.clouds.coordination.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.bo.ReportDetailInfoBO;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import com.tree.clouds.coordination.model.vo.WritingBatchVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资料上报 Mapper 接口
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface DataReportMapper extends BaseMapper<DataReport> {

    IPage<DataReportBO> selectDataReport(IPage<DataReportBO> page, @Param("dataReportPageVO") DataReportPageVO dataReportPageVO);

    IPage<DataReportBO> getDataReportPage(IPage<DataReportBO> page, @Param("writingBatchVO") WritingBatchVO writingBatchVO);

    List<ReportDetailInfoBO> getDetailInfo(@Param("reports") List<String> reports);

}
