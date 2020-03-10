package com.wybs.mbti.biz.cache;

import com.wybs.mbti.common.util.JsonUtil;
import com.wybs.mbti.dal.model.Description;
import com.wybs.mbti.dal.redis.RedisService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>结果描述缓存类</p>
 * <p>
 * <p>Date: 2018-05-03</p>
 *
 * @author Mumus
 */
@Service
public class DescriptionCache {
    private static final Logger logger = LoggerFactory.getLogger(DescriptionCache.class);

    private static final String MBTI_DESCRIPTION_MAP_KEY = "mbti_description:map";
    private static final String MBTI_DESCRIPTION_MAP_LOCK_KEY = "mbti_description:lock";

    @Autowired
    private RedisService redisService;

    /**
     * 获取单个用户的测试结果
     *
     * @param uudId 用户ID
     * @return
     */
    public Description getOne(Integer uudId) {
        logger.debug("getOne() - get one description from cache - uid={}", uudId);

        String json = redisService.hget(getMapKey(), String.valueOf(uudId));

        logger.debug("getOne() - get one description from cache response - json={}", json);

        if (StringUtils.isBlank(json)) {
            return null;
        }

        return JsonUtil.fromJson(json, Description.class);
    }

    public String getMapKey() {
        return MBTI_DESCRIPTION_MAP_KEY;
    }

    /**
     * 根据用户ID删除单个缓存
     *
     * @param uudId 用户ID
     */
    public void deleteOne(Integer uudId) {
        logger.debug("deleteOne() - delete one cache - uid={}", uudId);

        redisService.hdel(getMapKey(), String.valueOf(uudId));
    }

    /**
     * 保存单条缓存
     *
     * @param uudId       用户ID
     * @param description 结果描述
     */
    public void saveOne(Integer uudId, Description description) {
        logger.debug("saveOne() - save one cache - uid={}, description={}", uudId, description);

        Map<String, String> jsonMap = new HashMap<>();
        String codeKey = String.valueOf(uudId);
        jsonMap.put(codeKey, JsonUtil.toJson(description));
        redisService.hset(getMapKey(), jsonMap);
    }
}
