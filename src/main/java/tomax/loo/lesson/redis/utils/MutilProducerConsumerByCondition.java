package tomax.loo.lesson.redis.utils;

import java.util.concurrent.TimeUnit;

/**
 * @program: base-core
 * @description: ${description}
 * @author: Tomax
 * @create: 2018-05-28 14:58
 **/
public class MutilProducerConsumerByCondition {

    static class MutilProducer implements Runnable{

        private  ResourceByCondition r;

        MutilProducer(ResourceByCondition r) {
            this.r = r;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r.product("北京烤鸭");
            }
        }
    }

    static class MutilConsumer implements Runnable{
        private  ResourceByCondition r;

        MutilConsumer(ResourceByCondition r) {
            this.r = r;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                r.consume();
            }
        }
    }

    public static void main(String[] args) {
        ResourceByCondition r = new ResourceByCondition();
        MutilProducer pro = new MutilProducer(r);
        MutilConsumer cons = new MutilConsumer(r);
        // 生产者
        Thread product1 = new Thread(pro, "生产者1");
        Thread product2 = new Thread(pro, "生产者2");
        // 消费者
        Thread consumer1 = new Thread(cons, "消费者1");
        Thread consumer2 = new Thread(cons, "消费者2");

        product1.start();
        product2.start();
        consumer1.start();
        consumer2.start();

    }
}
