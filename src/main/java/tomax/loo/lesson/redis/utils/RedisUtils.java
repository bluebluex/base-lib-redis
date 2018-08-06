package tomax.loo.lesson.redis.utils;

public class RedisUtils {

    public static byte[] converLongTOBytes(Long l) {
        byte[] b = new byte[8];
        b = Long.toString(l).getBytes();
        return b;
    }

    public Long converBytesToLong(byte[] b) {
        long l = 0L;
        l = Long.parseLong(new String(b));
        return l;
    }
}
