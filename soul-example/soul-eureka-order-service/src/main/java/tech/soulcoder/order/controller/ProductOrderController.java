package tech.soulcoder.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.soulcoder.order.service.ProductOrderService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/product/order")
public class ProductOrderController {


    @Autowired
    private ProductOrderService productOrderService;

    @HystrixCommand(fallbackMethod = "saveOrderFail")
    @RequestMapping("save")
    public Object save(@RequestParam("user_id")int userId, @RequestParam("product_id") int productId){

        return productOrderService.save(userId, productId);
    }

    //注意，方法签名一定要要和api方法一致
    private Object saveOrderFail(int userId, int productId){
        Map<String, Object> msg = new HashMap<>();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，您被挤出来了，稍等重试");
        return msg;
    }

}
