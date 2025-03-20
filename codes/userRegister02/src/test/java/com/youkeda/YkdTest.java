package com.youkeda;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import com.youkeda.comment.service.UserService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class YkdTest {

    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {

        Method register = UserService.class.getDeclaredMethod("register", String.class, String.class);



    }

}
