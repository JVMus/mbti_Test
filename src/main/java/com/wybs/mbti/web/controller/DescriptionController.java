package com.wybs.mbti.web.controller;

import com.wybs.mbti.common.data.ResponseData;
import com.wybs.mbti.common.data.StatusCode;
import com.wybs.mbti.common.uud.UudUser;
import com.wybs.mbti.dal.model.Description;
import com.wybs.mbti.dal.service.DescriptionService;
import com.wybs.mbti.web.json.annotation.JsonDyncFilter;
import net.bull.javamelody.MonitoredWithSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>结果描述接口</p>
 *
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@MonitoredWithSpring
@RestController
public class DescriptionController {
    private static final Logger logger = LoggerFactory.getLogger(DescriptionController.class);

    @Autowired
    private DescriptionService descriptionService;

    /**
     * 获取用户测评结果
     *
     * @param uudUser 用户信息
     * @return
     */
    @GetMapping("/type/description")
    @JsonDyncFilter(target = Description.class, includes = {"resultCode", "resultType", "url"})
    public ResponseData<Description> get(@RequestAttribute("uudUser") UudUser uudUser) {
        logger.debug("get() - user get description - uid={}", uudUser.getId());

        Description description = descriptionService.getByUserId(uudUser.getId());
        if (description == null) {
            return new ResponseData<>(StatusCode.RESULT_NOT_FOUND.value(), StatusCode.RESULT_NOT_FOUND.message());
        }

        return new ResponseData<>(StatusCode.OK.value(), StatusCode.OK.message(), description);
    }

}
