package com.gdglc.hzqmes.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.CustomerBatchIdForm;
import com.gdglc.hzqmes.common.form.PageForm;
import com.gdglc.hzqmes.common.form.ProductBatchIdForm;
import com.gdglc.hzqmes.enums.ResultEnum;
import com.gdglc.hzqmes.po.PicData;
import com.gdglc.hzqmes.po.Product;
import com.gdglc.hzqmes.po.Users;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.service.OssService;
import com.gdglc.hzqmes.service.PicDataService;
import com.gdglc.hzqmes.service.ProductService;
import com.gdglc.hzqmes.service.UsersService;
import com.gdglc.hzqmes.util.HttpUtil;
import com.gdglc.hzqmes.util.PageUtil;
import com.gdglc.hzqmes.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>
 * 产品表 前端控制器
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Slf4j
@Api(tags = "产品")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private OssService ossService; //图片处理类
    @Autowired
    private UsersService usersService;
    @Autowired
    PicDataService picDataService;

    @ApiOperation("修改产品")
    @PostMapping("/modify")
    public ResultResponse modifyProduct(@RequestParam(name = "printImgList",required = false) MultipartFile[] printImgList,
                                        @RequestParam(name = "punchInfoImgList",required = false) MultipartFile[] punchInfoImgList,
                                        @RequestParam(name = "deletePicDataIds",required = false)Integer[] deletePicDataIds,
                                        @RequestParam(name = "deletePicDataImages",required = false)String[] deletePicDataImages,
                                        Product product,HttpServletRequest request){
        Users user = usersService.findUserInContext();
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateUid(user.getId());
        System.out.println(product);
        productService.updateById(product);

        for (MultipartFile file : printImgList){
            String imagePath;
            try {
                imagePath = ossService.upload(file);
                PicData picData = new PicData();
                picData.setPicPath(imagePath);
                picData.setEntity("product");
                picData.setEntityField("print_content");
                picData.setEntityId(product.getId());
                picDataService.insert(picData);
            } catch (Exception e) {
                log.error("error",e);
                return ResultVOUtil.error(ResultEnum.FILE_UPLOAD_ERROR.getMessage());
            }
        }
        for (MultipartFile file : punchInfoImgList){
            String imagePath;
            try {
                imagePath = ossService.upload(file);
                PicData picData = new PicData();
                picData.setPicPath(imagePath);
                picData.setEntity("product");
                picData.setEntityField("punch_info");
                picData.setEntityId(product.getId());
                picDataService.insert(picData);
            } catch (Exception e) {
                log.error("error",e);
                return ResultVOUtil.error(ResultEnum.FILE_UPLOAD_ERROR.getMessage());
            }
        }


        if(deletePicDataIds!=null && deletePicDataIds.length>0){
            picDataService.deleteBatchIds(deletePicDataIds); // 根据id删除数据库图片
            for (String imgPath : deletePicDataImages){
                deleteServiceFile(imgPath);
            }
        }
        return ResultVOUtil.success(true);
    }

    @ApiOperation("根据id查询产品")
    @RequestMapping("/getProductDetailById")
    public ResultResponse getProductDetailById(Integer id){
        return ResultVOUtil.success(productService.getProductDetailById(id));
    }

    @ApiOperation("删除产品")
    @RequestMapping("/batchMarkDeleteProduct")
    public ResultResponse batchMarkDeleteProduct(ProductBatchIdForm productBatchIdForm){
        productService.batchMarkDeleteProduct(productBatchIdForm.getProductIds());
        return ResultVOUtil.success();
    }

    @ApiOperation("查询产品")
    @RequestMapping("/searchProductDetailByCritera")
    public ResultResponse searchProductDetailByCritera(Product searchForm, PageForm pageForm){
        IPage iPage = PageUtil.initPage(pageForm);
        return ResultVOUtil.success(productService.searchProductDetailByCritera(searchForm,iPage));
    }

    @ApiOperation("添加产品")
    @PostMapping({"/add"})
    public  ResultResponse add(@RequestParam(name = "printImgList",required = false) MultipartFile[] printImgList,
                               @RequestParam(name = "punchInfoImgList",required = false) MultipartFile[] punchInfoImgList,
                               Product product,HttpServletRequest request) {
        product.setCreateTime(LocalDateTime.now());
        Users user = usersService.findUserInContext();
        product.setActiveFlag(1);
        product.setCreateTime(LocalDateTime.now());
        product.setCreateUid(user.getId());
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateUid(user.getId());

        productService.insert(product);

        //传递过来产品对象  以及图片集合  打印集合
        int orderIndex = 0;
        for (MultipartFile file : printImgList){
            String imagePath;
            try {
                imagePath = ossService.upload(file);
                PicData picData = new PicData();
                picData.setPicPath(imagePath);
                picData.setEntity("product");
                picData.setEntityField("print_content");
                picData.setSeqNo(orderIndex);
                picData.setEntityId(product.getId());
                orderIndex++; // 排序顺序增加
                picDataService.insert(picData);
            } catch (Exception e) {
                log.error("error",e);
                return ResultVOUtil.error(ResultEnum.FILE_UPLOAD_ERROR.getMessage());
            }
        }
        // 打孔位置描述集合

        int orderIndex2 = 0;
        for (MultipartFile file : punchInfoImgList){
            String imagePath;
            try {
                imagePath = ossService.upload(file);
                PicData picData = new PicData();
                picData.setPicPath(imagePath);
                picData.setEntity("product");
                picData.setEntityField("punch_info");
                picData.setSeqNo(orderIndex2);
                picData.setEntityId(product.getId());
                orderIndex2++; // 排序顺序增加
                picDataService.insert(picData);
            } catch (Exception e) {
                log.error("error",e);
                return ResultVOUtil.error(ResultEnum.FILE_UPLOAD_ERROR.getMessage());
            }
        }

        return  ResultVOUtil.success();
    }


    @ApiOperation("判断SkuCode是否重复")
    @GetMapping("/getSkuCode")
    public ResultResponse getSkuCode(String skuCode) {
        return ResultVOUtil.success(productService.getSkuCode(skuCode));
    }


    /**
     * 图片上传
     * @param users
     * @param file
     * @return
     */
    private ResultResponse uploadImg(Users users, MultipartFile file){
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

    private void deleteServiceFile(String fileParam){
        ossService.deleteFile(fileParam);
    }
}
