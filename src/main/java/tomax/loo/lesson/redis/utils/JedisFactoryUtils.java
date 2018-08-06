package tomax.loo.lesson.redis.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class JedisFactoryUtils {

    protected static Logger logger = LoggerFactory.getLogger(JedisFactoryUtils.class);

    private final static int DEFAULT_TIMEOUT = 3000;

    public static Jedis getJedis(final String host, final int port) {
        return getJedis(host, port, null);
    }

    private static Jedis getJedis(final String host, final int port, final String auth) {
        return getJedis(host, port, auth, DEFAULT_TIMEOUT);
    }

    private static Jedis getJedis(final String host, final int port, final int timeout) {
        return getJedis(host, port, null, timeout);
    }

    private static Jedis getJedis(final String host, final int port, final String auth, final int timeout) {
        Jedis jedis = new Jedis(host, port, timeout);
        if (StringUtils.isNotBlank(auth)) {
            jedis.auth(auth);
        }
        return jedis;
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.error("关闭Jedis连接失败！", e);
            }
        }
    }

}
