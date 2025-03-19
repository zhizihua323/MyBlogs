package com.youkeda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dataobject.CommentDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

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

        CommentDO commentDO = new CommentDO();

        commentDO.setContent("123");
        commentDO.setRefId("T1");
        commentDO.setUserId(1);

        commentDAO.insert(commentDO);

        List<CommentDO> t1 = commentDAO.findByRefId("T1");
        CommentDO commentDO1 = t1.get(t1.size() - 1);

        commentDO1.setContent(null);

        commentDAO.update(commentDO1);

        t1 = commentDAO.findByRefId("T1");
        commentDO1 = t1.get(t1.size() - 1);

        Assertions.assertNotNull(commentDO1.getContent());

    }

}
