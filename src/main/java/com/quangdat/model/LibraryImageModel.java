package com.quangdat.model;

import org.springframework.web.multipart.MultipartFile;

public class LibraryImageModel {

	private Long library_id;
	private MultipartFile avatarFile;
	private MultipartFile coverImageFile;
	
	public LibraryImageModel() {
	}

	public LibraryImageModel(Long library_id, MultipartFile avatarFile, MultipartFile coverImageFile) {
		this.library_id = library_id;
		this.avatarFile = avatarFile;
		this.coverImageFile = coverImageFile;
	}

	public Long getLibrary_id() {
		return library_id;
	}

	public void setLibrary_id(Long library_id) {
		this.library_id = library_id;
	}

	public MultipartFile getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(MultipartFile avatarFile) {
		this.avatarFile = avatarFile;
	}

	public MultipartFile getCoverImageFile() {
		return coverImageFile;
	}

	public void setCoverImageFile(MultipartFile coverImageFile) {
		this.coverImageFile = coverImageFile;
	}

}
