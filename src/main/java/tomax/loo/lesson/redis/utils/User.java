package tomax.loo.lesson.redis.utils;

import java.util.List;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-24 19:20
 **/

public class User {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        User.id = id;
    }

    public User() {
        System.out.println("user 构造方法被调用");
    }
    private String name;
    private int age;
    private static String id = "USER_ID";
    private List<User> lst;

    public List<User> getLst() {
        return lst;
    }

    public void setLst(List<User> lst) {
        this.lst = lst;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
