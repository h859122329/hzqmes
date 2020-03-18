package com.gdglc.hzqmes.controller;


import com.gdglc.hzqmes.common.form.PageForm;
import com.gdglc.hzqmes.common.form.SearchForm;
import com.gdglc.hzqmes.common.form.UsersForm;
import com.gdglc.hzqmes.common.vo.UserIdsVo;
import com.gdglc.hzqmes.enums.ResultEnum;
import com.gdglc.hzqmes.po.Users;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.service.OssService;
import com.gdglc.hzqmes.service.RoleService;
import com.gdglc.hzqmes.service.UsersService;
import com.gdglc.hzqmes.util.HttpUtil;
import com.gdglc.hzqmes.util.ResultVOUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private OssService ossService;
    @Autowired
    private RoleService roleService;

    @ApiOperation("根据条件查询用户列表")
    @GetMapping("/getByCondition")
    public ResultResponse getByCondition(Users user, SearchForm searchForm, PageForm pageForm){
        UsersForm usersForm = new UsersForm(user, pageForm, searchForm);
        return ResultVOUtil.success(usersService.getByCondition(usersForm));
    }

    @ApiOperation("根据用户编号获取用户信息")
    @ApiImplicitParam(paramType="query", name = "id", value = "用户编号", dataType = "Integer")
    @GetMapping("/getById")
    public  ResultResponse getById(Integer id){
        return  ResultVOUtil.success(usersService.selectUserById(id));
    }

    @ApiOperation("校验用户名是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "用户编号", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "username", value = "用户名", dataType = "String")
    })
    @GetMapping("/checkUserName")
    public ResultResponse checkUserName(Integer id,String username){
        Integer integer =usersService.checkUserByUsername(id,username);
        return  ResultVOUtil.success(integer);
    }

    @ApiOperation("校验邮箱或者手机号码是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "用户编号", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "str", value = "邮箱或者手机号码", dataType = "String")
    })
    @GetMapping("/checkUserEmailAndMobile")
    public ResultResponse checkUserEmail(Integer id,String str){
        Integer result = usersService.checkUserEmailAndMobile(id,str);
        return  ResultVOUtil.success(result);
    }

    @ApiOperation("加载所有角色列表")
    @GetMapping("/loadRoles")
    public ResultResponse loadRoles(){
        return  ResultVOUtil.success(roleService.getRoles());
    }


    @ApiOperation("编辑用户")
    @PostMapping("/edit")
    public  ResultResponse edit(@RequestParam(name="file",required = false) MultipartFile file, Users users ,@RequestParam(name="roleIdList") List<Integer> roleIdList , String delImg){
        if(delImg != null && !delImg.isEmpty()){
            ossService.deleteFile(delImg);
        }
        ResultResponse resultResponse=uploadImg(users,file);
        if(resultResponse!=null){
            return  resultResponse;
        }
        if(file == null && (delImg == null || delImg.isEmpty())){
            users.setFace(null);
        }
        if(StringUtils.isNotBlank(users.getPassword())) {
            users.setPassword(encoder.encode(users.getPassword()));
        }else {
            users.setPassword(null);
        }
        usersService.updateUserInfoById(users, roleIdList);
        return  ResultVOUtil.success();
    }

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public  ResultResponse add(@RequestParam(name="file",required = false) MultipartFile file, Users users,@RequestParam(name="roleIdList") List<Integer> roleIdList, HttpServletRequest request){
        ResultResponse resultResponse=uploadImg(users,file);
        if(resultResponse!=null){
            return resultResponse;
        }
        users.setRegIp(HttpUtil.getIpAddr(request));
        users.setPassword(encoder.encode(users.getPassword()));
        users.setNickname(StringUtils.trimToNull(users.getNickname()));
        usersService.addUser(users, roleIdList);
        return  ResultVOUtil.success();
    }

    @ApiOperation("禁用或启用用户")
    @PostMapping("/enable")
    public ResultResponse enable(@RequestBody Users users){
        usersService.updateUserStatusById(users.getId(),users.getStatus());
        return  ResultVOUtil.success();
    }

    @ApiOperation("批量删除用户")
    @PostMapping("/delByIds")
    public  ResultResponse delByIds(@RequestBody UserIdsVo userIdsVo){
        usersService.updateUserStatusByIds(userIdsVo.getIds(),1);
        return  ResultVOUtil.success();
    }

    /**
     * 图片上传
     * @param users
     * @param file
     * @return
     */
    private ResultResponse uploadImg(Users users,MultipartFile file){
        if (file != null ) {
            String imagePath;
            try {
                imagePath = ossService.upload(file);
            } catch (Exception e) {
                log.error("error",e);
                return ResultVOUtil.error(ResultEnum.FILE_UPLOAD_ERROR.getMessage());
            }
            users.setFace(imagePath);
        }
        return null;
    }
}
