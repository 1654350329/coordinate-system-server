package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tree.clouds.coordination.mapper.ReviewSignatureMapper;
import com.tree.clouds.coordination.model.bo.ReviewSignatureBO;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.entity.ReviewSignature;
import com.tree.clouds.coordination.model.vo.AppraisalReviewPageVO;
import com.tree.clouds.coordination.model.vo.ReviewSignatureVO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;
import com.tree.clouds.coordination.service.DataReportService;
import com.tree.clouds.coordination.service.EvaluationSheetService;
import com.tree.clouds.coordination.service.ReviewSignatureService;
import com.tree.clouds.coordination.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * <p>
 * 认定审签 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class ReviewSignatureServiceImpl extends ServiceImpl<ReviewSignatureMapper, ReviewSignature> implements ReviewSignatureService {
    @Autowired
    private DataReportService dataReportService;
    @Autowired
    private EvaluationSheetService evaluationSheetService;

    @Override
    public IPage<ReviewSignatureBO> reviewSignaturePage(AppraisalReviewPageVO appraisePageVO) {
        IPage<ReviewSignatureBO> page = appraisePageVO.getPage();
        return this.baseMapper.reviewSignaturePage(page, appraisePageVO);
    }

    @Override
    @Transactional
    public Boolean addReviewSignature(ReviewSignatureVO reviewSignatureVO) {
        ReviewSignature signature = this.getById(reviewSignatureVO.getReviewAndSignatureId());
        ReviewSignature reviewSignature = BeanUtil.toBean(reviewSignatureVO, ReviewSignature.class);
        reviewSignature.setReviewUser(LoginUserUtil.getUserId());
        if (reviewSignatureVO.getReviewResult().equals("1")) {
            //审核进度完成
            dataReportService.updateDataExamine(Collections.singletonList(signature.getReportId()), DataReport.EXAMINE_PROGRESS_SEVEN, null);
            reviewSignature.setReviewResult("1");
            reviewSignature.setReviewStatus("1");
            if (evaluationSheetService.isCompleteStatus(signature.getWritingBatchId())) {
                evaluationSheetService.updateCompleteStatus(signature.getWritingBatchId());
            }
        } else {
            //审核进度失败驳回
            dataReportService.updateDataExamine(Collections.singletonList(signature.getReportId()), DataReport.EXAMINE_PROGRESS_ZERO, reviewSignatureVO.getRemark());
            reviewSignature.setReviewResult("0");
            reviewSignature.setReviewStatus("1");
            reviewSignature.setRemark(reviewSignatureVO.getRemark());
        }
        this.updateById(reviewSignature);
        return true;
    }

    @Override
    public IPage<WritingResultBO> writingResultPage(IPage<WritingResultBO> page, WritingResultPageVO writingResultPageVO) {
        return this.baseMapper.writingResultPage(page, writingResultPageVO);
    }

    @Override
    public ReviewSignature getByReportId(String reportId) {
        QueryWrapper<ReviewSignature> wrapper = new QueryWrapper<>();
        wrapper.eq(ReviewSignature.REPORT_ID, reportId);
        return getOne(wrapper);
    }

}
