package tomax.loo.lesson.redis.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import tomax.loo.lesson.redis.connection.jedis.RedisSettingProperties;


/**
 * redis序列化工厂
 */
public class RedisSerializerFactory {

    protected static Logger logger = LoggerFactory.getLogger(RedisSettingProperties.class);

    private static final RedisSerializer<Object> JDK_SERIALIZATION = new JdkSerializationRedisSerializer();

    private static final RedisSerializer<Object> HESSIAN2_SERIALIZATION = new Hessian2SerializationRedisSerializer();

    public static RedisSerializer<Object> getRedisSerializer(String type) {

        if (type == null) {
            logger.error("未找到redis序列化配置，请技术在配置中心appsetting配置redis_serializer_type，默认使用JDK序列化工具");
            return JDK_SERIALIZATION;
        }

        if ("hessian2".equals(type)) {
            return HESSIAN2_SERIALIZATION;
        } else if ("jdk".equals(type)) {
            return JDK_SERIALIZATION;
        } else {
            logger.error("未找到redis序列化工具对应配置，type={}", type);
            return JDK_SERIALIZATION;
        }
    }

}
