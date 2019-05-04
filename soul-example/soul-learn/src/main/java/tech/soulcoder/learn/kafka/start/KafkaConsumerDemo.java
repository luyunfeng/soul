package tech.soulcoder.learn.kafka.start;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.ThreadUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author yunfeng.lu
 * @create 2019/4/29.
 */
public class KafkaConsumerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("bootstrap.servers", "kafka0:19092,kafka1:19093,kafka2:19094");
        props.setProperty("group.id", "123");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if(CollectionUtil.isEmpty(records)){
                ThreadUtil.sleep(5000);
            }
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }
}
