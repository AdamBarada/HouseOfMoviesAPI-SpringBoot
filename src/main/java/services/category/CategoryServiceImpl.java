package services.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import entities.Category;
import exceptions.category.CategoryNotFoundException;
import repositories.CategoryRepository;

@Component
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
	}

}
