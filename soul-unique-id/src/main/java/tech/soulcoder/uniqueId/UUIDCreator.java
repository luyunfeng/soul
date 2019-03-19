package tech.soulcoder.uniqueId;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import tech.soulcoder.common.Constant;

/**
 * 32位不重复的 id
 * 4位 appId  17位 时间戳  6位机器号  5位 数递增
 *
 * 优化 1： 在 时间戳的计算上改为 java8 方式，下面这种方式效率比较低
 * new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(System.currentTimeMillis()));
 *
 * 如何缩减长度？
 * 1.appId 可以取模 两位数
 * 2.年份可以去掉前面两位
 *
 * @author yunfeng.lu
 * @create 2019/2/27.
 */
public class UUIDCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UUIDCreator.class);

    @Value("${" + Constant.APP_ID + "}")
    private static String appId = "1000";

    static {
        try {
            IP_TAG = formatIp();
        } catch (Exception e) {
            LOGGER.error("ip 标识生成失败", e);
        }
    }

    /**
     * 最大容器容量
     */
    private static final long MAX_ID_CONTAINER_SIZE = 99990L;
    private static final long MIN_ID_CONTAINER_SIZE = 10000L;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    /**
     * 生产者工作状态
     */
    private static AtomicLong AUTO_INCREMENT_ID = new AtomicLong(MIN_ID_CONTAINER_SIZE);

    private static String IP_TAG;

    private static String create() {
        if (AUTO_INCREMENT_ID.get() > MAX_ID_CONTAINER_SIZE) {
            AUTO_INCREMENT_ID.set(MIN_ID_CONTAINER_SIZE);
        }
        return String.valueOf(AUTO_INCREMENT_ID.incrementAndGet());
    }

    public static String generate() {

        return appId + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + IP_TAG + create();
    }

    /**
     * @return 取当前机器IP后两节
     */
    private static String formatIp() throws Exception {

        byte[] ipBytes = InetAddress.getLocalHost().getAddress();

        StringBuilder builder = new StringBuilder();

        int data = Math.abs(ipBytes[2]);

        builder.append(new StringBuilder("000").replace(3 - String.valueOf(data).length(), 3,
            String.valueOf(data)));

        data = Math.abs(ipBytes[3]);

        builder.append(new StringBuilder("000").replace(3 - String.valueOf(data).length(), 3,
            String.valueOf(data)));

        return builder.toString();

    }

}
