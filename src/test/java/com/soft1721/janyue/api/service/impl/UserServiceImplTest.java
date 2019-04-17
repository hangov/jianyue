package com.soft1721.janyue.api.service.impl;

import com.soft1721.janyue.api.entity.User;
import com.soft1721.janyue.api.entity.dto.UserDTO;
import com.soft1721.janyue.api.service.UserService;
import com.soft1721.janyue.api.util.StatusConst;
import com.soft1721.janyue.api.util.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Resource
    private UserService userService;

    @Test
    public void getUserByMobile() {
        User user = userService.getUserByMobile("18761676366");
        System.out.println(user);
    }

    @Test
    public void signIn() {
        UserDTO loginUser = new UserDTO();
        loginUser.setMobile("18761676366");
        String base64Pass = StringUtil.getBase64Encoder("111");
        loginUser.setPassword(base64Pass);
       /* int status = userService.signIn(loginUser);
        assertEquals(StatusConst.SUCCESS, status);*/
    }
    @Test
    public void signUp() {
        UserDTO userDTO = new UserDTO();
        userDTO.setMobile("18761676369");
        userDTO.setPassword("111");
        userService.signUp(userDTO);
    }

}