package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Products;

public interface ProductsRepository extends JpaRepository<Products, Long> {

}
