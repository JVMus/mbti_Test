package com.wybs.mbti.common.uud;

import com.alibaba.fastjson.JSONObject;
import com.wybs.mbti.common.data.ResponseData;
import com.wybs.mbti.common.data.StatusCode;
import com.wybs.mbti.common.exception.CreateBeanException;
import com.wybs.mbti.common.util.JsonUtil;
import com.wybs.uud.UudApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>uud接口客户端</p>
 *
 * <p>Date：2018-05-03</p>
 *
 * @author Mumus
 */
public class UudAppClient {
    private static final Logger logger = LoggerFactory.getLogger(UudAppClient.class);
    private static final String RET_CODE = "retCode";
    private static final String TOKEN = "token";

    private UudApp uudApp;

    public UudAppClient(Long accessId, String secretKey, String host) {
        try {
            uudApp = new UudApp(accessId, secretKey, host);
        } catch (Exception e) {
            throw new CreateBeanException(e);
        }
    }

    /**
     * 验证token有效性
     *
     * @param token uud token
     * @return 验证数据
     */
    public ResponseData<Boolean> checkToken(String token) {
        JSONObject json = uudApp.checkToken(token);
        logger.debug("checkToken() - uud check token response  - token={}, json={}", token, json);

        StatusCode status = UudStatus.getStatusCode(json.getInteger(RET_CODE));
        if (StatusCode.OK != status) {
            logger.error("checkToken() - uud token invalid - token={}, json={}", token, json);
        }

        return new ResponseData<>(status.value(), status.message());
    }

    /**
     * 获取uud用户信息
     *
     * @param token uud token
     * @return 响应数据
     */
    public ResponseData<UudUser> getDetail(String token) {
        JSONObject json = uudApp.getUserDetail(token);
        logger.debug("getDetail() - uud get user detail response  - token={}, json={}", token, json);

        StatusCode status = UudStatus.getStatusCode(json.getInteger(RET_CODE));
        if (StatusCode.OK != status) {
            logger.error("checkToken() - uud token invalid - token={}, json={}", token, json);
            return new ResponseData<>(status.value(), status.message());
        }

        UudUser uudUser = JsonUtil.fromJson(json.getString("vo"), UudUser.class);
        return new ResponseData<>(status.value(), status.message(), uudUser);
    }


}
