package com.wybs.mbti.test;

import com.alibaba.fastjson.JSONObject;
import com.wybs.uud.UudApp;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>认证接口测试</p>
 *
 * <p>Date：2018-04-02</p>
 * 
 * @author Mumus
 */
public abstract class AbstractApiAuthTest extends AbstractApiTest {
    protected static String token;

    private static final Logger logger = LoggerFactory.getLogger(AbstractApiAuthTest.class);

    @BeforeClass
    public static void creatToken() {
        UudApp uudApp;
        try {
            uudApp = new UudApp(1450, "805c6c33a5014dbf8b78722e42aced7f", "http://114.215.253.9:8888");
             JSONObject json = uudApp.login("13143374613", "@100tian");
//            JSONObject json = uudApp.login("18825070757", "12345678");
            // JSONObject json = uudApp.login("13711544592", "11111111");
            
            token = json.getString("token");

            logger.debug("creatToken() - get token response - json={}", json);
        } catch (Exception e) {
            logger.error("creatToken()", e);
        }
    }

    @Override
    protected Map<String, Object> getPublicParams() {
        Map<String, Object> params = super.getPublicParams();
        params.put("token", token);
        return params;
    }
}
