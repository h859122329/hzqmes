package com.gdglc.hzqmes.service;

import com.gdglc.hzqmes.common.vo.AreaVo;
import com.gdglc.hzqmes.common.vo.SelectAreaVo;
import com.gdglc.hzqmes.po.Area;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 地区 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface AreaService {

    /**
     * 查询有子级的省市
     * @param id
     * @param areaType
     * @return List<Area>
     */
    List<Area> queryAreaById(String areaType, Integer id);

    /**
     * 查询所有省份城市信息
     * @return
     */
    @Cacheable( value = "Area.queryAll", key="'all'")
    List<Area> queryAll();

    /**
     * 查询所有省份城市信息
     * @return
     */
    List<AreaVo> queryAllToVo();

    /**
     * 传入某级菜单，查到它的所有子菜单
     * @param parentname
     * @return
     */
    List<SelectAreaVo> getSelectAreaById(Integer parentname);

    /**
     * 根据所属地方 查找所有属于该市的地方  用于编辑
     * @param areaid 市区id
     * @return
     */
    @Cacheable( value = "Area.getBrotherAreaById", key="#areaid")
    List<SelectAreaVo> getBrotherAreaById(Integer areaid);


    /**
     * 根据自身Id, 获取同级且共父 的所有地方列表
     * @param areaid
     * @return
     */
    @Cacheable( value = "Area.getPeerAreaById", key="#areaid")
    List<Area> getPeerAreaById(Integer areaid);

    /**
     * 根据父级返回所有子级
     * @return
     */
    @Cacheable( value = "Area.getSubordinate", key="#parentId")
    List<Area>  getSubordinate(Integer parentId);
    /**
     * 返回父级areaid用于活动编辑初始化
     * @param areaId
     * @return
     */
    Integer getSelectByParentId(Integer areaId);

    /**
     * 获取全部行政区域信息
     * @return
     */
    @Cacheable( value = "Area.getAllArea", key="'all'")
    List<Area> getAllArea();
}
