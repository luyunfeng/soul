package tech.soulcoder.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunfeng.lu
 * @create 2019/2/26.
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true)
@RestController
@ComponentScan(basePackages = {"tech.soulcoder"})
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/baseController/{param}")
    public String baseController(@PathVariable("param") String param) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param;
    }
}
