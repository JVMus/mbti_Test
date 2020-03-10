package com.wybs.mbti.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wybs.mbti.common.util.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>登录状态问答接口测试类</p>
 *
 * <p>Date: 2018-04-14</p>
 *
 * @author Mumus
 */
public class AnswerApiAuthTest extends AbstractApiAuthTest {
    private static final Logger logger = LoggerFactory.getLogger(AnswerApiAuthTest.class);

    public void getDescription() {
        Map<String, Object> params = getPublicParams();

        String url = UrlUtil.buildUrl(host + "/type/description", params);
        logger.debug("host={}", host);
        String body = null;
        try {
            body = doGet(url);
        } catch (Exception e) {
            logger.error("testGet() - url={}, body={}", url, body, e);
        }
    }

    public void getQuestions() {
        Map<String, Object> params = getPublicParams();

        String url = UrlUtil.buildUrl(host + "/type/questions", params);
        logger.debug("host={}", host);
        String body = null;
        try {
            body = doGet(url);
        } catch (Exception e) {
            logger.error("testGet() - url={}, body={}", url, body, e);
        }
    }

    public void testCommitResult() throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("E", "22.5");
        map.put("I", "20.0");
        map.put("S", "23.5");
        map.put("N", "20.0");
        map.put("T", "24.0");
        map.put("F", "20.0");
        map.put("J", "25.0");
        map.put("P", "20.0");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        Map<String, Object> params = getPublicParams();

        params.put("result", json);

        String url = host + "/type/result";

        String body = null;
        try {
            body = doPost(url, params);
        } catch (Exception e) {
            logger.error("testGetOrder() - url={}, body={}", url, body, e);
        }
    }
}
