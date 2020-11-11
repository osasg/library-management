package com.quangdat.model;

public class BookModel {
	private Long id;
	private String name;
	private Long library_id;
	private Long category_id;
	private Long shelf_id;
	private Long drawer;
	
	public BookModel() {
	}

	public BookModel(Long library_id) {
		this.library_id = library_id;
	}

	public BookModel(Long id, String name, Long library_id, Long category_id, Long shelf_id, Long drawer) {
		this.id = id;
		this.name = name;
		this.library_id = library_id;
		this.category_id = category_id;
		this.shelf_id = shelf_id;
		this.drawer = drawer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLibrary_id() {
		return library_id;
	}

	public void setLibrary_id(Long library_id) {
		this.library_id = library_id;
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

	public Long getShelf_id() {
		return shelf_id;
	}

	public void setShelf_id(Long shelf_id) {
		this.shelf_id = shelf_id;
	}

	public Long getDrawer() {
		return drawer;
	}

	public void setDrawer(Long drawer) {
		this.drawer = drawer;
	}
	
}
