package com.gdglc.hzqmes.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author:ZhongGuoce
 * @date:2019-02-02
 * @time:21:04
 */
public interface OssService {
    /**
     * oss 上传图片
     * @param file
     * @return 图片的相对路径
     */
    String upload(MultipartFile file);

    /**
     * 上传图片 byte[]
     * @param fileBytes
     * @param extension
     * @return
     */
    String upload(byte[] fileBytes,String extension);

    /**
     * 上传图片 inputStream
     * @param inputStream
     * @param extension
     * @return
     */
    String upload(InputStream inputStream, String extension);
    /**
     * 上传图片 base64Url
     * @param base64Url
     * @param extension
     * @return
     */
    String upload(String base64Url, String extension);

    /**
     * 根据key删除OSS服务器上的文件
     * @param key
     * @return
     */
    boolean deleteFile(String key);
}
