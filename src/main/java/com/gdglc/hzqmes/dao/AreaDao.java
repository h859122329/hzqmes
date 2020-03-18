package com.gdglc.hzqmes.dao;

import com.gdglc.hzqmes.common.vo.SelectAreaVo;
import com.gdglc.hzqmes.po.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 地区 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface AreaDao extends BaseMapper<Area> {

    /**
     * 查询所属地方
     * @param id
     * @param areaType
     * @return List<Area>
     */
    List<Area> queryById(@Param("areaType") String areaType, @Param("id") Integer id);

    /**
     * 查询全部
     * @return
     */
    List<Area> queryAll();
    /**
     * 传入某级菜单，查到它的所有子菜单
     * @param parentname
     * @return
     */
    List<SelectAreaVo> getSelectAreaById(@Param("parentid") Integer parentname);

    /**
     * 根据所属地方 查找所有属于该市的地方  用于编辑
     * @param areaid 市区id
     * @return
     */
    List<SelectAreaVo> getBrotherAreaById(@Param("areaid") Integer areaid);

    /**
     * 根据自身Id, 获取同级且共父 的所有地方列表
     * @param areaid
     * @return
     */
    List<Area> getPeerAreaById(@Param("areaid") Integer areaid);

    /**
     * 根据id查询返回该对象
     * @param areaid
     * @return
     */
    Area getByAreaId(Integer areaid);

    /**
     * 根据父级返回所有子级
     * @return
     */
    List<Area>  getSubordinate(@Param("parentId") Integer parentId);

    /**
     * 返回父级areaid用于活动编辑初始化
     * @param areaId
     * @return
     */
    Integer getSelectByParentId(@Param("areaId") Integer areaId);

}
