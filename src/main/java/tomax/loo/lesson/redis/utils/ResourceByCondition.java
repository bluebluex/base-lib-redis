package tomax.loo.lesson.redis.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-28 14:23
 **/
public class ResourceByCondition {
    private String name;
    private int count = 1;
    private boolean flag = true;

    Lock lock = new ReentrantLock();

    Condition producer_con = lock.newCondition();
    Condition consumer_con = lock.newCondition();

    /**
     * 生产者
     * @param name
     */
    public void product(String name) {
        lock.lock();
        try {
            while (flag) {
                flag = false;
                this.name = name + count;
                count++;
                System.out.println(Thread.currentThread().getName() + " :===生产者===" + this.name);
                try {
                    consumer_con.signal();// 直接唤醒消费线程
                    producer_con.await();
                    // TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }

    }

    /**
     * 消费者
     */
    public void consume() {
        lock.lock();
        try {
            while (!flag) {
                flag = true;
                count--;
                System.out.println(Thread.currentThread().getName() + " :===消费者===" + this.name);
                try {
                    producer_con.signal(); // 直接唤醒生产线程
                    consumer_con.await();
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }

}
