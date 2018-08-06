package tomax.loo.lesson.redis.utils;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-24 08:35
 **/
public class Main {
    public static void main(String[] args) {
        //创建两个目标对象
        TargetObject targetObject = new TargetObject();
        ThreadClass t1 = new ThreadClass(targetObject);
        ThreadClass t2 = new ThreadClass(targetObject);
        t1.setName("thread-1");
        t2.setName("thread-2");

        t1.start();
        t2.start();
    }
}
