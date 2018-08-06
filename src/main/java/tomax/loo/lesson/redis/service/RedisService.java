package tomax.loo.lesson.redis.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
    /**
     * 设置单个值
     */
    <T extends Serializable> void setValue(String key, T value);

    /**
     * 设置单个String值
     */
    void setString(String key, String vlue);

    /**
     * 设置带过期时间的单个值
     */
    <T extends Serializable> void setValue(String key, long timeoutSeconds, T value);

    /**
     * 设置带过期时间的单个值
     */
    void setValue(String key, long timeoutSeconds, String value);

    /**
     * 设置带过期时间的单个String值
     */
    void setString(String key, long timeoutSeconds, String value);

    /**
     * 设置缓存如果不存在，存在则返回false，不修改
     */
    <T extends Serializable> boolean setValueNEX(String key, T value);

    /**
     * 设置缓存如果不存在，存在则返回false，不修改
     */
    boolean setValueNEX(String key, Long value);

    /**
     * 设置缓存如果不存在，存在则返回false，不修改
     */
    <T extends Serializable> boolean setValueNEX(String key, long timeoutSeconds, T value);

    /**
     * 设置缓存如果不存在，存在则返回false，不修改
     */
    boolean setValueNEX(String key, long timeoutSeconds, Long value);

    /**
     * 获取单个String值
     */
    public <T extends Serializable> T getValue(String key);

    /**
     * 获取单个String值
     */
    String getString(String key);

    /**
     * 获取单个Long值
     */
    Long getLong(String key);

    /**
     * 判断key是否存在
     */
    Boolean isExists(String key);

    /**
     * 删除键对应的值
     */
    boolean remove(String key);

    /**
     * 获取元素类型
     * @return none, String, list, set, zset, hash
     */
    String getType(String key);

    /**
     * 在某段时间后失效
     */
    boolean setExpire(String key, long seconds);

    /**
     * 在某个时间点失效
     */
    boolean setExpireAt(String key, Date date);

    /**
     * 获取key存活时间，单位秒
     */
    Long getTimeToLive(String key);

    /**
     * Sets the bit at offset in value stored at key
     */
    Boolean setBit(String key, long offset, boolean value);

    /**
     * Get the bit value at offset of value at key
     */
    Boolean getBit(String key, long offset);

    /**
     * Decrement the integer value of a key by the given number.
     */
    Long decrement(String key, long number);

    /**
     * 判断递减是否成功
     */
    boolean isDecrement(String key);

    /**
     * Decrement the integer value of a key by the given number.
     */
    boolean isDecrement(String key, long number);

    /**
     * Decrement the integer value of a key by one
     */
    Long decrement(String key);

    /**
     * Increment the integer value of a key by given amount.
     */
    Long increment(String key, long number);

    /**
     * Increment the integer value of a key by one
     */
    Long increment(String key);

    /**
     * Append a String value to a key
     */
    Integer append(String key, String value);

    /**
     * Get a substring of the string stored at a key.
     */
    String subStr(String key, int start, int end);

    /**
     * Set the string value of a hash field.
     */
    <T extends Serializable> Boolean hashSet(String key, String field, T value);

    /**
     * 判断hash field 是否存在
     */
    Boolean hashExists(String key, String field);

    /**
     * Get the value of a hash field.
     */
    <T extends Serializable> T hashGet(String key, String filed);

    /**
     * Get all values of hash key
     */
    <T extends Serializable> Map<String, T> hashGetAll(String key);

    /**
     * Set the value of a hash field, only if the field does no exist
     */
    <T extends Serializable> Boolean hashSetNx(String key, String field, T value);

    /**
     * Delete one or more hash fields
     */
    Long hashDelete(String key, String field);

    /**
     * Append one value to a list.
     */
    <T extends Serializable> Long rightPushList(String key, T value);

    /**
     * Append one or multiple string values to a list.
     */
    Long rightPushListAll(String key, Serializable... values);

    /**
     * Append one or multiple string values to a list
     */
    Long rightPushStringListAll(String key, String... value);

    /**
     * Append values to key only if the list exists
     */
    <T extends Serializable> Long rightPushListIfPresent(String key, T value);

    /**
     * Prepend ont value to a list
     */
    <T extends Serializable> Long leftPushList(String kye, T value);

    /**
     * Prepend ont string value to a list
     */
    Long leftPushStringList(String key, String value);

    /**
     * Prepend one or multiple values to a list
     */
    Long leftPushListAll(String key, Serializable... values);

    /**
     * Prepend one or multiple string values to a list
     */
    Long leftPushListAll(String key, String... values);

    /**
     * Prepend values to key only if the list exits.
     */
    <T extends Serializable> Long leftPushListIfPresent(String key, T value);

    /**
     * Get a rang of elements from a list.
     */
    <T extends Serializable> List<T> rangList(String key, long start, long end);

    /**
     * Trim a list to the specified rang
     */
    void trimList(String key, long start, long end);

    /**
     * Removes the first count occurences of value from list stored at key.
     */
    <T extends Serializable> Long removeList(String key, long i, T value);

    /**
     * Get element at index form list at key
     */
    <T extends Serializable> T indexList(String key, long index);

    /**
     * Removes and returns first element in list stored at key
     */
    <T extends Serializable> T leftPopList(String key);

    /**
     * Removes and returns first element in list stored at key. And it may throw exception.
     */
    <T extends Serializable> T leftPopListThroeingException(String key) throws Throwable;

    /**
     * Removes and returns first element in list stored at key.
     */
    String leftPopStringList(String key);

    /**
     * Removes and returns first element from lists stored at keys(see@lPop(byte[])).Blocks connection
     * until element available or timeout reached.
     */
    <T extends Serializable> T leftPopList(String key, long timeoutSeconds);

    /**
     * Removes and returns last string element in list stored at key
     */
    <T extends Serializable> T rightPopList(String key);

    /**
     * Removes and returns last string element in list stored at key.
     */
    String rightPopStringList(String key);

    /**
     * Removes and returns last element from lists stored at keys.Blocks connection until
     * element available or timeout reached.
     */
    <T extends Serializable> T rightPopList(String key, long timeoutSeconds);

    /**
     * Get the length of a list
     */
    Long sizeList(String key);

    /**
     * Add given values to set at key
     */
    Long addSetValues(String key, Serializable... values);

    /**
     * check if set at key contains value.
     */
    <T extends Serializable> Boolean isSetContains(String key, T o);

    /**
     * Get all elements of set at key
     */
    <T extends Serializable> Set<T> getSetAll(String key);

    /**
     * Move value from srcKey to (@destKey).
     */
    <T extends Serializable> Boolean moveSetValue(String key, T value, String destKey);

    /**
     * Get random element from set at key
     */
    <T extends Serializable> T randomSetValue(String key);

    /**
     * Get count random elements from set at key
     */
    <T extends Serializable> Set<T> distinctRandomSetValues(String key, long count);

    /**
     * Get count random element from set at key.
     */
    <T extends Serializable> List<T> randomSetValues(String key, long count);

    /**
     * Remove given values from set at key and return the number of removed elements
     */
    Long removeSetValues(String key, Object... values);

    /**
     * Remove and return a random member from set at key.
     */
    <T extends Serializable> T popSetValue(String key);

    /**
     * Get size of set at key
     */
    Long sizeSet(String key);

    /**
     * Add value to a sorted set at key, or update its score if it already exists.
     */
    <T extends Serializable> Boolean addZSet(String key, T value, double score);

    /**
     * Increment the score of element with value in sorted set by increment.
     */
    <T extends Serializable> Double incrementZSetScore(String key, T value, double delta);

    /**
     * Determine the index of element with value in a sorted set
     */
    <T extends Serializable> Long rankZSet(String key, T o);

    /**
     * Determine the index of element with value in a sorted set when scored high to low.
     */
    <T extends Serializable> Long reverseZSetRank(String key, T o);

    /**
     * Get the score of element with value from sorted set with key.
     */
    <T extends Serializable> Double scoreZSet(String key, T o);

    /**
     * Remove value from sorted set. Return number of removed elements.
     */
    Long removeZSet(String key, Object... values);

    /**
     * Remove elements in range between begin and end from sorted set with key.
     */
    Long removeZSetRange(String key, long start, long end);

    /**
     * Remove elements with scores between min and max from sorted set with key
     */
    Long removeZSetRangeByScore(String key, double min, double max);

    /**
     * Count number of elements within sorted set with scores between min and max.
     */
    Long countZSet(String key, double min, double max);

}
