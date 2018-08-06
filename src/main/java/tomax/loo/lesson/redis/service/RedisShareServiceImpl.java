package tomax.loo.lesson.redis.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import tomax.loo.lesson.redis.connection.jedis.RedisShareSettingBean;
import tomax.loo.lesson.redis.connection.jedis.ShareJedisPoolFactory;
import tomax.loo.lesson.redis.serializer.RedisSerializerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-07-20 17:18
 **/
public class RedisShareServiceImpl extends BaseRedisServiceImpl implements InitializingBean, RedisService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisSerializer<String> keySerializer = new StringRedisSerializer();

    private ShareJedisPoolFactory shareJedisPoolFactory;

    public ShardedJedisPool getShareJedisPool() {
        return shareJedisPoolFactory.getShardedJedisPool();
    }

    public void setShareJedisPoolFactory(ShareJedisPoolFactory shareJedisPoolFactory) {
        this.shareJedisPoolFactory = shareJedisPoolFactory;
    }

    private void returnResource(ShardedJedis shardedJedis) {
        try {
            if (getShareJedisPool() == null) {
                return;
            }
            getShareJedisPool().returnResource(shardedJedis);
        } catch (Exception e) {
            logger.error("returnResource error.", e);
        }
    }

    private void returnBrokenResource(ShardedJedis shardedJedis) {
        try {
            if (getShareJedisPool() == null) {
                return;
            }
            getShareJedisPool().returnBrokenResource(shardedJedis);
        } catch (Exception e) {
            logger.error("returnBrokenResource error", e);
        }
    }

    private String getValueSerializerType() {
        if (this.shareJedisPoolFactory != null && this.shareJedisPoolFactory.getRedisShareSettingBean() != null) {
            RedisShareSettingBean settingBean = this.shareJedisPoolFactory.getRedisShareSettingBean();
            return settingBean.getSerializerType();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        Assert.notNull(this.shareJedisPoolFactory, "shareedJedisPoolFactory  参数不能为空");
    }

    @Override
    public RedisSerializer<String> getKeySerializer() {
        return this.keySerializer;
    }

    @Override
    public RedisSerializer<Object> getValueSerializer() {
        String valueSerializerType = getValueSerializerType();
        return RedisSerializerFactory.getRedisSerializer(valueSerializerType);
    }

    @Override
    public <T extends Serializable> void setValue(String key, T value) {
        if (StringUtils.isBlank(key) || getShareJedisPool() == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShareJedisPool().getResource();
            shardedJedis.set(this.serializeFiled(key), this.serializeValue(value));
        } catch (Exception e) {
            logger.error("set error.key={}", key, e);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
    }

    @Override
    public void setString(String key, String vlue) {

    }

    @Override
    public <T extends Serializable> void setValue(String key, long timeoutSeconds, T value) {

    }

    @Override
    public void setValue(String key, long timeoutSeconds, String value) {

    }

    @Override
    public void setString(String key, long timeoutSeconds, String value) {

    }

    @Override
    public <T extends Serializable> boolean setValueNEX(String key, T value) {
        return false;
    }

    @Override
    public boolean setValueNEX(String key, Long value) {
        return false;
    }

    @Override
    public <T extends Serializable> boolean setValueNEX(String key, long timeoutSeconds, T value) {
        return false;
    }

    @Override
    public boolean setValueNEX(String key, long timeoutSeconds, Long value) {
        return false;
    }

    @Override
    public <T extends Serializable> T getValue(String key) {
        if (StringUtils.isBlank(key) || getShareJedisPool() == null) {
            return null;
        }
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = getShareJedisPool().getResource();
        } catch (Exception e) {
            logger.error("get error.key={}", key, e);
            returnBrokenResource(shardedJedis);
        } finally {
            returnResource(shardedJedis);
        }
        return null;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public Long getLong(String key) {
        return null;
    }

    @Override
    public Boolean isExists(String key) {
        return null;
    }

    @Override
    public boolean remove(String key) {
        return false;
    }

    @Override
    public String getType(String key) {
        return null;
    }

    @Override
    public boolean setExpire(String key, long seconds) {
        return false;
    }

    @Override
    public boolean setExpireAt(String key, Date date) {
        return false;
    }

    @Override
    public Long getTimeToLive(String key) {
        return null;
    }

    @Override
    public Boolean setBit(String key, long offset, boolean value) {
        return null;
    }

    @Override
    public Boolean getBit(String key, long offset) {
        return null;
    }

    @Override
    public Long decrement(String key, long number) {
        return null;
    }

    @Override
    public boolean isDecrement(String key) {
        return false;
    }

    @Override
    public boolean isDecrement(String key, long number) {
        return false;
    }

    @Override
    public Long decrement(String key) {
        return null;
    }

    @Override
    public Long increment(String key, long number) {
        return null;
    }

    @Override
    public Long increment(String key) {
        return null;
    }

    @Override
    public Integer append(String key, String value) {
        return null;
    }

    @Override
    public String subStr(String key, int start, int end) {
        return null;
    }

    @Override
    public <T extends Serializable> Boolean hashSet(String key, String field, T value) {
        return null;
    }

    @Override
    public Boolean hashExists(String key, String field) {
        return null;
    }

    @Override
    public <T extends Serializable> T hashGet(String key, String filed) {
        return null;
    }

    @Override
    public <T extends Serializable> Map<String, T> hashGetAll(String key) {
        return null;
    }

    @Override
    public <T extends Serializable> Boolean hashSetNx(String key, String field, T value) {
        return null;
    }

    @Override
    public Long hashDelete(String key, String field) {
        return null;
    }

    @Override
    public <T extends Serializable> Long rightPushList(String key, T value) {
        return null;
    }

    @Override
    public Long rightPushListAll(String key, Serializable... values) {
        return null;
    }

    @Override
    public Long rightPushStringListAll(String key, String... value) {
        return null;
    }

    @Override
    public <T extends Serializable> Long rightPushListIfPresent(String key, T value) {
        return null;
    }

    @Override
    public <T extends Serializable> Long leftPushList(String kye, T value) {
        return null;
    }

    @Override
    public Long leftPushStringList(String key, String value) {
        return null;
    }

    @Override
    public Long leftPushListAll(String key, Serializable... values) {
        return null;
    }

    @Override
    public Long leftPushListAll(String key, String... values) {
        return null;
    }

    @Override
    public <T extends Serializable> Long leftPushListIfPresent(String key, T value) {
        return null;
    }

    @Override
    public <T extends Serializable> List<T> rangList(String key, long start, long end) {
        return null;
    }

    @Override
    public void trimList(String key, long start, long end) {

    }

    @Override
    public <T extends Serializable> Long removeList(String key, long i, T value) {
        return null;
    }

    @Override
    public <T extends Serializable> T indexList(String key, long index) {
        return null;
    }

    @Override
    public <T extends Serializable> T leftPopList(String key) {
        return null;
    }

    @Override
    public <T extends Serializable> T leftPopListThroeingException(String key) throws Throwable {
        return null;
    }

    @Override
    public String leftPopStringList(String key) {
        return null;
    }

    @Override
    public <T extends Serializable> T leftPopList(String key, long timeoutSeconds) {
        return null;
    }

    @Override
    public <T extends Serializable> T rightPopList(String key) {
        return null;
    }

    @Override
    public String rightPopStringList(String key) {
        return null;
    }

    @Override
    public <T extends Serializable> T rightPopList(String key, long timeoutSeconds) {
        return null;
    }

    @Override
    public Long sizeList(String key) {
        return null;
    }

    @Override
    public Long addSetValues(String key, Serializable... values) {
        return null;
    }

    @Override
    public <T extends Serializable> Boolean isSetContains(String key, T o) {
        return null;
    }

    @Override
    public <T extends Serializable> Set<T> getSetAll(String key) {
        return null;
    }

    @Override
    public <T extends Serializable> Boolean moveSetValue(String key, T value, String destKey) {
        return null;
    }

    @Override
    public <T extends Serializable> T randomSetValue(String key) {
        return null;
    }

    @Override
    public <T extends Serializable> Set<T> distinctRandomSetValues(String key, long count) {
        return null;
    }

    @Override
    public <T extends Serializable> List<T> randomSetValues(String key, long count) {
        return null;
    }

    @Override
    public Long removeSetValues(String key, Object... values) {
        return null;
    }

    @Override
    public <T extends Serializable> T popSetValue(String key) {
        return null;
    }

    @Override
    public Long sizeSet(String key) {
        return null;
    }

    @Override
    public <T extends Serializable> Boolean addZSet(String key, T value, double score) {
        return null;
    }

    @Override
    public <T extends Serializable> Double incrementZSetScore(String key, T value, double delta) {
        return null;
    }

    @Override
    public <T extends Serializable> Long rankZSet(String key, T o) {
        return null;
    }

    @Override
    public <T extends Serializable> Long reverseZSetRank(String key, T o) {
        return null;
    }

    @Override
    public <T extends Serializable> Double scoreZSet(String key, T o) {
        return null;
    }

    @Override
    public Long removeZSet(String key, Object... values) {
        return null;
    }

    @Override
    public Long removeZSetRange(String key, long start, long end) {
        return null;
    }

    @Override
    public Long removeZSetRangeByScore(String key, double min, double max) {
        return null;
    }

    @Override
    public Long countZSet(String key, double min, double max) {
        return null;
    }
}
