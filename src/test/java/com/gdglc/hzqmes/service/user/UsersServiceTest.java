package com.gdglc.hzqmes.service.user;

import com.gdglc.hzqmes.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * UsersService 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {
    @Autowired
    private UsersService usersService;
    /**
     * 根据用户ID修改用户的状态
     */
    @Test
    @Transactional
    public void testUpdateUserStatusById() {
        usersService.updateUserStatusById(1, 0);
    }

}


