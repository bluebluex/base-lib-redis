package tomax.loo.lesson.redis.connection.jedis;

/**
 * @program: base-core
 * @description: ${Redis配置参数}
 * @author: Tomax
 * @create: 2018-05-22 16:28
 **/
public class RedisSettingProperties {

    private String host;
    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
