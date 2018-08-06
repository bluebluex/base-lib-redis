package tomax.loo.lesson.redis.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-25 08:49
 **/
public class ABADemo {

    static AtomicInteger atInt = new AtomicInteger(100);

    static AtomicStampedReference<Integer> atomicStampedR = new AtomicStampedReference<>(100, 0);
    static Lock lock = new ReentrantLock();

    static Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            // 更新为200
            System.out.println("修改前的值=======Value：" + atInt + " ---Thread name : " + Thread.currentThread().getName());
            System.out.println("第一次修改======= 100 -> 200"+ " ---Thread name : " + Thread.currentThread().getName());
            atInt.compareAndSet(100, 200);
            // try {
            //     TimeUnit.SECONDS.sleep(1);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            // 更新为100
            lock.lock();
            lockMethod();
            lock.unlock();
            System.out.println("第二次修改======= 200 -> 100" + " ---Thread name : " + Thread.currentThread().getName());
            atInt.compareAndSet(200, 100);
            System.out.println("修改前的后=======Value：" + atInt + " ---Thread name : " + Thread.currentThread().getName());

        }
    }, "线程A");

    static Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("修改前的值=======Value：" + atInt + " ---Thread name : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.lock();
            lockMethod();
            lock.unlock();
            System.out.println("修改前的值======= Value: " + atInt + " ---Thread name : " + Thread.currentThread().getName());
            boolean flag = atInt.compareAndSet(100, 500);
            if (flag) {
                System.out.println("flag：" + flag + ", newValue: " + atInt + " 成功了  有ABA问题");
            }
        }
    }, "线程B");


    static Thread t3 = new Thread(new Runnable() {
        @Override
        public void run() {
            int time = atomicStampedR.getStamp();
            System.out.println("timeStamp： " + time);
            System.out.println("第一次修改前=======Value：" + atomicStampedR.getReference() + " ---Thread name : " + Thread.currentThread().getName());
            System.out.println("第一次修改======= 100 -> 200"+ " ---Thread name : " + Thread.currentThread().getName());
            // 跟新为200
            atomicStampedR.compareAndSet(100, 200, time, time + 1);
            // 更新为100
            int time2 = atomicStampedR.getStamp();
            System.out.println("timeStamp： " + time2);
            System.out.println("第二次修改前=======Value：" + atomicStampedR.getReference() + " ---Thread name : " + Thread.currentThread().getName());
            System.out.println("第二次修改======= 200 -> 100" + " ---Thread name : " + Thread.currentThread().getName());
            atomicStampedR.compareAndSet(200, 100, time2, time2 + 1);
            System.out.println("修改前的后=======Value：" + atomicStampedR.getReference() + " ---Thread name : " + Thread.currentThread().getName());
        }
    }, "线程C");


    static Thread t4 = new Thread(new Runnable() {
        @Override
        public void run() {
            int time = atomicStampedR.getStamp();
            System.out.println("sleep 前 t4 time: " + time);
            try {
                System.out.println("修改前的值=======Value：" + atomicStampedR.getReference() + " ---Thread name : " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("修改前的值=======Value：" + atomicStampedR.getReference() + " ---Thread name : " + Thread.currentThread().getName());
            boolean flag = atomicStampedR.compareAndSet(100, 500, time, time + 1);
            if (!flag) {
                System.out.println("flag: " + flag + ", newValue: " + atomicStampedR.getReference() + " ====失败了，没有ABA问题");
            }

        }
    }, "线程D");

    public static void lockMethod() {
        System.out.println("================lock: " + Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        t1.start();

        t2.start();
        t1.join();
        t2.join();

        System.out.println("===================================================");

        // t3.start();
        // t4.start();
    }
}
