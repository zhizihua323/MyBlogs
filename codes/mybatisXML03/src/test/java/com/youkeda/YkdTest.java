package com.youkeda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.comment.control.CommentController;
import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dataobject.CommentDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class YkdTest {

    @LocalServerPort
    int randomServerPort;
    @Autowired
    private CommentController commentController;
    @Autowired
    private ObjectMapper mapper;

    public static void error(String msg) {
        System.err.println("<YkdError>" + msg + "</YkdError>");
    }

    @Test
    void contextLoads() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/comment/";
        URI uri = new URI(baseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CommentDO commentDO = new CommentDO();
        commentDO.setContent("测试" + randomServerPort);
        commentDO.setRefId("1000000000");
        commentDO.setUserId(1);

        HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(commentDO), headers);

        ResponseEntity<CommentDO> result = restTemplate.postForEntity(uri, request, CommentDO.class);
        CommentDO body = result.getBody();

        Assertions.assertThat(body.getContent()).contains("测试");

        Assertions.assertThat(body.getId()).isGreaterThan(0);

        URL resource = CommentDAO.class.getResource("CommentDAO.xml");
        String text = new BufferedReader(new InputStreamReader(resource.openStream(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

        if (!text.contains("<insert")){
            error("CommentDAO.xml 没有创建 insert 语句");
        }
    }

}
