package com.quangdat.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BookImageModel {
	
	private Long book_id;
	
	private List<MultipartFile> files;
	
	public BookImageModel() {
	}

	public BookImageModel(Long book_id) {
		this.book_id = book_id;
	}

	public BookImageModel(List<MultipartFile> files) {
		this.files = files;
	}

	public Long getBook_id() {
		return book_id;
	}

	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
}
