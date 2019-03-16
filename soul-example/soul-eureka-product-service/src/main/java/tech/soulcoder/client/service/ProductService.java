package tech.soulcoder.client.service;



import tech.soulcoder.client.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProduct();

    Product findById(int id);


}
