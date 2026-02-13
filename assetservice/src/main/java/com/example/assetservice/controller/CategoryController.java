package com.example.assetservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.assetservice.dto.CategoryDTO;
import com.example.assetservice.entities.Category;
import com.example.assetservice.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	 @PostMapping("/create")
	    public ResponseEntity<Category> create(@RequestBody CategoryDTO dto, Authentication authentication) {
	        // authentication present due to introspection filter
	        return ResponseEntity.ok(categoryService.create(dto));
	    }

	    @GetMapping
	    public ResponseEntity<List<Category>> list(Authentication authentication) {
	        return ResponseEntity.ok(categoryService.list());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Category> update(@PathVariable Long id,
	                                          @RequestBody CategoryDTO dto,
	                                          Authentication authentication) {
	        return ResponseEntity.ok(categoryService.update(id, dto));
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deactivate(@PathVariable Long id, Authentication authentication) {
	        categoryService.deactivate(id);
	        return ResponseEntity.ok().body("Category deactivated");
	    }
}
