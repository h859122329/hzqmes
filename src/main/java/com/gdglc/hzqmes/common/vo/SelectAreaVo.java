package com.gdglc.hzqmes.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Created by Leyenda on 2019/12/13.
 */
@Data
public class SelectAreaVo {
    @TableId(value = "areaid")
    private Integer areaid;
    /**
     *  名称
     */
    @TableField("areaname")
    private String areaname;

    /**
     * 父 id
     */
    @TableField("parentid")
    private String parentid;

    /**
     * 父 名称
     */
    @TableField("rname")
    private String parentname;
}
