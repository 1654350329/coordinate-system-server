package com.tree.clouds.coordination.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tree.clouds.coordination.model.entity.UserManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: 林振坤
 * @description:
 * @date: 2021/05/07 16:14
 */
@Component
public class UserManagerExcelListener extends AnalysisEventListener<UserManage> {
    private static Logger LOGGER = LoggerFactory.getLogger(UserManagerExcelListener.class);
    private static List<UserManage> datas = new ArrayList<>();

    public static List<UserManage> getDatas() {
        return datas;
    }

    @Override
    public void invoke(UserManage data, AnalysisContext context) {
//        if (StrUtil.isBlank(data.getFieldCnName())) {
//            BizAssert.fail(String.format("第%s行字段名称为空，请核实", context.readRowHolder().getRowIndex() + 1));
//        }
//        if (StrUtil.isBlank(data.getFieldEnName())) {
//            BizAssert.fail(String.format("第%s行英文名称为空，请核实", context.readRowHolder().getRowIndex() + 1));
//        }
//        if (StrUtil.isBlank(data.getFieldTypeName())) {
//            BizAssert.fail(String.format("第%s行类型为空，请核实", context.readRowHolder().getRowIndex() + 1));
//        }
//        if (StrUtil.isBlank(data.getFieldLength())) {
//            BizAssert.fail(String.format("第%s行长度为空，请核实", context.readRowHolder().getRowIndex() + 1));
//        }
//        LOGGER.info("导入数据{}", JSON.toJSONString(data));
//        //数据存储到list，供批量处理
        datas.add(data);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！");
    }

}
