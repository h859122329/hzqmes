package com.gdglc.hzqmes;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.OrderForm;
import com.gdglc.hzqmes.common.form.PageForm;
import com.gdglc.hzqmes.common.form.UsersForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.dao.UsersDao;
import com.gdglc.hzqmes.po.Users;
import com.gdglc.hzqmes.service.OrdersService;
import com.gdglc.hzqmes.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HzqmesApplicationTests {
    @Autowired
    private UsersService usersService;


    @Autowired
    private UsersDao usersDao;
    @Test
    public void testGetUserByUserName(){
        Optional<Users> admin = usersService.getUserByUsername("admin");
        System.out.println(admin);
    }

    @Test
    public void testGetUsersByCondition() {
        PageForm pageForm = new PageForm();
        pageForm.setCurrent(1);
        pageForm.setSize(11);
        UsersForm usersForm =new UsersForm();
        usersForm.setPageForm(pageForm);
        IPage<Users> page= usersService.getByCondition(usersForm);
        for (int i = 0; i < page.getRecords().size(); i++) {
            System.out.println(page.getRecords().get(i));
        }
        System.out.println("total:"+ page.getTotal());
    }


}
