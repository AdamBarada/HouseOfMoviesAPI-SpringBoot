package dto.movie;

import java.util.List;

public class MovieFilterCategoryDto {

	private List<Long> categoriesId;

	public List<Long> getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(List<Long> categoriesId) {
		this.categoriesId = categoriesId;
	}

	public MovieFilterCategoryDto() {
		super();
	}
	
}
