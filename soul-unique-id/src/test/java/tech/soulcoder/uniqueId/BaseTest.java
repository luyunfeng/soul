package tech.soulcoder.uniqueId;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import tech.soulcoder.uniqueId.simple.UUIDCreator;

/**
 * @author yunfeng.lu
 * @create 2019/2/27.
 */
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class BaseTest {
    public static final ThreadPoolExecutor EXECUTOR =
        new ThreadPoolExecutor(15, 15, 10000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private static final String NAME_PREFIX = "BaseTest";

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

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder().
            include(BaseTest.class.getName()).forks(1).build();
        new Runner(options).run();


    }

    @Benchmark
    @Threads(10)
    public void run0() {
        UUIDCreator.generate();
    }
}
