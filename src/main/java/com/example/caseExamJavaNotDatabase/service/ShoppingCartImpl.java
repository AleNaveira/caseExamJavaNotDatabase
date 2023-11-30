package com.example.caseExamJavaNotDatabase.service;

import com.example.caseExamJavaNotDatabase.model.DTO.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ShoppingCartImpl implements ShoppingCart{
    private final List<ProductDTO> productList;

    public ShoppingCartImpl(){
        this.productList = new ArrayList<>();
    }

    @Override
    public List<ProductDTO> viewCart() {
        return productList;
    }

    @Override
    public void addProduct(ProductDTO product) {




        productList.add(product);

    }

    @Override
    public void removeProduct(Long productId) {

        productList.removeIf(product-> product.getId().equals(productId));

    }

    @Override
    public double calculateTotalPrice() {
        return
        productList.stream().mapToDouble(ProductDTO::getPrice).sum();
    }

    @Override
    public boolean idIsValid(Long productId) {
        return productList.stream().anyMatch(product-> product.getId().equals(productId));
    }
}
