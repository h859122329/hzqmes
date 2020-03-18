package com.gdglc.hzqmes.common.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:ZhongGuoce
 * @date:2019-01-21
 * @time:21:50
 */
@Data
public class SearchForm implements Serializable {

    private String startDate;

    private String endDate;
}
