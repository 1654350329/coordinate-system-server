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
import com.tree.clouds.coordination.utils.BaseBusinessException;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (!report.getUpdatedTime().equals(dataExamineVO.getUpdateTime())) {
                throw new BaseBusinessException(400, "审核数据已修改,请重新刷新数据!");
            }
            DataExamine dataExamine = BeanUtil.toBean(dataExamineVO, DataExamine.class);
            dataExamine.setExamineUser(LoginUserUtil.getUserId());
            dataExamine.setExamineTime(DateUtil.format(new Date(), "YYYY-MM-DD"));
            this.updateById(dataExamine);

            DataReport dataReport = new DataReport();
            dataReport.setReportId(examine.getReportId());
            //修改审核成功状态为到3鉴定 失败为1上报
            if (dataExamineVO.getExamineStatus() == 0) {
                dataReport.setExamineProgress(DataReport.EXAMINE_PROGRESS_ONE);
            } else {
                dataReport.setExamineProgress(DataReport.EXAMINE_PROGRESS_THREE);
            }
            dataReportService.updateById(dataReport);
        }
    }

    @Override
    public IPage<DataExamineBo> dataExaminePage(DataReportPageVO dataReport) {
        IPage<DataExamineBo> page = dataReport.getPage();
        return this.baseMapper.dataExaminePage(page, dataReport);
    }

    @Override
    public boolean getReportStatus(String id) {
        QueryWrapper<DataExamine> wrapper = new QueryWrapper<>();
        wrapper.eq(DataExamine.DATA_REPORT_ID, id);
        DataExamine examine = this.getOne(wrapper);
        return examine.getExamineStatus() == 1;
    }

    @Override
    public void removeByReportId(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put(DataExamine.DATA_REPORT_ID, id);
        this.removeByMap(map);
    }

}
