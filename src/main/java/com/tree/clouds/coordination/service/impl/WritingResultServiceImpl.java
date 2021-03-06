package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.entity.Appraise;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.entity.MessageInfo;
import com.tree.clouds.coordination.model.vo.FileInfoVO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;
import com.tree.clouds.coordination.service.*;
import com.tree.clouds.coordination.utils.BaseBusinessException;
import com.tree.clouds.coordination.utils.QiniuUtil;
import com.tree.clouds.coordination.utils.Word2PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class WritingResultServiceImpl implements WritingResultService {

    @Autowired
    private DataReportService dataReportService;
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private MessageInfoService messageInfoService;

    @Autowired
    private ReviewSignatureService reviewSignatureService;
    @Autowired
    private QiniuUtil qiniuUtil;


    @Override
    public IPage<WritingResultBO> writingResultPage(WritingResultPageVO writingResultPageVO) {
        IPage<WritingResultBO> page = writingResultPageVO.getPage();
        return reviewSignatureService.writingResultPage(page, writingResultPageVO);
    }

    @Override
    public void writingBuild(String reportId) {
        DataReport dataReport = this.dataReportService.getById(reportId);
        Appraise appraise = appraiseService.getByReportId(reportId);
        QueryWrapper<MessageInfo> wrapper = new QueryWrapper<>();
        wrapper.eq(MessageInfo.REPORT_ID, reportId);
        List<MessageInfo> list = messageInfoService.list(wrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new BaseBusinessException(400, "??????????????????,??????????????????!");
        }

        //?????????????????????
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessageStatus("0");
        messageInfo.setWritingBatchId(appraise.getWritingBatchId());
        messageInfo.setReportId(appraise.getReportId());
        messageInfoService.saveOrUpdate(messageInfo, new QueryWrapper<MessageInfo>().eq(MessageInfo.REPORT_ID, appraise.getReportId()));
        CompletableFuture.runAsync(() -> {
            //????????????
            FileInfoVO fileInfoVO = null;
            try {
                fileInfoVO = wordBuild(dataReport.getSort(), dataReport.getUnitName(), dataReport.getIdentifiedName(),
                        dataReport.getIdCart(), appraise.getAppraiseNumber(), qiniuUtil, appraise.getAppraiseGrade(), appraise.getAppraisePrinciple(), appraise.getAppraiseResult(),
                        appraise.getResultSickCondition(), appraise.getAppraiseTime());
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileInfoService.saveFileInfo(Collections.singletonList(fileInfoVO), messageInfo.getMessageId());
        });

    }

    @Override
    public String getWritingFile(String reportId) {
        String writingFile = this.messageInfoService.getWritingFile(reportId);
        if (StrUtil.isBlank(writingFile)) {
            throw new BaseBusinessException(400, "?????????????????????!,?????????????????????!");
        }
        return writingFile;
    }

    public FileInfoVO wordBuild(int sort, String unit, String name,
                                String idCart, String number, QiniuUtil qiniuUtil,
                                String appraiseGrade, String appraisePrinciple, String appraiseResult, String resultSickCondition, String dateTime) throws IOException {
        DateTime time = DateUtil.parseDate(dateTime);
        HashMap<String, Object> info = new HashMap<String, Object>() {{
            put("year", DateUtil.year(time));//?????????
            put("month", DateUtil.month(time) + 1);//?????????
            put("date", DateUtil.dayOfMonth(time));//?????????
            put("name", name);//??????
            put("idCart", idCart);//?????????
            put("appraiseNumber", number);//??????
            put("appraiseGrade", appraiseGrade);//????????????
            put("appraisePrinciple", appraisePrinciple);//????????????
            put("appraiseResult", appraiseResult);//????????????
            put("resultSickCondition", resultSickCondition);//????????????
            put("unit", unit);//??????
        }};
        InputStream resource = null;
        InputStream none = null;
        //????????????
        if (sort == 1) {
            if (StrUtil.isBlank(unit)) {
                resource = this.getClass().getResourceAsStream("/result.docx");
                none = this.getClass().getResourceAsStream("/result_none.docx");
            } else {
                //????????????
                resource = this.getClass().getResourceAsStream("/result2.docx");
                none = this.getClass().getResourceAsStream("/result2_none.docx");
            }
        } else {
            //???
            resource = this.getClass().getResourceAsStream("/result3.docx");
            none = this.getClass().getResourceAsStream("/result3_none.docx");
        }
        //????????????  ?????????
        Configure config = Configure.newBuilder().build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(info);
        String formatSuffix = ".docx";
        String fileName = "??????????????????" + DateUtil.year(time) + "??????" + number + "????????????";//?????????  ?????????
        FileOutputStream fos = new FileOutputStream(Constants.TMP_HOME + fileName + formatSuffix);
        template.write(fos);
        template.close();
        String file = Word2PdfUtil.doc2Img(Constants.TMP_HOME + fileName + formatSuffix, Constants.TMP_HOME);
        String fileKey = qiniuUtil.fileUpload(file);
        //=================?????????????????????=================
        template = XWPFTemplate.compile(none, config).render(info);
        fos = new FileOutputStream(Constants.TMP_HOME + fileName + "none" + formatSuffix);
        template.write(fos);
        template.close();
        file = Word2PdfUtil.doc2Img(Constants.TMP_HOME + fileName + "none" + formatSuffix, Constants.TMP_HOME);
        String noneFile = qiniuUtil.fileUpload(file);
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setType("6");
        fileInfoVO.setFilePath(fileKey + "," + noneFile);
        fileInfoVO.setFileName(fileName + formatSuffix);
        return fileInfoVO;
    }
}
