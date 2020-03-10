package com.wybs.mbti.web.controller;

import com.wybs.mbti.common.data.ResponseData;
import com.wybs.mbti.common.data.StatusCode;
import com.wybs.mbti.common.uud.UudUser;
import com.wybs.mbti.dal.service.UserResultService;
import net.bull.javamelody.MonitoredWithSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>测试结果接口</p>
 *
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@MonitoredWithSpring
@RestController
public class UserResultController {
    private static final Logger logger = LoggerFactory.getLogger(UserResultController.class);

    @Autowired
    private UserResultService userResultService;

    @PostMapping("/type/result")
    public ResponseData<Boolean> saveResult(@RequestAttribute("uudUser") UudUser uudUser, String result) {
        logger.debug("saveResult() - user save test result - uid={}, result={}", uudUser.getId(), result);

        int row = userResultService.saveResult(uudUser.getId(), result);
        if (row <= 0 ) {
            return new ResponseData<>(StatusCode.OK.value(), StatusCode.OK.message(), Boolean.FALSE);
        }

        return new ResponseData<>(StatusCode.OK.value(), StatusCode.OK.message(), Boolean.TRUE);
    }

}
