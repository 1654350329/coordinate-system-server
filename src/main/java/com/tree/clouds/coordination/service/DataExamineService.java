package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.DataExamineBo;
import com.tree.clouds.coordination.model.entity.DataExamine;
import com.tree.clouds.coordination.model.vo.DataExamineVO;
import com.tree.clouds.coordination.model.vo.DataReportPageVO;

import java.util.List;

/**
 * <p>
 * 资料初审 服务类
 * </p>
 *
 * @author LZK
 * @since 2022-01-02
 */
public interface DataExamineService extends IService<DataExamine> {
    /**
     * 审核
     *
     * @param dataExamineVOS
     */
    void addDataExamine(List<DataExamineVO> dataExamineVOS);

    IPage<DataExamineBo> dataExaminePage(DataReportPageVO dataReport);

    boolean getReportStatus(String id);

    /**
     * 根据报送资料主键删除审核
     *
     * @param id
     */
    void removeByReportId(String id);
}
