package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 地区
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "areaid", type = IdType.AUTO)
    private Integer areaid;

    @TableField("areaname")
    private String areaname;

    @TableField("parentid")
    private Integer parentid;

    @TableField("arrparentid")
    private String arrparentid;

    @TableField("child")
    private Integer child;

    @TableField("arrchildid")
    private String arrchildid;

    @TableField("listorder")
    private Integer listorder;

    @TableField(exist = false)
    private List<Area> areaList;


}
