package com.quangdat.model;

public class ShelvesViewModel {

	private Long id;
	
	private String code;
	
	private Long numberOfDrawer;
	
	private String name;
	
	public ShelvesViewModel() {
	}

	public ShelvesViewModel(Long id, String code, Long numberOfDrawer, String name) {
		this.id = id;
		this.code = code;
		this.numberOfDrawer = numberOfDrawer;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getNumberOfDrawer() {
		return numberOfDrawer;
	}

	public void setNumberOfDrawer(Long numberOfDrawer) {
		this.numberOfDrawer = numberOfDrawer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
