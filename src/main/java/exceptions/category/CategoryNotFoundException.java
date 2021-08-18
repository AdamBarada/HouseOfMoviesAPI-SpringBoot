package exceptions.category;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends RuntimeException {

	public CategoryNotFoundException(Long id) {
		super("Category " + id + " is not found.");
	}
}
