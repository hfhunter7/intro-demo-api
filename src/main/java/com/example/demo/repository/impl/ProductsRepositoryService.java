package com.example.demo.repository.impl;

import com.example.demo.domain.Products;
import com.example.demo.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsRepositoryService {

    @Autowired
    private ProductsRepository productsRepository;

    public ProductsRepositoryService() {

    }

    public Products save(Products products) {
        return productsRepository.save(products);
    }

    public void update(Products products) {
        productsRepository.save(products);
    }

    public void update(List<Products> productsList) {
        productsRepository.save(productsList);
    }

    public void delete(Long id) {
        productsRepository.delete(id);
    }

    public void delete(Products products) {
        productsRepository.delete(products);
    }

    public Products find(Long id) {
        return productsRepository.findOne(id);
    }

    public List<Products> findAll() {
        return productsRepository.findAll();
    }
}
