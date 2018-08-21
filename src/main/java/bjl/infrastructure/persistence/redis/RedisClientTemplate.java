package bjl.infrastructure.persistence.redis;

import bjl.core.util.CoreDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Author pengyi
 * Date 16-12-20.
 */
@Repository("redisClientTemplate")
public class RedisClientTemplate implements IRedisClientTemplate {

    private static final Logger log = LoggerFactory.getLogger(RedisClientTemplate.class);

    private final RedisDataSource redisDataSource;

    @Autowired
    public RedisClientTemplate(RedisDataSource redisDataSource) {
        this.redisDataSource = redisDataSource;
    }

    @Override
    public void disconnect() {
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        shardedJedis.disconnect();
    }

    /**
     * 设置单个值
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    @Override
    public String set(String key, String value) {
        String result = null;

        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 设置值（带存活时间）
     *
     * @param key 键
     * @param value 值
     * @param seconds 存活时间
     * @return
     */
    @Override
    public String set(String key, String value, int seconds) {
        String result = null;

        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.setex(key,seconds,value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 获取单个值
     *
     * @param key 键
     * @return 结果
     */
    @Override
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }

        try {
            result = shardedJedis.get(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    @Override
    public Boolean exists(String key) {
        Boolean result = false;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return false;
        }
        try {
            result = shardedJedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    @Override
    public String type(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.type(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    /**
     * 在某段时间后实现
     *
     * @param key     键
     * @param seconds 时间
     * @return 返回
     */
    @Override
    public Long expire(String key, int seconds) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    @Override
    public String getSet(String key, String value) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.getSet(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    @Override
    public Long append(String key, String value) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.append(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    @Override
    public String substr(String key, int start, int end) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.substr(key, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    @Override
    public Long del(String key) {
        Long result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.del(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    @Override
    public List<String> sort(String key) {
        List<String> result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return null;
        }
        try {
            result = shardedJedis.sort(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }


    /**
     * 获取分布式锁
     *
     * @param lockedKey 建议业务名称+业务主键
     * @param timeout   获取分布式锁的超时时间(毫秒)
     * @return true：获取锁成功，fasle:获取锁失败
     */
    public boolean lock(String lockedKey, long timeout) {
        try (ShardedJedis shardedJedis = redisDataSource.getRedisClient()) {
            long nano = System.nanoTime();
            long timeoutNanos = timeout * 1000000L;
            while ((System.nanoTime() - nano) < timeoutNanos) {
                if (shardedJedis.setnx(lockedKey, CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmssSSS")) == 1) {
                    shardedJedis.expire(lockedKey, 300);
                    log.info("RedisSimpleLockUtils.lock-->lockedKey=‘{}’ , timeout={}毫秒", lockedKey, timeout);
                    return true;
                }
                // 短暂休眠，nano避免出现活锁
                Thread.sleep(2, new Random().nextInt(500));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取分布式锁
     *
     * @param lockedKey 建议业务名称+业务主键
     * @return true：获取锁成功，fasle:获取锁失败
     */
    public boolean lock(String lockedKey) {
        return lock(lockedKey, 6);
    }

    /**
     * 获取调度锁
     * 注意点：1.无需释放锁；2.任务调度周期>5分钟  && 任务处理耗时 > 1秒
     *
     * @param lockedKey 调度锁的key
     * @return true：获取锁成功，fasle:获取锁失败
     * @desc instanceMaxDiffTime：集群下各实例所在应用服务器的最大时间差
     * timeout：超时时间，默认为1秒
     * tasktime：任务执行所需时间
     * expireTime：锁有效时间，默认为5分钟
     * scheduletime：调度周期
     * 调度锁有效条件： 0<=instanceMaxDiffTime<timeout<tasktime<expireTime<scheduletime
     * 此方法默认不调用解锁下,锁的有效条件：1秒<tasktime<5分钟<scheduletime
     */
    public boolean scheduleLock(String lockedKey) {
        return lock(lockedKey, 300);
    }

    /**
     * 释放分布式锁
     *
     * @param lockedKey 建议业务名称+业务主键
     */
    public void unlock(String lockedKey) {
        try (ShardedJedis shardedJedis = redisDataSource.getRedisClient()) {
            Long del = shardedJedis.del(lockedKey);
            log.info("RedisSimpleLockUtils.unlock-->lockedKey=‘{}’ ,del lock={}", lockedKey, del);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
