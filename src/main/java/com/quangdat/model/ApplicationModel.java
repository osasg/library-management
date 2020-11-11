package com.quangdat.model;

public class ApplicationModel {
	private Long id;
	private String username;
	private Long library_id;
	private String library_name;
	
	public ApplicationModel() {
	}

	public ApplicationModel(Long id, String username, Long library_id, String library_name) {
		this.id = id;
		this.username = username;
		this.library_id = library_id;
		this.library_name = library_name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getLibrary_id() {
		return library_id;
	}

	public void setLibrary_id(Long library_id) {
		this.library_id = library_id;
	}

	public String getLibrary_name() {
		return library_name;
	}

	public void setLibrary_name(String library_name) {
		this.library_name = library_name;
	}
	
}
