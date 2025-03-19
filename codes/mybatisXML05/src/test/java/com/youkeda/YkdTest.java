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
    private CommentDAO commentDAO;
    @Autowired
    private ObjectMapper mapper;

    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {

        List<CommentDO> all = commentDAO.findAll();

        Assertions.assertThat(all.size()).isGreaterThanOrEqualTo(1);

        List<CommentDO> byUserName = commentDAO.findByRefId(all.get(all.size() - 1).getRefId());
        Assertions.assertThat(byUserName).isNotNull();
        Assertions.assertThat(byUserName).isNotEmpty();


        URL resource = CommentDAO.class.getResource("CommentDAO.xml");
        String text = new BufferedReader(new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

        if (!text.contains("<select")) {
            error("CommentDAO.xml 没有创建 select 语句");
        }
    }

}
