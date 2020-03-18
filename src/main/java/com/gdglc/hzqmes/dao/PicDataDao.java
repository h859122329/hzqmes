package com.gdglc.hzqmes.dao;

import com.gdglc.hzqmes.po.PicData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 图片存储表 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
public interface PicDataDao extends BaseMapper<PicData> {
    List<PicData> getPicDataByIdAndPrintContent(Integer id);
    List<PicData> getPicDataByIdAndPunchInfo(Integer id);
}
