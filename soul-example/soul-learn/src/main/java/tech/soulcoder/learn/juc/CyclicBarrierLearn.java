package tech.soulcoder.learn.juc;


import java.util.concurrent.CyclicBarrier;

/**
 * https://www.cnblogs.com/dolphin0520/p/3920397.html
 * @author yunfeng.lu
 * @create 2019/3/10.
 */
public class CyclicBarrierLearn {
    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier = new CyclicBarrier(N,
                // 当所有线程执行完 执行这个
                () -> System.out.println("当前线程"+Thread.currentThread().getName()));
        for (int i = 0; i < N; i++) {
            new Writer(barrier).start();
        }
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
