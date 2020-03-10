package com.wybs.mbti.dal.loader;

import com.wybs.mbti.dal.model.Questions;
import com.wybs.mbti.dal.service.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>内存固化服务</p>
 *
 * <p>Date：2018-05-03</p>
 *
 * @author Mumus
 */
@Component
public class QuestionsLoader implements CommandLineRunner {
//    private static final Logger logger = LoggerFactory.getLogger(QuestionsLoader.class);

    @Autowired
    private QuestionsService questionsService;

    public static List<Questions> QUESTIONSES;

    @Override
    public void run(String... args) throws Exception {
        QUESTIONSES = questionsService.selectAll();
    }
}
