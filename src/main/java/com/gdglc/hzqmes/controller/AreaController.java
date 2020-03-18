package com.gdglc.hzqmes.controller;

import com.gdglc.hzqmes.common.vo.SelectAreaVo;
import com.gdglc.hzqmes.po.Area;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.service.AreaService;
import com.gdglc.hzqmes.util.ResultVOUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Leyenda on 2019/12/17.
 */
@Slf4j
@Api(tags = "行政区域表")
@RestController
@RequestMapping("/api/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping("/initArea")
    public ResultResponse initArea(Integer id) {
        List<SelectAreaVo> areasList = areaService.getBrotherAreaById(id);
        return ResultVOUtil.success(areasList);
    }

    @RequestMapping("/getByParentId")
    public ResultResponse getAreasByParentId( Integer parentId){
        List<Area> areaList = areaService.getSubordinate(parentId);
        return ResultVOUtil.success(areaList);
    }

    @RequestMapping("/getPeerAreaById")
    public ResultResponse getPeerAreaById( Integer id){
        List<Area> areaList = areaService.getPeerAreaById(id);
        return ResultVOUtil.success(areaList);
    }

    @RequestMapping("/getAllArea")
    public ResultResponse getAllArea(){
        List<Area> areaList = areaService.getAllArea();
        return ResultVOUtil.success(areaList);
    }
}
