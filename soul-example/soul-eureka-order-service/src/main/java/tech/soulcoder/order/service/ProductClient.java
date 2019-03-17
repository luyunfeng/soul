package tech.soulcoder.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.soulcoder.order.fallbcak.ProductClientFallback;

/**
 * 商品服务客户端
 */
@FeignClient(name = "product-service" , fallback = ProductClientFallback.class)
public interface ProductClient {


    @GetMapping("/api/v1/product/find")
    String findById(@RequestParam(value = "id") int id);


}
