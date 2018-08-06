package tomax.loo.lesson.redis.service;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisServiceImpl implements InitializingBean, RedisService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name="redisTemplate")
    private RedisTemplate<String, Serializable> redisTemplate;
    @Resource(name="redisTemplate")
    private ListOperations<String, Serializable> listOps;
    @Resource(name="redisTemplate")
    private ValueOperations<String, Serializable> valueOps;
    @Resource(name="redisTemplate")
    private ValueOperations<String, Long> longOps;
    @Resource(name="redisTemplate")
    private SetOperations<String, Serializable> setOps;
    @Resource(name="redisTemplate")
    private ZSetOperations<String, Serializable> zSetOps;

    /**
     * Returns the number of elements of the sorted set stored with given
     */
    public Long sizeZSet(String key) {
        try {
            this.zSetOps.size(key);
        } catch (Exception e) {
            logger.error("sizeZSet error.key=" + key, e);
        }
        return 0L;
    }

    /**
     * 序列化filed
     */
    private byte[] serializeFiled(final String filed) {
        return redisTemplate.getStringSerializer().serialize(filed);
    }

    /**
     * 反序列化filed
     * @param filed
     * @return
     */
    private String deserializeFiled(final byte[] filed) {
        return redisTemplate.getStringSerializer().deserialize(filed);
    }

    /**
     * 序列化value
     */
    @SuppressWarnings("unchecked")
    private byte[] serializeValue(final Serializable value) {
        return ((RedisSerializer<Serializable>)redisTemplate.getValueSerializer()).serialize(value);
    }

    /**
     * 序列化value
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    private Serializable deserializeValue(final byte[] value) {
        return ((RedisSerializer<Serializable>)redisTemplate.getValueSerializer()).deserialize(value);
    }

    @Override
    public <T extends Serializable> void setValue(String key, T value) {
        try {
            this.valueOps.set(key,value);
        } catch (Exception e) {
            logger.error("setValue error.key=" + key, e);
        }
    }

    @Override
    public void setString(final String key, final String value) {
        try {
            redisTemplate.execute((RedisCallback<byte[]>) redisConnection -> {
                byte[] bkey = serializeFiled(key);
                byte[] bvalue = serializeFiled(value);
                redisConnection.set(bkey, bvalue);
                return null;
            }, true);
        } catch (Exception e) {
            logger.error("setString error.key={}, value={}", key, value, e);
        }
        // JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        // RedisConnection connection = jedisConnectionFactory.getConnection();
        // redisTemplate.execute((RedisCallback<byte[]>) redisConnection -> {return null;});
        // redisTemplate.execute((RedisCallback<Long>) dfsdf -> {return null;});
        // new Thread(() -> System.out.println("Holy shit")).start();
    }

    @Override
    public <T extends Serializable> void setValue(String key, long timeoutSeconds, T value) {

    }

    @Override
    public void setValue(String key, long timeoutSeconds, String value) {

    }

    @Override
    public  void setString(String key, long timeoutSeconds, String value){
        try {
            redisTemplate.execute(new RedisCallback<byte[]>() {
                @Override
                public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] bkey = serializeFiled(key);
                    byte[] bvalue = serializeFiled(value);
                    redisConnection.setEx(bkey, timeoutSeconds, bvalue);
                    return null;
                }
            }, true);
        } catch (Exception e) {
            logger.error("setString error.key={}, timeoutSeconds={},value={}", key, timeoutSeconds, value, e);
        }
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

    @SuppressWarnings("uncheched")
    @Override
    public <T extends Serializable> T getValue(String key) {
        try {
           return (T) this.valueOps.get(key);
        } catch (Exception e) {
            logger.error("getValue error.key={}", key, e);
        }
        return null;
    }

    @Override
    public String getString(String key) {
        try {
            redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] bkey = serializeFiled(key);
                    byte[] bvalue = redisConnection.get(bkey);
                    return deserializeFiled(bvalue);
                }
            });
        } catch (Exception e) {
            logger.error("getString error.key={}", key, e);
        }
        return null;
    }

    @Override
    public Long getLong(String key) {
        String strValue = this.getString(key);
        if (NumberUtils.isNumber(strValue)) {
            return Long.valueOf(strValue);
        }
        return 0L;
    }

    @Override
    public Boolean isExists(String key) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                byte[] bkey = serializeFiled(key);
                return connection.exists(bkey);
            });
        } catch (Exception e) {
            logger.error("isExists error.key={}", key, e);
        }
        return false;
    }

    @Override
    public boolean remove(String key) {
        return false;
    }

    @Override
    public String getType(String key) {
        try {
            DataType type = redisTemplate.type(key);
            if (type == null) {
                return null;
            }
            return type.toString();
        } catch (Exception e) {
            logger.error("getType error.key={] ", key, e);
        }
        return null;
    }

    @Override
    public boolean setExpire(String key, long seconds) {
        try {
            return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("setExpire error.key={}, seconds={}", key, seconds);
        }
        return false;
    }

    @Override
    public boolean setExpireAt(String key, Date date) {
        try {
            return redisTemplate.expireAt(key, date);
        } catch (Exception e) {
            logger.error("setExpireAt error.key={}, date={}", key, date, e);
        }
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
        try {
            return this.valueOps.append(key, value);
        } catch (Exception e) {
            logger.error("append error.key={}", key, e);
        }
        return 0;
    }

    @Override
    public String subStr(String key, int start, int end) {
        try {
            return redisTemplate.execute((RedisCallback<String>) connection -> {
                byte[] bkey = serializeFiled(key);
                byte[] svalue = connection.getRange(bkey, start, end);
                return redisTemplate.getStringSerializer().deserialize(svalue);
            });
        } catch (Exception e) {
            logger.error("subStr error.key={}", key, e);
        }
        return null;
    }

    @Override
    public <T extends Serializable> Boolean hashSet(String key, String field, T value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                byte[] bkey = serializeFiled(key);
                byte[] bfield = serializeFiled(field);
                byte[] bvalue = serializeValue(value);
                return connection.hSet(bkey, bfield, bvalue);
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("hashSet error.key={}", key, e);
        }
        return false;
    }

    @Override
    public Boolean hashExists(String key, String field) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                byte[] bkey = serializeFiled(key);
                byte[] bfield = serializeFiled(field);
                return connection.hExists(bkey, bfield);
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("hashExists error.key={}", key, e);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T hashGet(String key, String filed) {
        try {
            return (T) redisTemplate.execute((RedisCallback<Serializable>) connection -> {
                byte[] bkey = serializeFiled(key);
                byte[] bfield = serializeFiled(filed);
                return deserializeValue(connection.hGet(bkey, bfield));
            });
        } catch (Exception e) {
            logger.error("hashGet error.key={}", key, e);
        }
        return null;
    }

    @Override
    public <T extends Serializable> Map<String, T> hashGetAll(String key) {
        try {
            return redisTemplate.execute((RedisCallback<Map<String, T>>) con -> {
                byte[] bkey = serializeFiled(key);
                Map<byte[], byte[]> byteMap = con.hGetAll(bkey);
                if (byteMap == null || byteMap.size() == 0) {
                    return null;
                }
                HashMap<String, T> result = new HashMap<>();
                Set<byte[]> fieldSet = byteMap.keySet();
                for (byte[] bfield : fieldSet) {
                    byte[] bvalue = byteMap.get(bfield);
                    String filed = deserializeFiled(bfield);
                    T value = (T) deserializeValue(bvalue);
                    result.put(filed, value);
                }
                return result;
            });
        } catch (Exception e) {
            logger.error("hashGetAll error.key={}", key, e);
        }
        return null;
    }

    @Override
    public <T extends Serializable> Boolean hashSetNx(String key, String field, T value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                byte[] bkey = serializeFiled(key);
                byte[] bfield = serializeFiled(field);
                byte[] bvalue = serializeValue(value);
                return connection.hSetNX(bkey, bfield, bvalue);
            });
        } catch (Exception e) {
            logger.error("hashSetNx error.key={}, field={}", key, field, e);
        }
        return null;
    }

    @Override
    public Long hashDelete(final String key, final String field) {
        try {
            return redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] bkey = serializeFiled(key);
                    byte[] bfield = serializeFiled(field);
                    return connection.hDel(bkey, bfield);
                }
            });
        } catch (Exception e) {
            logger.error("hashDelete error.key={}, field={}", key, field, e);
        }
        return 0L;
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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> rangList(String key, long start, long end) {
        try {
            return (List<T>) this.listOps.range(key, start, end);
        } catch (Exception e) {
            logger.error("rangList error.key={}", key, e);
        }
        return null;
    }

    @Override
    public void trimList(String key, long start, long end) {
        try {
            this.listOps.trim(key, start, end);
        } catch (Exception e) {
            logger.error("trimList error.key={}", key, e);
        }
    }

    @Override
    public <T extends Serializable> Long removeList(String key, long i, T value) {
        try {
            return this.listOps.remove(key, i, value);
        } catch (Exception e) {
            logger.error("removeList error.key={}", key, e);
        }
        return 0L;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T indexList(String key, long index) {
        try {
            return (T) this.listOps.index(key, index);
        } catch (Exception e) {
            logger.error("indexList error.key={}", key, e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T leftPopList(String key) {
        try {
            return (T) this.listOps.leftPop(key);
        } catch (Exception e) {
            logger.error("leftPopList error.key={}", key, e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T leftPopListThroeingException(String key) throws Throwable {
        try {
            return (T)this.listOps.leftPop(key);
        } catch (Exception e) {
            logger.error("leftPopList error.ket={}", key , e);
            throw e;
        }
    }

    @Override
    public String leftPopStringList(String key) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T leftPopList(String key, long timeoutSeconds) {
        try {
            return (T) this.listOps.leftPop(key, timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("leftPopList error.key={}", key, e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T rightPopList(String key) {
        try {
            return (T) this.listOps.rightPop(key);
        } catch (Exception e) {
            logger.error("rightPopList error.key={}", key, e);
        }
        return null;
    }

    @Override
    public String rightPopStringList(String key) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> T rightPopList(String key, long timeoutSeconds) {
        try {
            return (T) this.listOps.rightPop(key, timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("rightPopList error.key={}", key, e);
        }
        return null;
    }

    @Override
    public Long sizeList(String key) {
        try {
            return this.listOps.size(key);
        } catch (Exception e) {
            logger.error("sizeList error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public Long addSetValues(String key, Serializable... values) {
        try {
            return this.setOps.add(key, values);
        } catch (Exception e) {
            logger.error("addSetValues error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public <T extends Serializable> Boolean isSetContains(String key, T o) {
        try {
            return this.setOps.isMember(key, o);
        } catch (Exception e) {
            logger.error("isSetContains error.key={}", key, e);
        }
        return false;
    }

    @Override
    public <T extends Serializable> Set<T> getSetAll(String key) {
        try {
            return (Set<T>) this.setOps.members(key);
        } catch (Exception e) {
            logger.error("getSetAll error.key={}", key, e);
        }
        return null;
    }

    @Override
    public <T extends Serializable> Boolean moveSetValue(String key, T value, String destKey) {
        try {
            return this.setOps.move(key, value, destKey);
        } catch (Exception e) {
            logger.error("moveSetValue error.key={}", key, e);
        }
        return false;
    }

    @Override
    public <T extends Serializable> T randomSetValue(String key) {
        try {
            return (T) this.setOps.randomMember(key);
        } catch (Exception e) {
            logger.error("sizeSet error.key={}", key, e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> Set<T> distinctRandomSetValues(String key, long count) {
        try {
            return (Set<T>) this.setOps.distinctRandomMembers(key, count);
        } catch (Exception e) {
            logger.error("distinctRandomSetValues error.key={}", key, e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> randomSetValues(String key, long count) {
        try {
            return (List<T>) this.setOps.randomMembers(key, count);
        } catch (Exception e) {
            logger.error("randomSetValues error.key={}", key, e);
        }
        return null;
    }

    @Override
    public Long removeSetValues(String key, Object... values) {
        try {
            return this.setOps.remove(key, values);
        } catch (Exception e) {
            logger.error("removeSetValues error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public <T extends Serializable> T popSetValue(String key) {
        try {
            return (T) this.setOps.pop(key);
        } catch (Exception e) {
            logger.error("popSetValue error.key={}", key, e);
        }
        return null;
    }

    @Override
    public Long sizeSet(String key) {
        try {
            return this.setOps.size(key);
        } catch (Exception e) {
            logger.error("sizeSet error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public <T extends Serializable> Boolean addZSet(String key, T value, double score) {
        try {
            return this.zSetOps.add(key, value, score);
        } catch (Exception e) {
            logger.error("addZSet error.key={}", key, e);
        }
        return false;
    }

    @Override
    public <T extends Serializable> Double incrementZSetScore(String key, T value, double delta) {
        try {
            return this.zSetOps.incrementScore(key, value, delta);
        } catch (Exception e) {
            logger.error("incrementZSetScore error.key={}", key, e);
        }
        return 0D;
    }

    @Override
    public <T extends Serializable> Long rankZSet(String key, T o) {
        try {
            return this.zSetOps.rank(key, o);
        } catch (Exception e) {
            logger.error("rankZSet error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public <T extends Serializable> Long reverseZSetRank(String key, T o) {
        try {
            return this.zSetOps.reverseRank(key, o);
        } catch (Exception e) {
            logger.error("reverseZSetRank error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public <T extends Serializable> Double scoreZSet(String key, T o) {
        try {
            return this.zSetOps.score(key, o);
        } catch (Exception e) {
            logger.error("scoreZSet error.key={}", key, e);
        }
        return 0D;
    }

    @Override
    public Long removeZSet(String key, Object... values) {
        try {
            return this.zSetOps.remove(key, values);
        } catch (Exception e) {
            logger.error("removeZSet error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public Long removeZSetRange(String key, long start, long end) {
        try {
            return this.zSetOps.removeRange(key, start, end);
        } catch (Exception e) {
            logger.error("removeZSetRange error.key={}",key, e);
        }
        return 0L;
    }

    @Override
    public Long removeZSetRangeByScore(String key, double min, double max) {
        try {
            return this.zSetOps.removeRangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error("removeZSetRangeByScore error.key={}",key, e);
        }
        return 0L;
    }

    @Override
    public Long countZSet(String key, double min, double max) {
        try {
            return this.zSetOps.count(key, min, max);
        } catch (Exception e) {
            logger.error("countZSet error.key={}", key, e);
        }
        return 0L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(redisTemplate, "redisTemplate不能为空");
    }
}
