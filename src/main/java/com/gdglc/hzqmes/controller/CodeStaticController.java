package com.gdglc.hzqmes.controller;

import com.gdglc.hzqmes.po.CodeStaticTable;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.service.CodeStaticTableService;
import com.gdglc.hzqmes.util.ResultVOUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Leyenda on 2019/11/14.
 */
@Slf4j
@Api(tags = "静态数据表")
@RestController
@RequestMapping("/api/codestatic")
public class CodeStaticController {

    @Autowired
    private CodeStaticTableService codeStaticTableService;

//    @Cacheable(cacheNames = "codeStatic", key="#codeType")
    @RequestMapping("/getByCodeType")
    public ResultResponse getCodeListByCodeType(@RequestParam(value = "codeType") String codeType){

        List<CodeStaticTable> resultList = codeStaticTableService.getCodeListByCodeType(codeType);

        HashMap<String, HashMap<String,CodeStaticTable>> resultMap  = codeList2HashMap(resultList);

       return ResultVOUtil.success(resultMap);
    }

    @RequestMapping("/getFullCodeList")
    public ResultResponse getFullCodeList(){

        List<CodeStaticTable> resultList = codeStaticTableService.getFullCodeList();
        HashMap<String, HashMap<String,CodeStaticTable>> resultMap  = codeList2HashMap(resultList);

        return ResultVOUtil.success(resultMap);
    }

    private HashMap<String, HashMap<String,CodeStaticTable>> codeList2HashMap(List<CodeStaticTable> codeList){

        HashMap<String,HashMap<String,CodeStaticTable>> resultMap = new HashMap<>();

        for (CodeStaticTable code :
                codeList) {
//           System.out.println(code);
            HashMap<String,CodeStaticTable> codeTypeMap = resultMap.get(code.getCodeType());
            if (codeTypeMap == null){
                codeTypeMap = new HashMap<>();
                resultMap.put(code.getCodeType(),codeTypeMap);
            }
            codeTypeMap.put(code.getCodeIdCd(),code);
//           System.out.println(code);
        }
        return resultMap;
    }
}
