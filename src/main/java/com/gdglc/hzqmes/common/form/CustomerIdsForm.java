package com.gdglc.hzqmes.common.form;

import lombok.Data;

/**
 * Created by Leyenda on 2019/10/22.
 */
@Data
public class CustomerIdsForm {

    /**
     * 用于做批量处理的客户ID(删除/状态变更等)
     */
    private Integer[] customerIds;
}
