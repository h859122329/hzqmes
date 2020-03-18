package com.gdglc.hzqmes.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:dengyuxiao
 * @date:2019-01-2
 * @time:21:50
 */
@Data
public class UserIdsVo implements Serializable {
    /**
     * 用于批量接收 需要做删除操作的用户 id 号
     */
    private Integer[] ids;
    
}
