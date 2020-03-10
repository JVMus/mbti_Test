package com.wybs.mbti.dal.service;

import com.wybs.mbti.biz.cache.DescriptionCache;
import com.wybs.mbti.dal.model.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>测试结果服务</p>
 *
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@Service
public class DescriptionService {
    private static final Logger logger = LoggerFactory.getLogger(DescriptionService.class);

    @Autowired
    private DescriptionCache descriptionCache;

    /**
     * 根据用户ID获取用户测试结果
     *
     * @param uudId 用户ID
     * @return
     */
    public Description getByUserId(Integer uudId) {
        logger.debug("getByUserId() - user get description - uid={}", uudId);

        return descriptionCache.getOne(uudId);

    }
}
