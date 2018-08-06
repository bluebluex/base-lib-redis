package tomax.loo.lesson.redis.connection.jedis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class RedisSettingBean implements InitializingBean {

    private RedisSettingProperties settingBean;

    public RedisSettingProperties getSettingBean() {
        return settingBean;
    }

    public void setSettingBean(RedisSettingProperties settingBean) {
        this.settingBean = settingBean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(settingBean, "settingBean不能为空");
    }
}
