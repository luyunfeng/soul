package tech.soulcoder.order.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.URLConnection;
import sun.net.www.http.HttpClient;
import tech.soulcoder.order.domain.ProductOrder;
import tech.soulcoder.order.service.ProductClient;
import tech.soulcoder.order.service.ProductOrderService;
import tech.soulcoder.order.utils.JsonUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {


    @Autowired
    private RestTemplate restTemplate;//利用ribbon方式 底层httpclient发送一个请求
    @Autowired
    private ProductClient productClient;//利用feign方式


    @Override
    public ProductOrder save(int userId, int productId) {

        //Map<String,Object> productMap = restTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId, Map.class);
        String result = productClient.findById(productId);
        JsonNode jsonNode = JsonUtils.str2JsonNode(result);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setCreateTime(new Date());
        productOrder.setUserId(userId);
        productOrder.setTradeNo(UUID.randomUUID().toString());
//        productOrder.setProductName(productMap.get("name").toString());
//        productOrder.setPrice(Integer.parseInt(productMap.get("price").toString()));
        productOrder.setProductName(jsonNode.get("name").toString());
        productOrder.setPrice(Integer.parseInt(jsonNode.get("price").toString()));
        return productOrder;
    }
}
