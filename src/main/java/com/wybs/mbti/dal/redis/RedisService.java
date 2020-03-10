package com.wybs.mbti.dal.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>Redis操作类</p>
 *
 * <p>Date：2018-05-03</p>
 *
 * @author Mumus
 */
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /**
     * 按有效期缓存值
     *
     * @param key 缓存KEY
     * @param value 值
     * @param liveTime 存活时间 单位秒
     */
    public void set(final String key, final String value, final long liveTime) {
        redisTemplate.opsForValue().set(key, value);
        if (liveTime > 0) {
            redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
        }
    }
    
    /**
     * 值缓存
     *
     * @param key 缓存KEY
     * @param value 值
     */
    public void set(final String key, final String value) {
        set(key, value, 0);
    }
    
    public void multiSet(final Map<String, String> map, final long liveTime) {
        redisTemplate.opsForValue().multiSet(map);
        if (liveTime > 0) {
            map.keySet().forEach(key -> redisTemplate.expire(key, liveTime, TimeUnit.SECONDS));
        }
    }
    
    public void multiSet(final Map<String, String> map) {
        multiSet(map, 0);
    }
    
    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 SETNX 不做任何动作。
     *
     * @param key 缓存KEy
     * @param value 值
     * @return 是否设置 true－设置成功，false－未设置
     */
    public boolean setnx(final String key, final String value) {
        return setnx(key, value, 0);
    }
    
    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则 SETNX 不做任何动作。
     *
     * @param key 缓存KEY
     * @param value 值
     * @param liveTime 存活时间 单位秒
     * @return 是否设置 true－设置成功，false－未设置
     */
    public boolean setnx(final String key, final String value, final long liveTime) {
        boolean success = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (success && liveTime > 0) {
            expire(key, liveTime);
        }
        
        return success;
    }
    
    /**
     * 获取指定KEY值
     *
     * @param key 缓存KEY
     * @return 值
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     *
     * @param key 缓存KEY
     * @param value 值
     * @param liveTime 存活时间 单位秒
     * @return 旧值
     */
    public String getSet(final String key, final String value, final long liveTime) {
        String oldVal = redisTemplate.opsForValue().getAndSet(key, value);
        if (liveTime > 0) {
            expire(key, liveTime);
        }
        return oldVal;
    }
    
    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     *
     * @param key 缓存KEY
     * @param value 值
     * @return 旧值
     */
    public String getSet(final String key, final String value) {
        return getSet(key, value, 0);
    }

    /**
     * map缓存
     * 
     * @param key 缓存key
     * @param map map数据
     * @param liveTime 缓存时间 单位秒
     */
    public void hset(final String key, final Map<String, String> map, final long liveTime) {
        redisTemplate.opsForHash().putAll(key, map);
        if (liveTime > 0) {
            redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
        }
    }

    /**
     * map缓存
     * 
     * @param key 缓存key
     * @param map map数据
     */
    public void hset(final String key, final Map<String, String> map) {
        hset(key, map, 0);
    }

    /**
     * 缓存map元素
     * 
     * @param key 缓存key
     * @param hashKey map key
     * @param value map 值
     * @param liveTime 缓存时间 单位秒
     */
    public void hset(final String key, final String hashKey, final String value, final long liveTime) {
        boolean updateCacheTime = !redisTemplate.hasKey(key);

        redisTemplate.opsForHash().put(key, hashKey, value);

        if (liveTime > 0 && updateCacheTime) {
            logger.debug("hset() - the key livetime seconds - key={}, liveTime={}", key, liveTime);

            redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
        }
    }

    /**
     * 缓存map元素
     * 
     * @param key 缓存key
     * @param hashKey map key
     * @param value map 值
     */
    public void hset(final String key, final String hashKey, final String value) {
        hset(key, hashKey, value, 0);
    }
    
    /**
     * 获取map的所有值
     *
     * @param key 缓存key
     * @return values map所有值
     */
    public List<String> hvals(String key) {
        HashOperations<String, String, String> opt = redisTemplate.opsForHash();
        return opt.values(key);
    }

    /**
     * 获取map缓存
     * 
     * @param key 缓存key
     * @return map缓存
     */
    public Map<String, String> hget(String key) {
        HashOperations<String, String, String> opt = redisTemplate.opsForHash();
        return opt.entries(key);
    }

    /**
     * 获取map缓存中指定hash key的值
     * 
     * @param key 缓存key
     * @param hashKey map的hash key
     * @return map对应hash key的值
     */
    public String hget(final String key, final String hashKey) {
        HashOperations<String, String, String> opt = redisTemplate.opsForHash();
        return opt.get(key, hashKey);
    }
    
    /**
     * 批量获取指定hashkeys
     *
     * @param key cache key
     * @param hashKeys hashkey列表
     * @return 列表
     */
    public List<String> hget(final String key, final Collection<String> hashKeys) {
        HashOperations<String, String, String> opt = redisTemplate.opsForHash();
        return opt.multiGet(key, hashKeys);
    }

    /**
     * 删除缓存map对应 hash key列表
     * 
     * @param key 缓存key
     * @param hashKeys hash key 列表
     * @return 删除条数
     */
    public Long hdel(final String key, final Object... hashKeys) {
        HashOperations<String, String, String> opt = redisTemplate.opsForHash();
        return opt.delete(key, hashKeys);
    }

    /**
     * 判断缓存map中是否存在hash key
     * 
     * @param key 缓存key
     * @param hashKey map的hash key
     * @return true-存在，false-不存在
     */
    public boolean hhasKey(final String key, final String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取key对应的map大小
     * 
     * @param key 缓存key
     * @return map大小
     */
    public Long hsize(final String key) {
        return redisTemplate.opsForHash().size(key);
    }
    
    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     *
     * @param key 缓存KEY
     * @param values 集合
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素
     */
    public long sadd(final String key, final String... values) {
        Long size = redisTemplate.opsForSet().add(key, values);
        return size == null ? 0 : size;
    }
    
    /**
     * 移除并返回集合中的一个随机元素
     *
     * @param key 缓存KEY
     * @return 被移除的随机元素
     */
    public String spop(final String key) {
        return redisTemplate.opsForSet().pop(key);
    }
    
    /**
     * 回集合 key 的基数(集合中元素的数量)
     *
     * @param key 缓存KEY
     * @return 集合的基数
     */
    public long scard(final String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size == null ? 0 : size;
    }
    
    /**
     * 将 key 中储存的数字值增一
     *
     * @param key 缓存KEY
     * @return 执行增一之后 key 的值
     */
    public long incr(final String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    /**
     * 删除缓存key对应的缓存
     * 
     * @param keys 缓存key列表
     */
    public void del(final String... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        } else {
            redisTemplate.delete(Arrays.asList(keys));
        }
    }

    /**
     * 设置对应key的缓存时间
     * 
     * @param key 缓存key
     * @param liveTime 缓存时间，单位秒
     * @return true-成功，false-失败
     */
    public Boolean expire(final String key, final long liveTime) {
        return redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
    }
    
    public Boolean expireAt(final String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 获取key的过期时间（秒）
     * 
     * @param key 缓存key
     * @return 秒数
     */
    public Long getExpire(final String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 检查key是否存在
     * 
     * @param key 缓存key
     * @return true-存在，false-不存在
     */
    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }
}
