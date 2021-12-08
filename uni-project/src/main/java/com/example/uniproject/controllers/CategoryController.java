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
import com.example.uniproject.entities.UserEntity;
import com.example.uniproject.repositories.ProductCategoryRepository;

@RestController
public class CategoryController {
	ProductCategoryRepository _productCategoryRepository;

	public CategoryController(ProductCategoryRepository productCategoryRepository) {
		_productCategoryRepository = productCategoryRepository;
	}

	@PostMapping(path = "/category/add")
	public String addCategory(@RequestParam(value = "categoryName") String name, HttpSession session) {

		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user != null) {
			ProductCategoryEnitity category = new ProductCategoryEnitity();

			category.setName(name);

			category = _productCategoryRepository.saveAndFlush(category);

			if (category != null) {
				return String.valueOf(category.getId());
			}

			return "Error: Insert unsuccessfull!";
		}

		return "Error: There is no logged user!";
	}

	@PostMapping(path = "/category/update")
	public String updateProduct(@RequestParam(value = "categoryId") int id, @RequestParam(value = "name") String name,
			HttpSession session) {

		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user != null) {

			Optional<ProductCategoryEnitity> optionalCategory = _productCategoryRepository.findById(id);

			if (optionalCategory.isPresent()) {

				ProductCategoryEnitity category = optionalCategory.get();

				category.setId(id);
				category.setName(name);

				category = _productCategoryRepository.saveAndFlush(category);

				if (category != null) {
					return String.valueOf(category.getId());
				}
			}

			return "Category not inserted!";
		}
		return "Error: there is no logged user!";
	}

	@GetMapping(path = "/category/all")
	public List<ProductCategoryEnitity> getAllCategories() {
		return _productCategoryRepository.findAll();
	}

	@DeleteMapping(path = "/category/delete")
	public ResponseEntity<Boolean> deleteCategory(@RequestParam(value = "id") int id, HttpSession session) {
		UserEntity user = (UserEntity) session.getAttribute("user");

		if (user == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
		}

		Optional<ProductCategoryEnitity> catEntity = _productCategoryRepository.findById(id);

		if (catEntity.isPresent()) {
			ProductCategoryEnitity category = catEntity.get();
			_productCategoryRepository.delete(category);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
	}

}
