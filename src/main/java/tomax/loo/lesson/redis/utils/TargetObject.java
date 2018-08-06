package tomax.loo.lesson.redis.utils;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-24 08:28
 **/
public class TargetObject {

    /*private int num = 0;

    private Object lock  = new Object();

    public void addNumber() {
        synchronized (lock) { //
            for (int i = 0; i < 10000; i++) {
                num++;
                if (num < 3) {
                    addNumber();// 递归调用，已经是monitor的持有者，直接进入
                }
            }
        }
    }

    public int getNum(){
        return num;
    }*/

    private int num = 0;

    private  Object lock1 = new Object();
    private  Object lock2 = new Object();

    public synchronized void addNumber(){
        // synchronized(lock1){ //synchronized获取的是lock1对象的Monitor
            System.out.println(Thread.currentThread().getName()+" 休眠....");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        // }
    }

    public synchronized void addNum(){
        // synchronized(lock2){ //synchronized获取的是lock2对象的Monitor
            for(int i=0; i<5; i++){

                num++;
                System.out.println(Thread.currentThread().getName()+"---"+num);
            }
        // }
    }
}
