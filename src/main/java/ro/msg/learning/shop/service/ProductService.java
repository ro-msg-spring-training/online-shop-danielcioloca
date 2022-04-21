package ro.msg.learning.shop.service;

import java.util.Collection;

public interface ProductService {
	void createProduct(Product product);
	void updateProduct(String id, Product product);
	void deleteProduct(String id);
	Collection<Product> getProducts();
}