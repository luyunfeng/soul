package tech.soulcoder.learn.juc;

import com.google.common.collect.Maps;
import tech.soulcoder.uniqueId.UUIDCreator;

import java.util.Map;
import java.util.concurrent.*;

/**
 * https://www.cnblogs.com/dolphin0520/p/3920397.html
 *
 * @author yunfeng.lu
 * @create 2019/3/10.
 */
public class CountDownLatchLearn {

    public static void main(String[] args) throws InterruptedException {
        int n = 4;
        final CountDownLatch latch = new CountDownLatch(n);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Map<String, String> result = Maps.newHashMap();
        for (int i = 0; i < n; i++) {
            executorService.execute(() -> {
                System.out.println("子线程" + Thread.currentThread().getName() + "正在执行");
                try {
                    Thread.sleep(2993);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result.put(UUIDCreator.generate(), String.valueOf(n));
                System.out.println("子线程" + Thread.currentThread().getName() + "执行完毕");
                latch.countDown();
            });
        }
        System.out.println("等待 " + n + "个子线程执行完毕...");
        latch.await(3, TimeUnit.SECONDS);
        System.out.println(result);
        System.out.println(n + "个子线程已经执行完毕");
        System.out.println("继续执行主线程");
        executorService.shutdown();
    }


}
