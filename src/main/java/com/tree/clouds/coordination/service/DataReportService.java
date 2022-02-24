package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import com.tree.clouds.coordination.model.vo.UpdateDataReportVO;
import com.tree.clouds.coordination.model.vo.WritingBatchVO;

import java.util.List;

/**
 * <p>
 * 资料上报 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface DataReportService extends IService<DataReport> {

    /**
     * 添加上报资料任务
     *
     * @param DataReportVO
     */
    void addDataReport(UpdateDataReportVO DataReportVO);

    /**
     * 修改上报资料
     *
     * @param DataReportVO
     */
    void updateDataReport(UpdateDataReportVO DataReportVO);

    /**
     * 删除上报资料
     *
     * @param id
     */
    void deleteDataReport(String id);

    /**
     * 带条件分页查询
     *
     * @param dataReportPageVO
     * @return
     */
    IPage<DataReportBO> dataReportPage(DataReportPageVO dataReportPageVO);

    /**
     * 评估准备条件分页查询
     *
     * @param writingBatchVO
     * @return
     */
    IPage<DataReportBO> dataReportPage(WritingBatchVO writingBatchVO);

    /**
     * 修改审核进度
     *
     * @param reportIds
     * @param progressStatus
     */
    void updateDataExamine(List<String> reportIds, int progressStatus, String remark);

    UpdateDataReportVO getDataReportDetail(String reportId);

    void report(List<String> ids);

    void revokeReport(String id);
}
