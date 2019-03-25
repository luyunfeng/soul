package tech.soulcoder.uniqueId;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 高并发场景下System.currentTimeMillis()的性能问题的优化
 * <p>
 * <p>
 * System.currentTimeMillis()的调用比new一个普通对象要耗时的多（参加https://www.jianshu.com/p/3fbe607600a5）
 * <p>
 * System.currentTimeMillis()之所以慢是因为去跟系统打了一次交道
 * <p>
 * 后台定时更新时钟，JVM退出时，线程自动回收
 * <p>
 * 10亿：43410,206,210.72815533980582%
 * <p>
 * 1亿：4699,29,162.0344827586207%
 * <p>
 * 1000万：480,12,40.0%
 * <p>
 * 100万：50,10,5.0%
 *
 * @author yunfeng.lu
 * @create 2019/3/21.
 */
public class SystemClock {

    private final long period;

    private final AtomicLong nowTimestamp;

    private SystemClock(long period) {
        this.period = period;
        this.nowTimestamp = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    /**
     * 内部类实现的单例模式
     */
    private static class InstanceHolder {
        private static final SystemClock INSTANCE = new SystemClock(1);
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    private static final ScheduledThreadPoolExecutor EXECUTOR =
        new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private static final String NAME_PREFIX = "SystemClock";

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(Thread.currentThread().getThreadGroup(), r,
                    NAME_PREFIX + threadNumber.getAndIncrement(),
                    0);
                if (t.isDaemon()) {
                    t.setDaemon(false);
                }
                if (t.getPriority() != Thread.NORM_PRIORITY) {
                    t.setPriority(Thread.NORM_PRIORITY);
                }
                return t;
            }
        }, new AbortPolicy());

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(EXECUTOR::shutdown));
    }

    private void scheduleClockUpdating() {
        EXECUTOR.scheduleAtFixedRate(() ->
            nowTimestamp.set(System.currentTimeMillis()), period, period, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return nowTimestamp.get();
    }

    public static long now() {
        return instance().currentTimeMillis();
    }

}
