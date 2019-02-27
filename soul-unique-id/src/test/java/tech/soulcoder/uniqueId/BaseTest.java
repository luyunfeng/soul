package tech.soulcoder.uniqueId;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author yunfeng.lu
 * @create 2019/2/27.
 */
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class BaseTest {

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
