package com.wybs.mbti.web.interceptor;

import com.wybs.mbti.common.constant.Constants;
import com.wybs.mbti.common.data.ResponseData;
import com.wybs.mbti.common.data.StatusCode;
import com.wybs.mbti.common.util.JsonUtil;
import com.wybs.mbti.common.util.UrlUtil;
import com.wybs.mbti.common.uud.UudAppClient;
import com.wybs.mbti.common.uud.UudUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>认证拦截器</p>
 *
 * <p>Date：2018-05-03</p>
 *
 * @author Mumus
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private static final String TOKEN = "token";

    @Autowired
    private UudAppClient uudAppClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (logger.isDebugEnabled()) {
            String url = getRequestUrl(request);
            logger.debug("preHandle() - {}", url);
        }

        if (isMissParam(request, TOKEN)) {
            write(response, HttpStatus.BAD_REQUEST, StatusCode.MISS_PARAM);
            return false;
        }

        String token = request.getParameter(TOKEN);
        ResponseData<Boolean> checkToken = uudAppClient.checkToken(token);
        if (StatusCode.OK.value() != checkToken.getRetCode()) {
            write(response, HttpStatus.UNAUTHORIZED, StatusCode.valueOf(checkToken.getRetCode()));
            return false;
        }

        ResponseData<UudUser> detail = uudAppClient.getDetail(token);

        if (StatusCode.OK.value() != detail.getRetCode()) {
            write(response, HttpStatus.UNAUTHORIZED, StatusCode.valueOf(detail.getRetCode()));
            return false;
        }

        request.setAttribute(Constants.UUD_USER_KEY, detail.getData());

        return super.preHandle(request, response, handler);
    }

    private boolean isMissParam(HttpServletRequest request, String... paramNames) {
        boolean isMiss = false;
        for (String paramName : paramNames) {
            String value = request.getParameter(paramName);
            if (org.apache.commons.lang3.StringUtils.isBlank(value)) {
                isMiss = true;

                logger.error("isMissParam() - miss param - param={}, type=java.lang.String", paramName);

                break;
            }
        }
        return isMiss;
    }

    private void write(HttpServletResponse response, HttpStatus httpStatus, StatusCode statusCode) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(httpStatus.value());
        ResponseData<Object> resp = new ResponseData<>(statusCode.value(), statusCode.message());
        String jsonStr = JsonUtil.toJson(resp);
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonStr);
        } catch (IOException e) {
            logger.error("write() - response content error - content=" + jsonStr, e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String getRequestUrl(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getMethod()).append(" ");
        builder.append(request.getRequestURL());
        String query = UrlUtil.buildOriginalQueryString(request.getParameterMap());
        if (!"".equals(query)) {
            builder.append("?").append(query);
        }
        return builder.toString();
    }
}
