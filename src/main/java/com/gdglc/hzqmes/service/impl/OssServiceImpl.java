package com.gdglc.hzqmes.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyun.oss.OSSClient;
import com.gdglc.hzqmes.exception.BusinessException;
import com.gdglc.hzqmes.service.OssService;
import com.gdglc.hzqmes.util.Base64Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Slf4j
//@Service
public class OssServiceImpl implements OssService {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.ossUrlPath}")
    private String ossUrlPath;

    private OSSClient ossClient;

    private OSSClient getOSSClient(){
        if (ossClient == null) {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
        return ossClient;
    }

    /**
     * oss 上传图片
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) {
        if (file == null) {
            return "";
        }
        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename(): "";
        if (filename.lastIndexOf(".") > 0) {
            filename = RandomUtil.randomNumbers(4) + DateUtils.formatDate(new Date(), "yyyyMMddHHmm") +filename.substring(filename.lastIndexOf("."));
        }
        OSSClient ossClient = getOSSClient();
        try{
            // 上传文件流。
            ossClient.putObject(bucketName, filename, file.getInputStream());
            // 关闭OSSClient。
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            ossClient.shutdown();
        }
        return ossUrlPath + filename;
    }

    @Override
    public String upload(byte[] fileBytes, String extension) {
        if (fileBytes == null) {
            return "";
        }
        String filename = RandomUtil.randomNumbers(4) + DateUtils.formatDate(new Date(), "yyyyMMddHHmm") + extension;
        OSSClient ossClient = getOSSClient();
        try{
            // 上传文件流。
            ossClient.putObject(bucketName, filename, new ByteArrayInputStream(fileBytes));
            // 关闭OSSClient。
        } finally {
            ossClient.shutdown();
        }
        return ossUrlPath + filename;
    }

    /**
     * 根据imageUrl删除OSS服务器上的文件
     * @param imageUrl
     */
    @Override
    public boolean deleteFile(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            return false;
        }
        OSSClient ossClient = getOSSClient();
        if ((imageUrl.lastIndexOf(ossUrlPath)) == 0){
            imageUrl = imageUrl.replace(ossUrlPath,"");
        }
        try {
            ossClient.deleteObject(bucketName,  imageUrl);
        } catch (Exception e) {
            log.error("删除图片失败",e);
            return false;
        }
        return true;
    }

    @Override
    public String upload(InputStream inputStream, String extension) {
        String filename = RandomUtil.randomNumbers(4) + DateUtils.formatDate(new Date(), "yyyyMMddHHmm") + extension;
        OSSClient ossClient = getOSSClient();
        try{
            // 上传文件流。
            ossClient.putObject(bucketName, filename,inputStream);
            // 关闭OSSClient。
        } finally {
            ossClient.shutdown();
        }
        return ossUrlPath + filename;
    }

    @Override
    public String upload(String base64Url, String extension) {
        if (StringUtils.isBlank(base64Url)){
            return "";
        }
        byte[] bytes = Base64Util.base64ImageToByte(base64Url);
        if (bytes !=null && bytes.length > 0) {
            return this.upload(bytes, extension);
        }
        return null;
    }
}
