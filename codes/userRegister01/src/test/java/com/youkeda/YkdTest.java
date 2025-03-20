package com.youkeda;

import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import org.junit.jupiter.api.Test;

class YkdTest {

    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {

        Result<User> result = new Result<User>();

    }

}
