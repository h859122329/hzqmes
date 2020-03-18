package com.gdglc.hzqmes.controller;


import com.gdglc.hzqmes.po.PicData;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.service.PicDataService;
import com.gdglc.hzqmes.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 图片存储表 前端控制器
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/pic-data")
public class PicDataController {

    @Autowired
    PicDataService picDataService;

    @RequestMapping("/getProductPicById")
    public ResultResponse getProductPicById(Integer id){

        return ResultVOUtil.success(picDataService.getProductPicById(id));
    }
    @RequestMapping("/remove")
    public ResultResponse removePicDataById(Integer id){
        return ResultVOUtil.success(picDataService.removePicDataById(id));
    }
}
