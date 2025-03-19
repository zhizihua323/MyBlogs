package com.youkeda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dao.UserDAO;
import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.dataobject.UserDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YkdTest {

    @LocalServerPort
    int randomServerPort;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ObjectMapper mapper;

    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {

        UserDO userDO = new UserDO();

        userDO.setNickName("DDDD_1");
        userDO.setUserName("test_1");
        userDO.setPwd("123");

        userDAO.add(userDO);

        userDO = new UserDO();
        userDO.setNickName("nnnn_1");
        userDO.setUserName("DDDD_user");
        userDO.setPwd("123");
        userDAO.add(userDO);

        List<UserDO> userDOS = userDAO.query("DDDD");

        Assertions.assertThat(userDOS.size()).isGreaterThanOrEqualTo(2);

        for (UserDO aDo : userDOS) {
            userDAO.delete(aDo.getId());
        }


    }

}
