package tech.soulcoder.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author as
 * @create 2019-03-21 21:03
 * @desc 配置中心
 */
@SpringBootApplication
@EnableConfigServer
public class EurekaCloudConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCloudConfigApplication.class, args);
    }

}
