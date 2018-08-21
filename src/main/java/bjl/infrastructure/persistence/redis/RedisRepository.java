package bjl.infrastructure.persistence.redis;

import bjl.infrastructure.persistence.redis.generic.AbstractRedisGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi
 * Date : 2016/3/21.
 */
@Repository("redisRepository")
public class RedisRepository extends AbstractRedisGenericRepository<String, String> implements IRedisRepository<String, String> {

    @Autowired
    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate);
    }
}
