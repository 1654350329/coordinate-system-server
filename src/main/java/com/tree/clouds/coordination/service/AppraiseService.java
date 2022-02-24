package com.tree.clouds.coordination.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tree.clouds.coordination.model.bo.AppraiseBO;
import com.tree.clouds.coordination.model.entity.Appraise;
import com.tree.clouds.coordination.model.vo.AppraiseInfoVO;
import com.tree.clouds.coordination.model.vo.AppraisePageVO;
import com.tree.clouds.coordination.model.vo.AppraiseVO;
import com.tree.clouds.coordination.model.vo.ExpertGroupInfo;

import java.util.List;

/**
 * <p>
 * 鉴定表 服务类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
public interface AppraiseService extends IService<Appraise> {

    IPage<AppraiseBO> appraisePage(AppraisePageVO appraisePageVO);

    void addAppraise(AppraiseVO appraiseVO);

    Appraise getByWritingBatchId(String writingBatchId);

    /**
     * 获取认定批号
     *
     * @param s
     * @return
     */
    Integer getAppraiseNumber(String s);

    Appraise getByReportId(String reportId);

    List<ExpertGroupInfo> expertGroupInfo(String reportId);

    AppraiseInfoVO appraiseInfoVO(String reportId);
}
