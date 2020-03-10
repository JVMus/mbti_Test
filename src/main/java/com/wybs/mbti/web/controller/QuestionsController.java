package com.wybs.mbti.web.controller;

import com.wybs.mbti.common.data.ResponseData;
import com.wybs.mbti.common.data.StatusCode;
import com.wybs.mbti.common.uud.UudUser;
import com.wybs.mbti.dal.loader.QuestionsLoader;
import com.wybs.mbti.dal.model.Questions;
import com.wybs.mbti.dal.service.QuestionsService;
import com.wybs.mbti.web.json.annotation.JsonDyncFilter;
import net.bull.javamelody.MonitoredWithSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>测试题接口</p>
 *
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@MonitoredWithSpring
@RestController
public class QuestionsController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionsController.class);

    @Autowired
    private QuestionsService questionsService;

    @GetMapping("/type/questions")
    @JsonDyncFilter(target = Questions.class, includes = {"qNo", "title", "code", "countCode"})
    public ResponseData<List<Questions>> listQue(@RequestAttribute("uudUser") UudUser uudUser) {
        logger.debug("listQue() - user list questions - uid={}", uudUser.getId());

        return new ResponseData<>(StatusCode.OK.value(), StatusCode.OK.message(), QuestionsLoader.QUESTIONSES);
    }
}
