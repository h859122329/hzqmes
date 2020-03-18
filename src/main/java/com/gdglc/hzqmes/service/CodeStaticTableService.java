package com.gdglc.hzqmes.service;


import com.gdglc.hzqmes.po.CodeStaticTable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */

@CacheConfig(cacheNames = "codeStatic")
public interface CodeStaticTableService {


    /**
     * 根据code Id 和 code type 得到code
     * @param codeId
     * @param codeType
     * @return
     */
    public CodeStaticTable getByCodeIdAndCodeType (String codeId, String codeType);


    /**
     * 得到一个分类
     * @param codeType
     * @return
     */
    @Cacheable( value = "getCodeListByCodeType",key = "#codeType"  )
    public List<CodeStaticTable> getCodeListByCodeType(String codeType);

    /**
     * 获取code static table 所有数据
     * @return
     */
    @Cacheable(value = "getCodeListByCodeType",key = "'ALL'")
    public List<CodeStaticTable> getFullCodeList();
}
