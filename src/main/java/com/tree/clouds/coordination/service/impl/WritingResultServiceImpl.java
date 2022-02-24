package com.tree.clouds.coordination.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.mapper.DataReportMapper;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class WritingResultServiceImpl implements WritingResultService {

    @Autowired
    private DataReportMapper dataReportMapper;
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private MessageInfoService messageInfoService;

    @Autowired
    private ReviewSignatureService reviewSignatureService;


    @Override
    public IPage<WritingResultBO> writingResultPage(WritingResultPageVO writingResultPageVO) {
        IPage<WritingResultBO> page = writingResultPageVO.getPage();
        return reviewSignatureService.writingResultPage(page, writingResultPageVO);
    }

    @Override
    public void writingBuild(String reportId) {
        DataReport dataReport = this.dataReportMapper.selectById(reportId);
        Appraise appraise = appraiseService.getByReportId(reportId);
        QueryWrapper<MessageInfo> wrapper = new QueryWrapper();
        wrapper.eq(MessageInfo.REPORT_ID, reportId);
        List<MessageInfo> list = messageInfoService.list(wrapper);
        if (CollUtil.isNotEmpty(list)) {
            throw new BaseBusinessException(400, "已生成结论书,不可重复生成!");
        }

        try {
            //流转到结论送达
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setMessageStatus("0");
            messageInfo.setWritingBatchId(appraise.getWritingBatchId());
            messageInfo.setReportId(appraise.getReportId());
            messageInfoService.save(messageInfo);
            //文件保存
            FileInfoVO fileInfoVO = wordBuild(dataReport.getSort(), dataReport.getUnitName(), dataReport.getIdentifiedName(), dataReport.getIdCart(), appraise.getAppraiseNumber(), dataReport.getSickCondition(), appraise.getAppraiseResult());
            this.fileInfoService.saveFileInfo(Collections.singletonList(fileInfoVO), messageInfo.getMessageId());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getWritingFile(String reportId) {
        String writingFile = this.messageInfoService.getWritingFile(reportId);
        if (StrUtil.isBlank(writingFile)) {
            throw new BaseBusinessException(400, "还未生成结论书!,请先生成结论书!");
        }
        return writingFile;
    }

    public FileInfoVO wordBuild(int sort, String unit, String name, String idCart, String number, String sickCondition, String appraiseResult) throws IOException {
        Calendar now = Calendar.getInstance();
        HashMap<String, Object> info = new HashMap<String, Object>() {{
            put("year", now.get(Calendar.YEAR));//当前年
            put("month", (now.get(Calendar.MONTH) + 1));//当前月
            put("date", now.get(Calendar.DAY_OF_MONTH));//当前日
            put("name", name);//时间
            put("idCart", idCart);//身份证
            put("number", number);//编号
            put("sickCondition", sickCondition);//病残情况
            put("appraiseResult", appraiseResult);//鉴定结论
            put("unit", unit);//单位
        }};
        String resource;
        if (sort == 0 && unit == null) {
            resource = this.getClass().getClassLoader().getResource("result.docx").getFile();
        } else {
            resource = this.getClass().getClassLoader().getResource("result2.docx").getFile();
        }
        if (sort == 1) {
            resource = this.getClass().getClassLoader().getResource("result3.docx").getFile();
        }
        //渲染表格  动态行
        Configure config = Configure.newBuilder().build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(info);
        //=================生成文件保存=================
        // 生成的word格式
        String formatSuffix = ".docx";
        // 拼接后的文件名
        String fileName = "南劳鉴病字［" + now.get(Calendar.YEAR) + "］第" + number + "号结论书" + formatSuffix;//文件名  带后缀

        FileOutputStream fos = new FileOutputStream(Constants.TMP_HOME + fileName);
        template.write(fos);
        String file = Word2PdfUtil.doc2Img(Constants.TMP_HOME + fileName, Constants.TMP_HOME);
        String fileKey = QiniuUtil.fileUpload(file);
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setType("6");
        fileInfoVO.setFilePath(fileKey);
        fileInfoVO.setFileName(fileName);
        return fileInfoVO;
    }
}
