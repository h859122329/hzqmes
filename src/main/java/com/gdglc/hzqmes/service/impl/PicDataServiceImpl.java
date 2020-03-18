package com.gdglc.hzqmes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdglc.hzqmes.dao.ProductDao;
import com.gdglc.hzqmes.po.PicData;
import com.gdglc.hzqmes.dao.PicDataDao;
import com.gdglc.hzqmes.service.PicDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片存储表 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Service
public class PicDataServiceImpl extends ServiceImpl<PicDataDao, PicData> implements PicDataService {

    @Autowired
    private PicDataDao picDataDao;

    /**
     * 插入图片
     * @param picData
     * */
    @Override
    public void insert(PicData picData) {
        picDataDao.insert(picData);
    }

    /**
     * @param id  根据产品id获得图片
     * */
    @Override
    public Map<String,List<PicData>> getProductPicById(Integer id) {
        Map<String,List<PicData>> map = new HashMap<String,List<PicData>>();

        map.put("punch_info", picDataDao.selectList(new QueryWrapper<PicData>().
                eq("entity","product").eq("entity_id",id).eq("entity_field","punch_info")));
        map.put("print_content", picDataDao.selectList(new QueryWrapper<PicData>().
                eq("entity","product").eq("entity_id",id).eq("entity_field","print_content")));
        return map;
    }


    /**
     * @Param id 根据id删除图片
     * */
    @Override
    public int removePicDataById(Integer id) {
        PicData picData = new PicData().setId(id);
        if(picDataDao.selectById(picData)!=null){
            return picDataDao.deleteById(picData);
        }else{
            return 0;
        }
    }

    @Override
    public void deleteBatchIds(Integer[] deletePicDataIds) {
        for (Integer id: deletePicDataIds){
            picDataDao.deleteById(id);
        }
    }
}
