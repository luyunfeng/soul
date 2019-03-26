package tech.soulcoder.test;

import java.util.HashMap;

import com.google.common.collect.Maps;
import org.junit.Test;
import tech.soulcoder.uniqueId.simple.UUIDCreator;

/**
 * @author yunfeng.lu
 * @create 2019/2/28.
 */
public class MapTest extends BaseTest {

    @Test
    public void endlessLoopTest() {

        final HashMap<String, String> baseHashMap = Maps.newHashMap();
        for (int i = 0; i < 10000; i++) {
            EXECUTOR.execute(() -> baseHashMap.put(UUIDCreator.generate(), UUIDCreator.generate()));
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
