package com.gdglc.hzqmes.controller;

import com.gdglc.hzqmes.po.Category;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.service.CategoryService;
import com.gdglc.hzqmes.service.ProductService;
import com.gdglc.hzqmes.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Api(tags = "分类管理")
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @ApiOperation("根据你的id查询对应的分类信息数据")
    @GetMapping("/getCategoryListById")
    public ResultResponse getCategoryListById(@RequestParam(value = "moduleId") Integer moduleId){
        return ResultVOUtil.success(categoryService.getCategoryListById(moduleId));
    }
}
