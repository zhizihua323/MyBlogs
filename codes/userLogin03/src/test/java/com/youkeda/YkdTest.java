package com.youkeda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.comment.dao.UserDAO;
import com.youkeda.comment.model.Result;
import com.youkeda.comment.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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

        long l = System.currentTimeMillis();
        String userName = "t" + l;

        httpPost("/api/user/reg?userName=" + userName + "&pwd=123");


        Result<User> result = httpPost("/api/user/login?userName=" + userName + "&pwd=123");

        if (result.getData() == null) {
            error("没有正确处理返回数据");
            return;
        }

        File file = new File("src/main/java/com/youkeda/comment/api/UserAPI.java");
        String text = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

        if (!text.contains("userId")){
            error("没有正确的把 userId 存储到 session");
        }


    }

    private Result<User> httpPost(String url) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + randomServerPort + "/" + url;
        URI uri = new URI(baseUrl);

        //        HttpHeaders headers = new HttpHeaders();
        //        headers.setContentType(MediaType.APPLICATION_JSON);
        //        HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(param), headers);

        String value = restTemplate.exchange(uri, HttpMethod.POST, null, String.class).getBody();

        return mapper.readValue(value, new TypeReference<Result<User>>() {
        });
    }
}
