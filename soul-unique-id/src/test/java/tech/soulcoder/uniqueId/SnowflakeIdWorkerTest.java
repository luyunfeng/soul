package tech.soulcoder.uniqueId;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.util.StringUtils;
import tech.soulcoder.uniqueId.simple.UUIDCreator;
import tech.soulcoder.uniqueId.snowflake.SnowflakeIdWorker;

/**
 * @author yunfeng.lu
 * @create 2019/2/28.
 */
public class SnowflakeIdWorkerTest extends BaseTest {

    @Test
    public void creatorTest() {

        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 1);
        for (int i = 0; i < 1000; i++) {
            EXECUTOR.execute(() -> {
                long id = snowflakeIdWorker.nextId();
                System.out.println(snowflakeIdWorker.parseUID(id));
            });
        }
    }

    /**
     * 验证唯一性
     */
    @Test
    public void creatorTest_2() {
        ConcurrentHashMap<String, String> baseHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 100000; i++) {
            EXECUTOR.execute(() -> {
                String key = UUIDCreator.generate();
                baseHashMap.put(key, UUIDCreator.generate());
            });
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(baseHashMap.size());
    }
}
