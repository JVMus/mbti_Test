package com.wybs.mbti.dal.service;

import com.alibaba.fastjson.JSON;
import com.wybs.mbti.biz.cache.DescriptionCache;
import com.wybs.mbti.dal.example.DescriptionExample;
import com.wybs.mbti.dal.example.UserResultExample;
import com.wybs.mbti.dal.mapper.DescriptionMapper;
import com.wybs.mbti.dal.mapper.UserResultMapper;
import com.wybs.mbti.dal.model.Description;
import com.wybs.mbti.dal.model.UserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * <p>用户测试结果服务</p>
 * <p>
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@Service
public class UserResultService {
    private static final Logger logger = LoggerFactory.getLogger(UserResultService.class);

    @Autowired
    private UserResultMapper userResultMapper;

    @Autowired
    private DescriptionMapper descriptionMapper;

    @Autowired
    private DescriptionCache descriptionCache;

    /**
     * 保存/更新用户测试结果
     *
     * @param uudId  用户ID
     * @param result 测试结果
     */
    public int saveResult(Integer uudId, String result) {
        logger.debug("saveResult() - user save test result - uid={}, result={}", uudId, result);

        Object succesResponse = JSON.parse(result);

        Map<String, String> map = (Map<String, String>)succesResponse;

        StringBuilder sb = new StringBuilder();

        Double e = Double.valueOf(map.get("E"));
        Double i = Double.valueOf(map.get("I"));
        Double s = Double.valueOf(map.get("S"));
        Double n = Double.valueOf(map.get("N"));
        Double t = Double.valueOf(map.get("T"));
        Double f = Double.valueOf(map.get("F"));
        Double j = Double.valueOf(map.get("J"));
        Double p = Double.valueOf(map.get("P"));

        map.get("E");

        if (e > i) {
            sb.append("E");
        } else {
            sb.append("I");
        }
        if (s > n) {
            sb.append("S");
        } else {
            sb.append("N");
        }
        if (t >f) {
            sb.append("T");
        } else {
            sb.append("F");
        }
        if (j > p) {
            sb.append("J");
        } else {
            sb.append("P");
        }

        String res = sb.toString();

        UserResultExample example = new UserResultExample();
        example.createCriteria().andUudIdEqualTo(uudId);
        UserResult userResult = userResultMapper.selectOneByExample(example);
        Date date = new Date();
        int row = 0;
        if (userResult != null) {
            descriptionCache.deleteOne(uudId);
            userResult.seteTotal(e);
            userResult.setsTotal(s);
            userResult.setiTotal(i);
            userResult.setfTotal(f);
            userResult.setpTotal(p);
            userResult.setjTotal(j);
            userResult.setnTotal(n);
            userResult.settTotal(t);
            userResult.setResultCode(res);
            userResult.setUpdateTime(date);

            row = userResultMapper.updateByPrimaryKeySelective(userResult);
        } else {
            userResult = new UserResult();
            userResult.setUudId(uudId);
            userResult.setResultCode(res);
            userResult.setIsFinish(1);
            userResult.seteTotal(e);
            userResult.setsTotal(s);
            userResult.setiTotal(i);
            userResult.setfTotal(f);
            userResult.setpTotal(p);
            userResult.setjTotal(j);
            userResult.setnTotal(n);
            userResult.settTotal(t);
            userResult.setCreateTime(date);
            userResult.setUpdateTime(date);

            row = userResultMapper.insertSelective(userResult);
        }

        DescriptionExample descriptionExample = new DescriptionExample();
        descriptionExample.createCriteria().andResultCodeEqualTo(res);
        Description description = descriptionMapper.selectOneByExample(descriptionExample);
        descriptionCache.saveOne(uudId, description);

        return row;

    }
}
