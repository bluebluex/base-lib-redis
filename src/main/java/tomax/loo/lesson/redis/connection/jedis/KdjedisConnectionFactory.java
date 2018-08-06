package tomax.loo.lesson.redis.connection.jedis;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import javax.annotation.Resource;

/**
 * @program: base-core
 * @description: ${自定义Jedis工厂}
 * @author: Tomax
 * @create: 2018-05-22 18:05
 **/
public class KdjedisConnectionFactory extends JedisConnectionFactory {

    @Resource(name="redisSettingBean")
    private RedisSettingBean redisSettingBean;

    @Override
    public void afterPropertiesSet() {
        super.setHostName(redisSettingBean.getSettingBean().getHost());
        super.setPort(Integer.parseInt(redisSettingBean.getSettingBean().getPort()));
        super.afterPropertiesSet();
    }
}
