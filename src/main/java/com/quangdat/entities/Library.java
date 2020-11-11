package com.quangdat.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "library", catalog = "quangdatphambookstore")
public class Library {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "AVATAR")
	private String avatar;
	
	@Column(name = "COVER_IMAGE")
	private String coverImage;
	
	@ManyToMany(cascade = CascadeType.DETACH, mappedBy = "ownedLibraries")
	private List<User> librarians = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.DETACH, mappedBy = "servedLibraries")
	private List<User> employees;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "library")
	private List<Application> applications;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "library")
	private List<Shelf> shelves;
	
	public Library() {
	}
	
	public Library(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<User> getLibrarians() {
		return librarians;
	}

	public void setLibrarians(List<User> librarians) {
		this.librarians = librarians;
	}

	public List<User> getEmployees() {
		return employees;
	}

	public void setEmployees(List<User> employees) {
		this.employees = employees;
	}
	
	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	public List<Shelf> getShelves() {
		return shelves;
	}

	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}

	//MY setter getter
	public void addEmployee(User employee) {
		this.employees.add(employee);
	}
	
	public void deleteEmployee(User employee) {
		this.employees.remove(employee);
	}
	
	public void addLibrarian(User librarian) {
		this.librarians.add(librarian);
	}
	
	public void deleteLibrarian(User librarian) {
		this.librarians.remove(librarian);
	}
	
	public void addShelf(Shelf shelf) {
		this.shelves.add(shelf);
	}
	
	public void deleteShelf(Shelf shelf) {
		this.shelves.remove(shelf);
	}
	
	public void deleteAllLibrarian(Role role_LIBRARIAN) {
		for(User librarian:this.librarians) {
			librarian.deleteOwnedLibrary(this);
			if(librarian.getOwnedLibraries().size() == 0) {
				librarian.deleteRole(role_LIBRARIAN);
			}
		}
		this.librarians.removeAll(this.librarians);
	}
	
	public void deleteAllEmployee(Role role_EMPLOYEE) {
		for(User employee:this.employees) {
			employee.deleteServedLibrary(this);
			if(employee.getServedLibraries().size() == 0) {
				employee.deleteRole(role_EMPLOYEE);
			}
		}
		this.employees.removeAll(this.employees);
	}

	public void deleteAllShelf() {
		this.shelves.clear();
	}
	
}
