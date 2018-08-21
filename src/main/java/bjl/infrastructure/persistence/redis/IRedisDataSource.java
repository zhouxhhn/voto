package bjl.infrastructure.persistence.redis;

import redis.clients.jedis.ShardedJedis;

/**
 * Author pengyi
 * Date 16-12-20.
 */
public interface IRedisDataSource {

    ShardedJedis getRedisClient();
}
