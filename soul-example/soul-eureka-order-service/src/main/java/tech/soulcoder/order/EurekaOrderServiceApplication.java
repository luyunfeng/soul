package tech.soulcoder.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author as
 * @create 2019-03-16 10:05
 * @desc Eureka客户端入口
 */
@SpringBootApplication
@EnableFeignClients
public class EurekaOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaOrderServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced//负载均衡策略
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
