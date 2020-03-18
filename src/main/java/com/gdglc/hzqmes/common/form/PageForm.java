package com.gdglc.hzqmes.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author:ZhongGuoce
 * @date:2019-01-21
 * @time:21:50
 */
@Data
@ApiModel(description= "分页查询的对象")
public class PageForm implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前页码", dataType = "Integer")
    private int current;

    @ApiModelProperty(value = "一页显示多少条记录", dataType = "Integer")
    private int size;

    private List records;

    private Long total;
}
