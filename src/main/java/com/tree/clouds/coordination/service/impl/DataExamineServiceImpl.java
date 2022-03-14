package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.DataExamineMapper;
import com.tree.clouds.coordination.model.bo.DataExamineBo;
import com.tree.clouds.coordination.model.entity.DataExamine;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.vo.DataExamineVO;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;
import com.tree.clouds.coordination.service.DataExamineService;
import com.tree.clouds.coordination.service.DataReportService;
import com.tree.clouds.coordination.service.UserManageService;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 资料初审 服务实现类
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
@Service
public class DataExamineServiceImpl extends ServiceImpl<DataExamineMapper, DataExamine> implements DataExamineService {
    @Autowired
    private DataReportService dataReportService;
    @Autowired
    private UserManageService userManageService;

    /**
     * 添加审核
     *
     * @param dataExamineVOS
     */
    @Override
    public void addDataExamine(List<DataExamineVO> dataExamineVOS) {
        for (DataExamineVO dataExamineVO : dataExamineVOS) {
            DataExamine examine = this.getById(dataExamineVO.getDataExamineId());
            DataReport report = this.dataReportService.getById(examine.getReportId());
            if (dataExamineVO.getUpdateTime() == null || !report.getUpdatedTime().equals(dataExamineVO.getUpdateTime())) {
                throw new BaseBusinessException(400, "审核数据已修改,请重新刷新数据!");
            }
            if (examine.getExamineStatus() == 1) {
                throw new BaseBusinessException(400, "审核已完成,不能修改数据!");
            }
            DataExamine dataExamine = BeanUtil.toBean(dataExamineVO, DataExamine.class);
            dataExamine.setExamineUser(LoginUserUtil.getUserId());
            dataExamine.setExamineTime(DateUtil.format(new Date(), "YYYY-MM-dd"));
            dataExamine.setExamineStatus(1);
            this.updateById(dataExamine);
            //修改审核成功状态为到3鉴定 失败为0初始
            if (dataExamineVO.getStatus() == 0) {
                dataReportService.updateDataExamine(Collections.singletonList(report.getReportId()), DataReport.EXAMINE_PROGRESS_ZERO, dataExamineVO.getExamineDescribe());
            } else {
                dataReportService.updateDataExamine(Collections.singletonList(report.getReportId()), DataReport.EXAMINE_PROGRESS_TWO, null);
            }

        }
    }

    @Override
    public IPage<DataExamineBo> dataExaminePage(DataReportPageVO dataReport) {
        IPage<DataExamineBo> page = dataReport.getPage();
        page = this.baseMapper.dataExaminePage(page, dataReport);
        for (DataExamineBo record : page.getRecords()) {
            if (record.getCreatedUser() != null) {
                record.setCreatedUser(userManageService.getById(record.getCreatedUser()).getUserName());
            }
        }
        return page;
    }

    @Override
    public boolean getReportStatus(String id) {
        QueryWrapper<DataExamine> wrapper = new QueryWrapper<>();
        wrapper.eq(DataExamine.REPORT_ID, id);
        DataExamine examine = this.getOne(wrapper);
        if (examine == null) {
            return false;
        }
        return examine.getExamineStatus() == 1;
    }

    @Override
    public void removeByReportId(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put(DataExamine.REPORT_ID, id);
        this.removeByMap(map);
    }

}
