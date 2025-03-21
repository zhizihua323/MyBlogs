package com.youkeda;

import com.youkeda.comment.dao.UserDAO;
import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import com.youkeda.comment.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YkdTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;

    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {

        Result<User> register1 = userService.login(null, "123");

        if (!"600".equals(register1.getCode())) {
            error("没有判断用户名为空为null的情况");
            return;
        }

        register1 = userService.login("1", "");

        if (!"601".equals(register1.getCode())) {
            error("没有判断密码为空为null的情况");
            return;
        }

        long l = System.currentTimeMillis();
        String userName = "t" + l;
        Result<User> register = userService.register(userName, "123");

        Result<User> login = userService.login(userName, "1234");

        if (!"603".equals(login.getCode())) {
            error("没有正确的判断密码");
            return;
        }

        login = userService.login(userName, "123");

        if (login.getData().getId() == 0) {
            error("没有正确的返回 User 对象");
            return;
        }

    }

}
