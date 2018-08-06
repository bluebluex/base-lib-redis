package tomax.loo.lesson.redis.utils;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-05-31 21:17
 **/
public class WsReqInfo {

    private String key;

    private String content;

    public String getSign_string() {
        return sign_string;
    }

    public void setSign_string(String sign_string) {
        this.sign_string = sign_string;
    }

    private String sign_string;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
