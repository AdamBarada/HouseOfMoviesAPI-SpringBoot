package services.category;

import java.util.List;

import org.springframework.stereotype.Service;

import entities.Category;

@Service
public interface CategoryService {
	List<Category> findAll();
	Category findById(Long id);
}
