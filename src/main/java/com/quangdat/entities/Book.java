package com.quangdat.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "book", catalog = "quangdatphambookstore")
public class Book{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "book")
	private TrialBook trialBook;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "book")
	private List<BookImage> bookImages;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private Category category;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "SHELF_ID", nullable = false)
	private Shelf shelf;
	
	@Column(name = "DRAWER", nullable = false)
	private Long drawer;
	
	public Book() {
	}

	public Book(Long id, String name, Category category) {
		this.id = id;
		this.name = name;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public TrialBook getTrialBook() {
		return trialBook;
	}

	public void setTrialBook(TrialBook trialBook) {
		this.trialBook = trialBook;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<BookImage> getBookImages() {
		return bookImages;
	}

	public void setBookImages(List<BookImage> bookImages) {
		this.bookImages = bookImages;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

	public Long getDrawer() {
		return drawer;
	}
	
	public void setDrawer(Long drawer) {
		this.drawer = drawer;
	}
	
	//
	public void addImage(BookImage bookImage) {
		this.bookImages.add(bookImage);
	}
	
	public void deleteImage(BookImage bookImage) {
		this.bookImages.remove(bookImage);
	}
	
	public void deleteAllBookImage() {
		this.bookImages.clear();
	}
}
