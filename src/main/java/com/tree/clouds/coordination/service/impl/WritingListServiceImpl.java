package com.tree.clouds.coordination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tree.clouds.coordination.mapper.DataReportMapper;
import com.tree.clouds.coordination.model.bo.ReportDetailInfoBO;
import com.tree.clouds.coordination.model.bo.WritingListBO;
import com.tree.clouds.coordination.model.entity.EvaluationSheet;
import com.tree.clouds.coordination.model.entity.UserManage;
import com.tree.clouds.coordination.model.vo.*;
import com.tree.clouds.coordination.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 人员行文名单 服务实现类
 * </p>
 *
 * @author lzk
 * @since 2021-12-28
 */
@Service
public class WritingListServiceImpl implements WritingListService {

    @Autowired
    private DataReportMapper dataReportMapper;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private WritingBatchService writingBatchService;
    @Autowired
    private UserManageService userManageService;
    @Autowired
    private EvaluationSheetService evaluationSheetService;


    @Override
    public IPage<WritingListBO> writingListPage(WritingListPageVO writingListPageVO) {
        IPage<WritingListBO> page = writingListPageVO.getPage();
        return this.evaluationSheetService.writingListPage(page, writingListPageVO);
    }

    @Override
    public void writingListUpload(WritingListVO writingListVO) {
        FileInfoVO fileInfoVO = writingListVO.getFileInfoVO();
        fileInfoVO.setType("5");
        this.fileInfoService.saveFileInfo(Collections.singletonList(fileInfoVO), writingListVO.getWritingBatchId());
        this.evaluationSheetService.updateUpload(writingListVO.getWritingBatchId());
    }

    @Override
    public WritingListDetailVO writingListDetail(String writingBatchId) {
        WritingListDetailVO writingListDetailVO = new WritingListDetailVO();
        EvaluationSheet evaluationSheet = evaluationSheetService.getByWritingBatchId(writingBatchId);
        writingListDetailVO.setTime(evaluationSheet.getYear() + "年" + evaluationSheet.getMonth() + "月");
        List<String> reports = writingBatchService.getReportByWritingBatchId(writingBatchId);
        List<ReportDetailInfoBO> detailInfoBOS = dataReportMapper.getDetailInfo(reports);
        writingListDetailVO.setDataReports(detailInfoBOS);

        List<String> appraisalReviewUsers = new ArrayList<>();
        List<String> examineUsers = new ArrayList<>();
        List<String> reviewUsers = new ArrayList<>();
        List<writingListUserVO> writingListUserVOS = evaluationSheetService.getUserId(writingBatchId);
        for (writingListUserVO writingListUserVO : writingListUserVOS) {
            appraisalReviewUsers.add(writingListUserVO.getAppraisalReviewUserTwo());
            examineUsers.add(writingListUserVO.getExamineUser());
            reviewUsers.add(writingListUserVO.getReviewUser());
        }

        String appraisalReviewUser = userManageService.listByIds(appraisalReviewUsers).stream().map(UserManage::getUserName).collect(Collectors.joining(" "));
        String examineUser = userManageService.listByIds(examineUsers).stream().map(UserManage::getUserName).collect(Collectors.joining(" "));
        String reviewUser = userManageService.listByIds(reviewUsers).stream().map(UserManage::getUserName).collect(Collectors.joining(" "));
        writingListDetailVO.setReviewSignatureUser(appraisalReviewUser);
        writingListDetailVO.setDataReportUser(examineUser);
        writingListDetailVO.setAppraisalReviewUser(reviewUser);
        return writingListDetailVO;
    }
}
