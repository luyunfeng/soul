package tech.soulcoder.product.service;



import tech.soulcoder.product.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProduct();

    Product findById(int id);


}
