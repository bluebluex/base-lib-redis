package tomax.loo.lesson.redis.connection.jedis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-22 16:33
 **/
public class ShareJedisPoolFactory implements InitializingBean, DisposableBean {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPoolConfig jedisPoolConfig;
    private RedisShareSettingBean redisShareSettingBean;
    private String shareRedisUrl = null;
    private ShardedJedisPool shardedJedisPool;

    private String prevPwd;
    // 默认超时时间：15s
    private int timeout = 15000;

    /**
     * 获取 shardedJedisPool
     */
    public ShardedJedisPool getShardedJedisPool() {

        try {
            String rmtUrl = redisShareSettingBean.getShareSetting();
            String pwd = redisShareSettingBean.getAuthPassword();
            // 当url和pwd都没更改的时候，返回已经初始化好的pool
            if (!isNeedInit(rmtUrl, pwd)) {
                return this.shardedJedisPool;
            }
            // 否则初始化新的pool
            return this.initPool(rmtUrl, pwd);
        } catch (Exception e) {
            logger.error("获取ShardedJedisPoll异常", e);
        }

        return null;
    }

    /**
     * 初始化 shardedJedisPool
     */
    private ShardedJedisPool initPool(String rmtUrl, String pwd) {
        if (!isNeedInit(rmtUrl, pwd)) {
            return this.shardedJedisPool;
        }

        if (this.shardedJedisPool != null) {
            try {
                ShardedJedisPool tmpPool = this.shardedJedisPool;
                this.shardedJedisPool = null;
                tmpPool.destroy();
                tmpPool = null;
            } catch (Exception e) {
                logger.error("判断URL是否发生变化，销毁shardedJedisPool", e);
            }
        }

        try {
            // 构造shardedJedisPool
            this.shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, this.initJedisShardInfo(rmtUrl, pwd));
            this.shareRedisUrl = rmtUrl;
            this.prevPwd = pwd;
            return this.shardedJedisPool;
        } catch (Exception e) {
            logger.error("初始化ShardedJedisPool rmtUrl={}", rmtUrl, e);
            return null;
        }
    }

    private List<JedisShardInfo> initJedisShardInfo(String shareRedisUrl, String pwd) {

        logger.info("初始化redis配置信息shareRedisUrl={}", shareRedisUrl);
        String[] urls = StringUtils.split(shareRedisUrl, ",");
        List<JedisShardInfo> urlList = new ArrayList<>();
        for (String url : urls) {
            String[] cnf = StringUtils.split(url, ":");
            if (cnf.length != 2) {
                logger.error("redis 配置信息不合规 url={}", url);
                continue;
            }
            String host = StringUtils.trim(cnf[0]);
            String port = StringUtils.trim(cnf[1]);
            if (StringUtils.isBlank(host) || !NumberUtils.isDigits(port)) {
                logger.error("redis配置信息不合规url={}", url);
                continue;
            }
            urlList.add(initJedisShardInfo(host, Integer.parseInt(port), pwd));
        }

        return urlList;

    }

    private JedisShardInfo initJedisShardInfo(String ip, int port, String password) {

        JedisShardInfo info = new JedisShardInfo(ip, port, timeout, ip + ":" + port);

        if (StringUtils.isEmpty(password)) {
            return info;
        }
        info.setPassword(password);
        return info;
    }

    private boolean isNeedInit(String rmtUrl, String password) {
        return isUrlChanged(rmtUrl) || isPwdChanged(password);
    }

    /**
     * 判断url是否更改过
     */
    private boolean isUrlChanged(String rmtUrl) {
        if (StringUtils.isBlank(rmtUrl)) {
            return false;
        }
        if (this.shardedJedisPool == null) {
            return true;
        }
        if (StringUtils.equals(shareRedisUrl, rmtUrl)) {
            return false;
        } else {
            logger.info("share redis URL发生变化shareRedisUtl={},rmtUrl={}", shareRedisUrl, rmtUrl);
            return true;
        }
    }

    private boolean isPwdChanged(String password) {

        if (StringUtils.equals(prevPwd, password)) {
            return false;
        }
        logger.info("share redis{} auth 密码发生变化", shareRedisUrl);
        return true;
    }

    @Override
    public void destroy() throws Exception {
        if (shardedJedisPool != null) {
            shardedJedisPool.destroy();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.jedisPoolConfig, "jedisPoolConfi 参数不能为空");
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setRedisShareSettingBean(RedisShareSettingBean redisShareSettingBean) {
        this.redisShareSettingBean = redisShareSettingBean;
    }

    public RedisShareSettingBean getRedisShareSettingBean() {
        return redisShareSettingBean;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public static void main(String[] args) {
        // 判断字符串是否非空， 不能排除空格字符
        boolean f1 = StringUtils.isNotEmpty(null); // false
        boolean f2 = StringUtils.isNotEmpty(""); // false
        boolean f3 = StringUtils.isNotEmpty(" "); // true
        boolean f4 = StringUtils.isNotEmpty("pop"); // true
        boolean f5 = StringUtils.isNotEmpty(" pop "); // true

        System.out.println(f1);
        System.out.println(f2);
        System.out.println(f3);
        System.out.println(f4);
        System.out.println(f5);

        System.out.println("==================================");
        // 判断字符串是否不为空且长度不为0且不由空白符(whitespace) 构成
        // if (!Character.isWhitespace(str.charAt(i))) {
        boolean b1 = StringUtils.isNotBlank(null); // false
        boolean b2 = StringUtils.isNotBlank(""); // false
        boolean b3 = StringUtils.isNotBlank(" "); // false
        boolean b4 = StringUtils.isNotBlank("/t /n /f /r"); // true
        boolean b5 = StringUtils.isNotBlank("/b"); // true
        boolean b6 = StringUtils.isNotBlank(" pop "); // true
        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(b4);
        System.out.println(b5);
        System.out.println(b6);
    }
}
