package com.youkeda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dataobject.CommentDO;
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

        CommentDO commentDO = all.get(all.size() - 1);
        commentDO.setContent("123");

        commentDAO.update(commentDO);

        List<CommentDO> byRefId = commentDAO.findByRefId(commentDO.getRefId());

        CommentDO commentDO1 = byRefId.stream().filter(i -> i.getId() == commentDO.getId()).findFirst().get();

        Assertions.assertThat(commentDO1.getContent()).isEqualTo("123");

        boolean result = commentDAO.delete(commentDO1.getId()) > 0;
        Assertions.assertThat(result).isEqualTo(true);


        URL resource = CommentDAO.class.getResource("CommentDAO.xml");
        String text = new BufferedReader(new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

        if (!text.contains("<update")) {
            error("CommentDAO.xml 没有创建 update 语句");
        }
        if (!text.contains("<delete")) {
            error("CommentDAO.xml 没有创建 delete 语句");
        }
    }

}
