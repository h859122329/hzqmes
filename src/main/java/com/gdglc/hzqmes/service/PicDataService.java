package com.gdglc.hzqmes.service;

import com.gdglc.hzqmes.po.PicData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片存储表 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
public interface PicDataService extends IService<PicData> {

    void insert(PicData picData);

    Map<String,List<PicData>>  getProductPicById(Integer id);

    int removePicDataById(Integer id);

    void deleteBatchIds(Integer [] deletePicDataIds);
}
