package com.gdglc.hzqmes.common.form;

import com.gdglc.hzqmes.po.Users;
import lombok.Data;

/**
 * @author:ZhongGuoce
 * @date:2019-02-13
 * @time:11:59
 */
@Data
public class UsersForm {
    private Users user;
    private PageForm pageForm;
    private SearchForm searchForm;

    public UsersForm() {
    }

    public UsersForm(Users users, PageForm pageForm, SearchForm searchForm) {
        this.user = users;
        this.pageForm = pageForm;
        this.searchForm = searchForm;
    }
}
