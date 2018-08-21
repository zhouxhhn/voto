package bjl.core.redis;

import bjl.infrastructure.persistence.redis.IRedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengyi on 2016/3/21.
 */
@Service("redisService")
public class RedisService {

    private final IRedisClientTemplate redisClientTemplate;

    @Autowired
    public RedisService(IRedisClientTemplate redisClientTemplate) {
        this.redisClientTemplate = redisClientTemplate;
    }

    public boolean exists(final String key) {
        return redisClientTemplate.exists(key);
    }

    public void addCache(final String key, final String value) {
        redisClientTemplate.set(key, value);
    }

    public void addCache(final String key, final String value, final int seconds) {
        redisClientTemplate.set(key, value, seconds);
    }

    public String getCache(final String key) {
        return redisClientTemplate.get(key);
    }

    public List getCaches(final String key) {
        String a = redisClientTemplate.get(key);
        List<String> b = new ArrayList<>();
        b.add(a);
        return b;
    }

    public void delete(final String key) {
        if (redisClientTemplate.exists(key)) {
            redisClientTemplate.del(key);
        }
    }

}
