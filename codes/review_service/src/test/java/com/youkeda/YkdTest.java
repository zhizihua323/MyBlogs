package com.youkeda;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.youkeda.comment.model.Comment;
import com.youkeda.comment.model.User;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class YkdTest {

    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {

        Field pwd = User.class.getDeclaredField("pwd");
        JsonSerialize annotation = pwd.getAnnotation(JsonSerialize.class);
        if (!annotation.using().equals(NullSerializer.class)) {
            error("User 对象的 pwd 属性没有正确提供 JsonSerialize 注解");
            return;
        }

        Field gmtCreated = User.class.getDeclaredField("gmtCreated");
        JsonFormat gmtCreatedJsonFormat = gmtCreated.getAnnotation(JsonFormat.class);
        if (gmtCreatedJsonFormat == null) {
            error("User 对象的 gmtCreated 属性没有正确提供 JsonFormat 注解");
            return;
        }




        Field gmtModified = User.class.getDeclaredField("gmtModified");
        JsonFormat jsonFormat = gmtModified.getAnnotation(JsonFormat.class);
        if (jsonFormat == null) {
            error("User 对象的 gmtCreated 属性没有正确提供 JsonFormat 注解");
            return;
        }

        Field gmtCreated1 = Comment.class.getDeclaredField("gmtCreated");
        JsonFormat gmtCreatedJsonFormat1 = gmtCreated1.getAnnotation(JsonFormat.class);
        if (gmtCreatedJsonFormat1 == null) {
            error("Comment 对象的 gmtCreated 属性没有正确提供 JsonFormat 注解");
            return;
        }

        Field gmtModified1 = Comment.class.getDeclaredField("gmtModified");
        JsonFormat jsonFormat1 = gmtModified1.getAnnotation(JsonFormat.class);
        if (jsonFormat1 == null) {
            error("Comment 对象的 gmtCreated 属性没有正确提供 JsonFormat 注解");
            return;
        }

    }

}
