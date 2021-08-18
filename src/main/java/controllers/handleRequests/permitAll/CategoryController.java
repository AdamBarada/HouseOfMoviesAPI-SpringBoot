package controllers.handleRequests.permitAll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import entities.Category;
import exceptions.category.CategoryNotFoundException;
import services.category.CategoryService;

@Controller
@CrossOrigin
@RequestMapping("/public/request/categories")
@ResponseBody
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public List<Category> all(){
		return categoryService.findAll();
	}
	
	@GetMapping("/{id}")
	public Category one(@PathVariable Long id) {
		try {
			return categoryService.findById(id);
		} catch(CategoryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
