package com.gdglc.hzqmes.common.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Leyenda on 2019/12/13.
 */
@Data
public class AreaVo {
    private Integer areaid;
    private String areaname;
    private List<AreaVo> children;

    public AreaVo() {
    }

    public AreaVo(Integer areaid, String areaname, List<AreaVo> children) {
        this.areaid = areaid;
        this.areaname = areaname;
        this.children = children;
    }
}
