package tomax.loo.lesson.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseRedisServiceImpl implements InitializingBean {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 序列化filed
     */
    protected byte[] serializeFiled(final String filed) {
        return getKeySerializer().serialize(filed);
    }

    /**
     * 反序列化filed
     */
    protected String deserializeFiled(final byte[] filed) {
        return getKeySerializer().deserialize(filed);
    }

    /**
     * 系列化value
     */
    protected <T extends Serializable> byte[] serializeValue(final T value) {
        return getValueSerializer().serialize(value);
    }

    /**
     * 序列化value
     */
    protected byte[][] serializeValues(final Serializable... values) {
        byte[][] bv = new byte[values.length][];
        for (int i = 0; i < values.length; i++) {
            bv[i] = this.serializeValue(values[i]);
        }
        return bv;
    }

    /**
     * 反序列化values
     */
    protected <T extends Serializable> T deserializeValue(final byte[] value) {
        return (T) getValueSerializer().deserialize(value);
    }

    /**
     * 反序列化values
     */
    protected <T extends Serializable> Set<T> deserializeValues(final Set<byte[]> values) {
        Set<T> lvalues = new HashSet<>(values.size());
        for (byte[] value : values) {
            T v = this.deserializeValue(value);
            lvalues.add(v);
        }
        return lvalues;
    }

    /**
     * 反序列化values
     */
    protected <T extends Serializable> List<T> deserializeValus(final List<byte[]> values) {
        List<T> lvalues = new ArrayList<>(values.size());
        for (byte[] value : values) {
            T v = this.deserializeValue(value);
            lvalues.add(v);
        }
        return lvalues;
    }

    protected static byte[] converLongTOBytes(Long l) {
        byte[] b = new byte[8];
        b = Long.toString(l).getBytes();
        return b;
    }

    protected Long converBytesToLong(byte[] b) {
        long l = 0L;
        l = Long.parseLong(new String(b));
        return l;
    }

    /**
     * 获取key序列化工具
     */
    public abstract RedisSerializer<String> getKeySerializer();

    /**
     * 获取value序列化工具
     */
    public abstract RedisSerializer<Object> getValueSerializer();

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
