package bjl.infrastructure.persistence.redis;

import java.util.List;

/**
 * Author pengyi
 * Date 16-12-20.
 */
public interface IRedisClientTemplate {
    void disconnect();

    String set(String key, String value);

    String set(String key,String value,int seconds);

    String get(String key);

    Boolean exists(String key);

    String type(String key);

    Long expire(String key, int seconds);

    String getSet(String key, String value);

    Long append(String key, String value);

    String substr(String key, int start, int end);

    Long del(String key);

    List<String> sort(String key);

    boolean lock(String lockedKey, long timeout);

    boolean lock(String lockedKey);

    boolean scheduleLock(String lockedKey);

    void unlock(String lockedKey);
}
