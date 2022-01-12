package com.tree.clouds.coordination.model.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageParam {
    @ApiModelProperty(value = "当前页", example = "1")
    private long current = 1L;
    @ApiModelProperty(value = "页面大小", example = "10")
    private long size = 10L;
    @ApiModelProperty(value = "排序参数", example = "column:createTime")
    private List<OrderItem> orderItems = new ArrayList();

    @JsonIgnore
    public <T> IPage<T> getPage() {
        Page page = new Page(this.getCurrent(), this.getSize());
        page.setOrders(this.orderItems);
        return page;
    }
}
