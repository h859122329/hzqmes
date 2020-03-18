package com.gdglc.hzqmes.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.gdglc.hzqmes.exception.BusinessException;
import com.gdglc.hzqmes.service.OssService;
import com.gdglc.hzqmes.util.Base64Util;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author:ZhongGuoce
 * @date:2019-03-02
 * @time:13:26
 */
@Slf4j
@Service
public class QiniuOssServiceImpl implements OssService {
    @Value("${qiniuoss.accessKey}")
    private String accessKey;

    @Value("${qiniuoss.secretKey}")
    private String secretKey;

    @Value("${qiniuoss.bucketName}")
    private String bucket;

    @Value("${qiniuoss.ossUrlPath}")
    private String ossUrlPath;


    @Value("${qiniuoss.ossFolderName}")
    private String ossFolderName;

    private Configuration cfg;

    private Auth auth;

    private Configuration getCfg() {
        if (cfg == null) {
            cfg = new Configuration(Zone.zone2());
        }
        return cfg;
    }

    private Auth getAuth() {
        if (auth == null) {
            auth = Auth.create(accessKey, secretKey);
        }
        return auth;
    }

    @Override
    public String upload(MultipartFile file) {
        if (file == null) {
            return "";
        }
        String filename = file.getOriginalFilename() != null ? file.getOriginalFilename(): "";
        if (filename.lastIndexOf(".") > 0) {
            String prefix = filename.substring(filename.lastIndexOf("."));
            filename = RandomUtil.randomNumbers(4) + DateUtils.formatDate(new Date(), "yyyyMMddHHmm") +prefix;
        }
        filename = ossFolderName + "/" + filename;
        //构造一个带指定Zone对象的配置类
        Configuration cfg = getCfg();
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Auth auth = getAuth();
        String upToken = auth.uploadToken(bucket);
        try {
            uploadManager.put(file.getInputStream(), filename, upToken, null, null);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("上传失败",r.toString());
            throw new BusinessException(ex);
        } catch (IOException e) {
            throw new BusinessException(e);
        }
        return ossUrlPath+filename;
    }

    @Override
    public String upload(byte[] fileBytes,String extension) {
        if (fileBytes == null) {
            return "";
        }
        //Amended by ZhongGuoce on 2019-08-04 代码重构
        return uploadFile(fileBytes, null, extension);
    }

    @Override
    public boolean deleteFile(String imageUrl) {
        if (StringUtils.isBlank(imageUrl)) {
            return false;
        }
        Configuration cfg = getCfg();
        Auth auth = getAuth();
        BucketManager bucketManager = new BucketManager(auth, cfg);
        if ((imageUrl.lastIndexOf(ossUrlPath)) == 0){
            imageUrl = imageUrl.replace(ossUrlPath,"");
        }
        try {
            bucketManager.delete(bucket, imageUrl);
            return true;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error("删除图片失败",ex);
            return false;
        }
    }

    @Override
    public String upload(InputStream inputStream, String extension) {
        //Amended by ZhongGuoce on 2019-08-04 代码重构
        return uploadFile(null, inputStream, extension);
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
        return "";
    }

    private String uploadFile (byte[] fileBytes,InputStream inputStream,String extension) {
        String filename = ossFolderName + "/" + RandomUtil.randomNumbers(4) + DateUtils.formatDate(new Date(), "yyyyMMddHHmm") + extension;
        //构造一个带指定Zone对象的配置类
        Configuration cfg = getCfg();
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Auth auth = getAuth();
        String upToken = auth.uploadToken(bucket);
        try {
            if (fileBytes!=null) {
                uploadManager.put(fileBytes, filename, upToken);
            } else if (inputStream !=null) {
                uploadManager.put(inputStream, filename, upToken, null, null);
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("上传失败",r.toString());
            throw new BusinessException(ex);
        }
        return ossUrlPath + filename;
    }
}
