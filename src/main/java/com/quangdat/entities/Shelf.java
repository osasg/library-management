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
import javax.persistence.Table;

@Entity
@Table(name = "shelf" , catalog = "quangdatphambookstore")
public class Shelf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "CODE", nullable = false)
	private String code;
	
	@Column(name = "NUMBER_OF_DRAWER", nullable = false)
	private Long numberOfDrawer;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "shelf")
	private List<Book> books;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "LIBRARY_ID", nullable = false)
	private Library library;

	public Shelf() {
	}

	public Shelf(String code, Long numberOfDrawer, String name, List<Book> books, Library library) {
		this.code = code;
		this.numberOfDrawer = numberOfDrawer;
		this.name = name;
		this.books = books;
		this.library = library;
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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	public void addBook(Book book) {
		this.books.add(book);
	}
	
	public void deleteBook(Book book) {
		this.books.remove(book);
	}

}
