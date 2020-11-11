package com.quangdat.model;

import org.springframework.web.multipart.MultipartFile;

public class TrialBookModel {

	private Long book_id;
	
	private MultipartFile file;
	
	public TrialBookModel() {
	}

	public TrialBookModel(Long book_id) {
		this.book_id = book_id;
	}

	public TrialBookModel(Long book_id, MultipartFile file) {
		this.book_id = book_id;
		this.file = file;
	}

	public Long getBook_id() {
		return book_id;
	}

	public void setBook_id(Long book_id) {
		this.book_id = book_id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
