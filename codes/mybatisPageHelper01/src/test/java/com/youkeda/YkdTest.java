package com.youkeda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.comment.dao.CommentDAO;
import com.youkeda.comment.dataobject.CommentDO;
import com.youkeda.comment.model.Paging;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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

        Paging<CommentDO> commentDOS = httpGet("comments");


        Assertions.assertThat(commentDOS.getData().size()).isEqualTo(3);

        Assertions.assertThat(commentDOS.getTotalCount()).isGreaterThan(3);


    }

    private Paging<CommentDO> httpGet(String url) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + randomServerPort + "/" + url;
        URI uri = new URI(baseUrl);

        String value = restTemplate.exchange(uri, HttpMethod.GET, null, String.class).getBody();

        return mapper.readValue(value, new TypeReference<Paging<CommentDO>>() {
        });
    }
}
