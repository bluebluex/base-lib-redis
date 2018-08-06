package tomax.loo.lesson.redis.utils;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-23 17:18
 **/
public class Demo {

    private int num = 0;

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public synchronized void addNumber() {
        // synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + " 休眠ing...");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(num);
        // }
    }

    public synchronized void addNum() {
        // synchronized (lock1) {
            for (int i = 0; i < 10; i++) {
                num++;
                // Thread.yield();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ---" + num);
            }
        // }
    }

    /*static class ThreadClass extends Thread{
        private Demo demo;

        public ThreadClass(Demo demo){
            this.demo = demo;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            if (name.equals("thread-1")) {
                demo.addNumber();
            } else {
                demo.addNum();
            }
        }
    }*/

    public static void main(String[] args) {
        // Demo demo = new Demo();
        // ThreadClass t1 = new ThreadClass(demo);
        // t1.setName("thread-1");
        // t1.start();
        Demo demo1 = new Demo();
        new Thread(() -> {
            demo1.addNumber();
        },"thread-1").start();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Demo demo1 = new Demo();
                demo1.addNum();
            }
        });*/
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            demo1.addNum();
        },"thread-2").start();
    }
}
