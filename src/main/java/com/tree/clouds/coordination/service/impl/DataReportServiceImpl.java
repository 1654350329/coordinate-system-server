package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.DataReportMapper;
import com.tree.clouds.coordination.model.bo.DataReportBO;
import com.tree.clouds.coordination.model.entity.DataExamine;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.entity.FileInfo;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import com.tree.clouds.coordination.model.vo.FileInfoVO;
import com.tree.clouds.coordination.model.vo.UpdateDataReportVO;
import com.tree.clouds.coordination.service.DataExamineService;
import com.tree.clouds.coordination.service.DataReportService;
import com.tree.clouds.coordination.service.FileInfoService;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资料上报 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class DataReportServiceImpl extends ServiceImpl<DataReportMapper, DataReport> implements DataReportService {

    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private DataExamineService dataExamineService;

    @Transient
    public void addDataReport(UpdateDataReportVO updateDataReportVO) {
        DataReport dataReport = BeanUtil.toBean(updateDataReportVO, DataReport.class);
        dataReport.setExamineProgress(DataReport.EXAMINE_PROGRESS_ONE);
        this.save(dataReport);
        fileInfoService.saveFileInfo(updateDataReportVO.getBizFile(), dataReport.getReportId());
    }

    @Transient
    public void updateDataReport(UpdateDataReportVO updateDataReportVO) {
        if (dataExamineService.getReportStatus(updateDataReportVO.getReportId())) {
            throw new BaseBusinessException(400, "已审核完成,无法修改!");
        }
        DataReport dataReport = BeanUtil.toBean(updateDataReportVO, DataReport.class);
        dataReport.setExamineProgress(0);
        this.updateById(dataReport);
        fileInfoService.deleteByBizIds(Collections.singletonList(dataReport.getReportId()));
        fileInfoService.saveFileInfo(updateDataReportVO.getBizFile(), dataReport.getReportId());
    }

    @Transient
    public void deleteDataReport(String id) {
        this.removeById(id);
        fileInfoService.deleteByBizIds(Collections.singletonList(id));
    }

    public IPage<DataReportBO> dataReportPage(DataReportPageVO dataReportPageVO) {
        IPage<DataReportBO> page = dataReportPageVO.getPage();
        return this.baseMapper.selectDataReport(page, dataReportPageVO);
    }

    /**
     * 修改审核进度
     *
     * @param reportIds      资料上报主键
     * @param progressStatus 审核进度状态
     */
    @Override
    public void updateDataExamine(List<String> reportIds, int progressStatus) {
        if (progressStatus == DataReport.EXAMINE_PROGRESS_ZERO) {
            for (String reportId : reportIds) {
                //修改原有的上报资料状态并作废
                DataReport report = this.getById(reportId);
                report.setDel(1);
                report.setStatus(1);
                report.setExamineProgress(0);
                this.updateById(report);
                //重新生成新的上报资料
                report.setReportId(null);
                report.setDel(0);
                this.save(report);
            }
        } else {
            List<DataReport> reports = reportIds.stream().map(id -> {
                DataReport dataReport = new DataReport();
                dataReport.setReportId(id);
                dataReport.setExamineProgress(progressStatus);
                return dataReport;
            }).collect(Collectors.toList());
            this.updateBatchById(reports);
        }

    }

    @Override
    public UpdateDataReportVO getDataReportDetail(String reportId) {
        DataReport dataReport = this.getById(reportId);
        List<FileInfo> infos = this.fileInfoService.getByBizIdsAndType(dataReport.getReportId(), null);
        List<FileInfoVO> fileInfoVOS = infos.stream().map(fileInfo -> BeanUtil.toBean(fileInfo, FileInfoVO.class)).collect(Collectors.toList());
        UpdateDataReportVO dataReportVO = BeanUtil.toBean(dataReport, UpdateDataReportVO.class);
        dataReportVO.setBizFile(fileInfoVOS);
        return dataReportVO;
    }

    @Override
    public void report(List<String> ids) {
        updateDataExamine(ids, DataReport.EXAMINE_PROGRESS_ONE);
        for (String id : ids) {
            DataExamine dataExamine = new DataExamine();
            dataExamine.setReportId(id);
            dataExamineService.save(dataExamine);
        }
    }

    @Override
    public void revokeReport(String id) {
        if (dataExamineService.getReportStatus(id)) {
            throw new BaseBusinessException(400, "已审核完成,无法撤销!");
        }
        dataExamineService.removeByReportId(id);
        DataReport report = new DataReport();
        report.setReportId(id);
        report.setExamineProgress(DataReport.EXAMINE_PROGRESS_ZERO);
        this.updateById(report);
    }
}
