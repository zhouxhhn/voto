package bjl.infrastructure.persistence.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Author pengyi
 * Date 16-12-20.
 */
@Repository("redisDataSource")
public class RedisDataSource implements IRedisDataSource {

    private static final Logger log = LoggerFactory.getLogger(RedisDataSource.class);

    private final ShardedJedisPool shardedJedisPool;

    @Autowired
    public RedisDataSource(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public ShardedJedis getRedisClient() {
        try {
            return shardedJedisPool.getResource();
        } catch (Exception e) {
            log.error("getRedisClent error", e);
        }
        return null;
    }
}
