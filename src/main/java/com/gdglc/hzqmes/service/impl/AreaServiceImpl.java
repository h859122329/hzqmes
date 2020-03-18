package com.gdglc.hzqmes.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdglc.hzqmes.common.vo.AreaVo;
import com.gdglc.hzqmes.common.vo.SelectAreaVo;
import com.gdglc.hzqmes.dao.AreaDao;
import com.gdglc.hzqmes.po.Area;
import com.gdglc.hzqmes.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 地区 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;



    @Override
    public List<Area> queryAreaById(String areaType, Integer id) {
        return areaDao.queryById(areaType, id);
    }

    @Override
    public List<Area> queryAll() {
        return areaDao.queryAll();
    }

    @Override
    public List<Area> getAllArea(){
        return areaDao.selectList(new QueryWrapper<>(new Area()));
    }

    /**
     * 查询全部地址 存到AreaVo类型集合
     * @return
     */
    @Override
    public List<AreaVo> queryAllToVo() {

        List<Area> areaList = areaDao.queryAll();

        if(areaList == null){
            return null;
        }
        List<AreaVo> areaVoList = new ArrayList<>();
        for (Area area1 : areaList) {
            List<AreaVo> areaVoListTemp = new ArrayList<>();

            for (Area area2: area1.getAreaList()) {
                areaVoListTemp.add(new AreaVo(area2.getAreaid(),area2.getAreaname(),null));
            }
            areaVoList.add(new AreaVo(area1.getAreaid(), area1.getAreaname(),areaVoListTemp));
        }
        return areaVoList;
    }

    @Override
    public List<SelectAreaVo> getSelectAreaById(Integer parentname) {
        return areaDao.getSelectAreaById(parentname);
    }

    @Override
    public List<SelectAreaVo> getBrotherAreaById(Integer areaid) {
        return areaDao.getBrotherAreaById(areaid);
    }

    /**
     * 根据自身Id, 获取同级且共父 的所有地方列表
     *
     * @param areaid
     * @return
     */
    @Override
    public List<Area> getPeerAreaById(Integer areaid) {
        return areaDao.getPeerAreaById(areaid);
    }

    @Override
    public List<Area> getSubordinate(Integer parentId) {
        return areaDao.getSubordinate(parentId);
    }

    @Override
    public Integer getSelectByParentId(Integer areaId) {
        return areaDao.getSelectByParentId(areaId);
    }

}
