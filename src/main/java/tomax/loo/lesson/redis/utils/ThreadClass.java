package tomax.loo.lesson.redis.utils;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-24 08:34
 **/
public class ThreadClass extends Thread {
    private TargetObject targetObject;

    public ThreadClass(TargetObject targetObject) {
        this.targetObject = targetObject;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if(name.equals("thread-1")){
            targetObject.addNumber();
        }else{
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            targetObject.addNum();;
        }
    }
}
