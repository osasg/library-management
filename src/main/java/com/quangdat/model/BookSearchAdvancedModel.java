package com.quangdat.model;

public class BookSearchAdvancedModel {
	
	private String name;
	private Long category_id;
	private Long library_id;
	private Long minPrice;
	private Long maxPrice;
	//color
	private Integer red;
	private Integer green;
	private Integer blue;
	
	public BookSearchAdvancedModel() {
	}

	public BookSearchAdvancedModel(String name, Long category_id, Long library_id, Long minPrice, Long maxPrice,
			Integer red, Integer green, Integer blue) {
		this.name = name;
		this.category_id = category_id;
		this.library_id = library_id;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public Long getLibrary_id() {
		return library_id;
	}

	public void setLibrary_id(Long library_id) {
		this.library_id = library_id;
	}

	public Long getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}

	public Long getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Integer getRed() {
		return red;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public Integer getGreen() {
		return green;
	}

	public void setGreen(Integer green) {
		this.green = green;
	}

	public Integer getBlue() {
		return blue;
	}

	public void setBlue(Integer blue) {
		this.blue = blue;
	}

}
