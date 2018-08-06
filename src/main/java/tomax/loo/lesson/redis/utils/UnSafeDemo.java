package tomax.loo.lesson.redis.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-24 19:12
 **/
public class UnSafeDemo {

    public static void main(String[] args) throws Exception {
        // 通过反射得到theUnsafe对应的Field对象
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        // 设置该Field为可访问
        field.setAccessible(true);
        // 通过Field得到该Field对应的具体对象，传入null是因为该Field为static
        Unsafe unsafe = (Unsafe) field.get(null);
        System.out.println(unsafe);

        // 通过allocateInstance直接创建对象
        User user = (User) unsafe.allocateInstance(User.class);

        Class clazz = user.getClass();
        Field name = clazz.getDeclaredField("name");
        Field age = clazz.getDeclaredField("age");
        Field id = clazz.getDeclaredField("id");

        // 获取实例变量name和age在对象内存中的偏移量并设置值
        unsafe.putInt(user, unsafe.objectFieldOffset(age), 18);
        unsafe.putObject(user, unsafe.objectFieldOffset(name), "Tomax");

        Object staticBase = unsafe.staticFieldBase(id);
        System.out.println("staticBase: " + staticBase);

        // 获取静态变量id的偏移量staticOffset
        long staticOffset = unsafe.staticFieldOffset(clazz.getDeclaredField("id"));
        System.out.println("staticOffset: " + staticOffset);
        // 获取静态变量的值
        System.out.println("设置前的ID：" + unsafe.getObject(staticBase, staticOffset));
        // 设置值
        unsafe.putObject(staticBase,staticOffset,"ssssssssssss");
        // 获取静态变量的值
        System.out.println("设置后的ID：" + unsafe.getObject(staticBase, staticOffset));
        System.out.println("staticOffset: " + staticOffset);
        System.out.println("输出USER: " + user.toString());

        long data = 1000;
        byte size = 1; // 单位字节

        // 调用allocateMemory分配内存，并获取内存地址memoryAddress
        long memoryAddress = unsafe.allocateMemory(size);
        System.out.println("memoryAddress: " + memoryAddress);
        // 直接往内存写入数据
        unsafe.putAddress(memoryAddress, data);
        // 获取指定内存地址的数据
        long addrData = unsafe.getAddress(memoryAddress);
        System.out.println("addrData: " + addrData);

    }

}
