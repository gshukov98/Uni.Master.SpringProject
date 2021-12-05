package com.example.uniproject.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.uniproject.entities.ProductCategoryEnitity;
import com.example.uniproject.entities.UserEntity;
import com.example.uniproject.repositories.ProductCategoryRepository;

@RestController
public class CategoryController {
	ProductCategoryRepository _productCategoryRepository;

	public CategoryController(ProductCategoryRepository productCategoryRepository) 
	{
		_productCategoryRepository = productCategoryRepository;
	}
	
	@PostMapping(path = "/category/add")
	public String addCategory(@RequestParam(value = "categoryName") String name, HttpSession session) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if(user != null) {
			ProductCategoryEnitity category = new ProductCategoryEnitity();
			
			category.setName(name);
			
			category = _productCategoryRepository.saveAndFlush(category);
			
			if(category != null) {
				return String.valueOf(category.getId());
			}
			
			return "Error: Insert unsuccessfull!";
		}
		
		return "Error: There is no logged user!";	
	}
	
	@GetMapping(path ="/category/all")
	public List<ProductCategoryEnitity> getAllCategories(){
		return _productCategoryRepository.findAll();		
	}
	

}
