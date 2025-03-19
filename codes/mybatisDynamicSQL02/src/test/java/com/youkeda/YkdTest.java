package com.youkeda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youkeda.comment.dao.UserDAO;
import com.youkeda.comment.dataobject.UserDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        List<UserDO> users = userDAO.findAll();

        for (UserDO user : users) {
            userDAO.delete(user.getId());
        }

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

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String start = URLEncoder.encode("2020-07-01 00:00:00");

        String end = URLEncoder.encode(now.format(formatter));

        List<UserDO> userDOS = httpGet("user/search?keyWord=DDDD&startTime=" + start + "&endTime=" + end);

        Assertions.assertThat(userDOS.size()).isEqualTo(2);

        for (UserDO aDo : userDOS) {
            userDAO.delete(aDo.getId());
        }

    }

    private List<UserDO> httpGet(String url) throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + randomServerPort + "/" + url;
        URI uri = new URI(baseUrl);

        String value = restTemplate.exchange(uri, HttpMethod.GET, null, String.class).getBody();

        return mapper.readValue(value, new TypeReference<List<UserDO>>() {
        });
    }
}
