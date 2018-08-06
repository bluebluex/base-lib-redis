package tomax.loo.lesson.redis.connection.jedis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-22 16:29
 **/
public class RedisShareSettingBean implements InitializingBean {

    private String shareSetting;
    private String serializerType;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(shareSetting, "shareSetting不能为空");
    }

    public String getAuthPassword() {
        return null;
    }

    public String getShareSetting() {
        return shareSetting;
    }

    public void setShareSetting(String shareSetting) {
        this.shareSetting = shareSetting;
    }

    public String getSerializerType() {
        return serializerType;
    }

    public void setSerializerType(String serializerType) {
        this.serializerType = serializerType;
    }

}
