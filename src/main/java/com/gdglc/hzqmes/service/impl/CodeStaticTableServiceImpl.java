package com.gdglc.hzqmes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdglc.hzqmes.dao.CodeStaticTableDao;
import com.gdglc.hzqmes.po.CodeStaticTable;
import com.gdglc.hzqmes.service.CodeStaticTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */

@Service
public class CodeStaticTableServiceImpl  implements CodeStaticTableService {

    @Autowired
    private CodeStaticTableDao codeStaticTableDao;


    /**
     * 根据code Id 和 code type 得到code
     *
     * @param codeId
     * @param codeType
     * @return
     */
    @Override
    public CodeStaticTable getByCodeIdAndCodeType(String codeId, String codeType) {

        CodeStaticTable searchCode = new CodeStaticTable();
        searchCode.setCodeType(codeType);
        searchCode.setCodeIdCd(codeId);

        CodeStaticTable code = codeStaticTableDao.selectOne(new QueryWrapper<>(searchCode));

        return code;
    }

    /**
     * 得到一个分类
     *
     * @param codeType
     * @return
     */

    //@Cacheable(value = "codeStaticList" , key = "#codeType" )
    @Override
    public List<CodeStaticTable> getCodeListByCodeType(String codeType) {
        CodeStaticTable searchCode = new CodeStaticTable();
        searchCode.setCodeType(codeType);

        List result = codeStaticTableDao.selectList(new QueryWrapper<>(searchCode));
        return result;
    }

    /**
     * 获取code static table 所有数据
     *
     * @return
     */
    @Override
    public List<CodeStaticTable> getFullCodeList() {
        return codeStaticTableDao.selectList(new QueryWrapper<>(new CodeStaticTable()));
    }

}
