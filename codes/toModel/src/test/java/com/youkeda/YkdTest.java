package com.youkeda;

import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.model.Comment;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

class YkdTest {


    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {


        File file = new File("src/main/java/com/youkeda/comment/service/impl/UserServiceImpl.java");
        String text = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

        if (text.contains("setId")) {
            error("没有正确的重构 UserServiceImpl 类哦");
        }

        CommentDO commentDO = new CommentDO();
        commentDO.setUserId(123L);
        Comment comment = commentDO.toModel();

        if (comment.getAuthor() == null || comment.getAuthor().getId() != 123) {
            error("CommentDO.toModel 方法没有正确的处理 author 属性");
        }

    }

}
