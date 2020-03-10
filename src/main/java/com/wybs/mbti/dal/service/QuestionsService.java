package com.wybs.mbti.dal.service;

import com.wybs.mbti.dal.mapper.QuestionsMapper;
import com.wybs.mbti.dal.model.Questions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>测试题服务</p>
 *
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@Service
public class QuestionsService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionsService.class);

    @Autowired
    private QuestionsMapper questionsMapper;

    /**
     * 获取所有测试题
     *
     * @return
     */
    public List<Questions> selectAll() {
        logger.debug("selectAll() - select all questions from database");

        return questionsMapper.selectByExample(null);
    }

}
