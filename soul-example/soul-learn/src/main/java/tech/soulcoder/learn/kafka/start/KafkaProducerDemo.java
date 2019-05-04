package tech.soulcoder.learn.kafka.start;

import java.util.Properties;

import cn.hutool.core.thread.ThreadUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author yunfeng.lu
 * @create 2019/4/29.
 */
public class KafkaProducerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka0:19092,kafka1:19093,kafka2:19094");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100000; i++) {
            producer.send(new ProducerRecord<>("test", Integer.toString(i), Integer.toString(i)));
        }
        producer.close();
    }
}
