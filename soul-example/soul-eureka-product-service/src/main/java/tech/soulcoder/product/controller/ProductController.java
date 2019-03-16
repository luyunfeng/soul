package tech.soulcoder.product.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.soulcoder.product.domain.Product;
import tech.soulcoder.product.service.ProductService;

/**
 * @author as
 * @create 2019-03-16 10:17
 * @desc 商品web层
 */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {



    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductService productService;

    /**
     * 获取所有商品列表
     * @return
     */
    @RequestMapping("list")
    public Object list(){
        return productService.listProduct();
    }


    /**
     * 根据id查找商品详情
     * @param id
     * @return
     */
    @RequestMapping("find")
    public Object findById(int id){

        Product product = productService.findById(id);

        Product result = new Product();
        BeanUtils.copyProperties(product,result);
        result.setName( result.getName() + " data from port="+port );
        return result;
    }



}

