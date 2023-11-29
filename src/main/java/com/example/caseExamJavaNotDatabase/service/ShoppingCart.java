package com.example.caseExamJavaNotDatabase.service;

import com.example.caseExamJavaNotDatabase.model.DTO.ProductDTO;

import java.util.List;

public interface ShoppingCart {


    public List<ProductDTO> viewCart();
    public void addProduct (ProductDTO product);
    public void removeProduct (Long productId);

    public double calculateTotalPrice();

    public boolean idIsValid(Long productId);




}
