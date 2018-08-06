package tomax.loo.lesson.redis.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-24 20:19
 **/
public class AtomicIntegerDemo {
    // 创建AtomicInteger，用于自增操作
    static AtomicInteger i = new AtomicInteger();

    public static class AddThread implements Runnable{

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread[] ts = new Thread[10];

        for (int j = 0; j < 10; j++) {
            ts[j] = new Thread(new AddThread());
            ts[j].start(); // 2
        }
        // for (int j = 0; j < 10; j++) { // 1
        //     ts[j].start();
        // }
        for (int j = 0; j < 10; j++) {
            ts[j].join();
        }
        System.out.println(i);
    }

}
