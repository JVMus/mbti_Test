package com.wybs.mbti.common.uud;

import com.alibaba.fastjson.JSONObject;
import com.wybs.mbti.common.data.ResponseData;
import com.wybs.mbti.common.data.StatusCode;
import com.wybs.mbti.common.exception.CreateBeanException;
import com.wybs.mbti.common.util.JsonUtil;
import com.wybs.uud.AdminApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建时间：2017年12月5日
 * <p>修改时间：2017年12月5日
 * <p>类说明：uud管理接口客户端
 * 
 * @author Mumus
 * @version 1.0
 */
public class UudAdminClient {
    private static final Logger logger = LoggerFactory.getLogger(UudAdminClient.class);
    private static final String RET_CODE = "retCode";

    private AdminApp uudAdmin;

    public UudAdminClient(Long accessId, String secretKey, String host) {
        try {
            uudAdmin = new AdminApp(accessId, secretKey, host);
        } catch (Exception e) {
            throw new CreateBeanException(e);
        }
    }

    /**
     * 根据ID获取uud用户数据
     *
     * @param uudId uud id
     * @return uud用户数据
     */
    public ResponseData<UudUser> getUser(Integer uudId) {
        JSONObject json = uudAdmin.searchAccount(uudId, null, null);
        logger.debug("getUser() - uud get user response  - json={}", json);

        StatusCode status = UudStatus.getStatusCode(json.getInteger(RET_CODE));
        if (StatusCode.OK != status) {
            logger.error("checkToken() - uud token invalid - uudId={}, json={}", uudId, json);
            return new ResponseData<>(status.value(), status.message());
        }

        UudUser uudUser = JsonUtil.fromJson(json.getString("vo"), UudUser.class);
        return new ResponseData<>(status.value(), status.message(), uudUser);
    }
}
