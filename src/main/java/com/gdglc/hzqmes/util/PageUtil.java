package com.gdglc.hzqmes.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdglc.hzqmes.common.form.PageForm;


/**
 * @author:ZhongGuoce
 * @date:2019-01-21
 * @time:21:50
 */
public class PageUtil {

    public static IPage initPage(PageForm pageForm){
        int current= pageForm.getCurrent();
        int size= pageForm.getSize();

        if(current<1){
            current=1;
        }
        if(size<1){
            size=10;
        }
        return new Page(current, size);
    }
}
