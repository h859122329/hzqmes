package com.gdglc.hzqmes.util;


import com.gdglc.hzqmes.response.ResultResponse;
import org.springframework.http.HttpStatus;

public class ResultVOUtil {
    private ResultVOUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static ResultResponse success(Object object){
        ResultResponse resultVO = new ResultResponse();
        resultVO.setData(object);
        resultVO.setStatus(HttpStatus.OK);
        resultVO.setCode(200);
        resultVO.setMsg("操作成功");
        return resultVO;
    }
    public static ResultResponse success(String msg, Object object){
        ResultResponse response = success(object);
        response.setMsg(msg);
        return response;
    }

    public static ResultResponse success(){
        return  success(null);
    }

    public static ResultResponse error(){
        return  error("操作失败");
    }

    public static ResultResponse error(String msg){
        return error(msg,500,null);
    }
    public static ResultResponse error(String msg, Integer code){
        return error(msg, code,null);
    }
    public static ResultResponse error(String msg, Integer code, Object data){
        ResultResponse resultVO = new ResultResponse();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        resultVO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        resultVO.setData(data);
        return resultVO;
    }
}
