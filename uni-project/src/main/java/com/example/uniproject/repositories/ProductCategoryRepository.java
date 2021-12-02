package com.example.uniproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.uniproject.entities.ProductCategoryEnitity;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEnitity, Integer> {

}
