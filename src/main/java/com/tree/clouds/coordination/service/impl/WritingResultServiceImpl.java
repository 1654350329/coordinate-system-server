package com.tree.clouds.coordination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.tree.clouds.coordination.common.Constants;
import com.tree.clouds.coordination.mapper.DataReportMapper;
import com.tree.clouds.coordination.model.bo.WritingResultBO;
import com.tree.clouds.coordination.model.entity.Appraise;
import com.tree.clouds.coordination.model.entity.DataReport;
import com.tree.clouds.coordination.model.entity.MessageInfo;
import com.tree.clouds.coordination.model.vo.FileInfoVO;
import com.tree.clouds.coordination.model.vo.WritingResultPageVO;
import com.tree.clouds.coordination.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

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

    @Autowired
    private FastFileStorageClient storageClient;


    @Override
    public IPage<WritingResultBO> writingResultPage(WritingResultPageVO writingResultPageVO) {
        IPage<WritingResultBO> page = writingResultPageVO.getPage();
        return reviewSignatureService.writingResultPage(page, writingResultPageVO);
    }

    @Override
    public void writingBuild(String reportId) {
        DataReport dataReport = this.dataReportMapper.selectById(reportId);
        Appraise appraise = appraiseService.getByReportId(reportId);
        try {
            //流转到结论送达
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setMessageStatus("0");
            messageInfo.setWritingBatchId(appraise.getWritingBatchId());
            messageInfo.setReportId(appraise.getReportId());
            messageInfoService.save(messageInfo);
            //文件保存
            FileInfoVO fileInfoVO = wordBuild(dataReport.getIdentifiedName(), dataReport.getIdCart(), appraise.getAppralseNumber(), dataReport.getSickCondition(), appraise.getAppraiseResult());
            this.fileInfoService.saveFileInfo(Collections.singletonList(fileInfoVO), messageInfo.getMessageId());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FileInfoVO wordBuild(String name, String idCart, String number, String sickCondition, String appraiseResult) throws IOException {
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

        }};
        String resource = this.getClass().getClassLoader().getResource("result.docx").getFile();
        //渲染表格  动态行
        Configure config = Configure.newBuilder().build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(info);
        //=================生成文件保存=================
//        String courseFile = new File("").getCanonicalPath() + File.separator + "file";
//        FileUtil.mkdir(courseFile);
        // 生成的word格式
        String formatSuffix = ".docx";
        // 拼接后的文件名
        String fileName = "南劳鉴病字［" + now.get(Calendar.YEAR) + "］第" + number + "号结论书" + formatSuffix;//文件名  带后缀

        FileOutputStream fos = new FileOutputStream(Constants.TMP_HOME + fileName);
        template.write(fos);
        StorePath storePath = this.storageClient.uploadFile(new FileInputStream(Constants.TMP_HOME + fileName), new File(Constants.TMP_HOME + fileName).length(), formatSuffix, null);
        FileInfoVO fileInfoVO = new FileInfoVO();
        fileInfoVO.setType("6");
        fileInfoVO.setFilePath(storePath.getFullPath());
        fileInfoVO.setFileName(fileName);
        return fileInfoVO;
    }
}
