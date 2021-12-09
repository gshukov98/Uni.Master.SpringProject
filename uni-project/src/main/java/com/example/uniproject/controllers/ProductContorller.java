package com.example.uniproject.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.uniproject.entities.ProductCategoryEnitity;
import com.example.uniproject.entities.ProductEntity;
import com.example.uniproject.entities.UserEntity;
import com.example.uniproject.repositories.ProductCategoryRepository;
import com.example.uniproject.repositories.ProductRepository;

@RestController
public class ProductContorller {

	ProductRepository _productRepository;
	ProductCategoryRepository _productCategoryRepository;

	public ProductContorller(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
		_productRepository = productRepository;
		_productCategoryRepository = productCategoryRepository;
	}

	@PostMapping(path = "/product/add")
	public String addProduct(@RequestParam(value = "productName") String name,
			@RequestParam(value = "productPrice") double price, @RequestParam(value = "productQuantity") double quantity,
			@RequestParam(value = "categoryId") int categoryId, HttpSession session) {

		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user != null) {

			Optional<ProductCategoryEnitity> optionalCategory = _productCategoryRepository.findById(categoryId);

			if (optionalCategory.isPresent()) {

				ProductCategoryEnitity category = optionalCategory.get();

				ProductEntity product = new ProductEntity();

				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);
				product.setUser(user);

				product = _productRepository.saveAndFlush(product);

				if (product != null) {
					return String.valueOf(product.getId());
				}

				return "Error: Insert unsuccessfull!";
			}

			return "Error: Category not found!";
		}

		return "Error: There is no logged user!";
	}

	@PostMapping(path = "/product/update")
	public String updateProduct(@RequestParam(value = "productId") int id,
			@RequestParam(value = "productName") String name, @RequestParam(value = "productPice") double price,
			@RequestParam(value = "productQuantity") double quantity, HttpSession session) {

		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user != null) {
			Optional<ProductEntity> optionalProduct = _productRepository.findById(id);

			if (optionalProduct.isPresent()) {

				ProductEntity product = optionalProduct.get();
				product.setId(id);
				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setUser(user);

				product = _productRepository.saveAndFlush(product);

				if (product != null) {
					return String.valueOf(product.getId());
				}

			}

			return "Product not inserted!";
		}

		return "Error: there is no logged user!";
	}
	
	@GetMapping(path = "/product/all")
	public List<ProductEntity> getAllProducts() {
		return _productRepository.findAll();
	}
	
	@DeleteMapping(path = "/product/delete")
	public ResponseEntity<Boolean> deleteProduct(@RequestParam(value = "id") int id, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
		}

		Optional<ProductEntity> prodEntity = _productRepository.findById(id);

		if (prodEntity.isPresent()) {
			ProductEntity product = prodEntity.get();
			_productRepository.delete(product);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
	}

}
