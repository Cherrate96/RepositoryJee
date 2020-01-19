package com.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.domain.Category;
import com.home.repository.CategoryRepository;

@RestController
public class CategoryController {
@Autowired
	public CategoryRepository categoryRepository;

//@GetMapping("/categories")
//public List<Category> findAll()
//{
//	return categoryRepository.findAll();
//}
}
