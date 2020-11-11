package com.quangdat.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user", catalog = "quangdatphambookstore")
public class User {
	@Id
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "ADDRESS", nullable = false)
	private String address;

	@DateTimeFormat
	@Column(name = "BIRTHDAY", nullable = false)
	private Date birthday;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "REGISTRATION_DATE", nullable = false)
	private Date registrationDate;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "USERNAME")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
	private List<Role> roles;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "librarian", joinColumns = {@JoinColumn(name = "LIBRARIAN")}, inverseJoinColumns = {@JoinColumn(name = "OWNEDLIBRARY")})
	private List<Library> ownedLibraries;
	
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(name = "employee", joinColumns = {@JoinColumn(name = "EMPLOYEE")}, inverseJoinColumns = {@JoinColumn(name = "SERVEDLIBRARY")})
	private List<Library> servedLibraries;
	
	@ManyToMany(cascade = CascadeType.DETACH)
	@JoinTable(name = "book_user", joinColumns = {@JoinColumn(name = "USER")}, inverseJoinColumns = {@JoinColumn(name = "Book_ID")})
	private List<Book> books;
	
	public User() {
	}
	
	public User(String username, String password, String address, Date birthday, String email, Date registrationDate,
			List<Role> roles) {
		this.username = username;
		this.password = password;
		this.address = address;
		this.birthday = birthday;
		this.email = email;
		this.registrationDate = registrationDate;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public List<Library> getOwnedLibraries() {
		return ownedLibraries;
	}

	public void setOwnedLibraries(List<Library> ownedLibraries) {
		this.ownedLibraries = ownedLibraries;
	}

	public List<Library> getServedLibraries() {
		return servedLibraries;
	}

	public void setServedLibraries(List<Library> servedLibraries) {
		this.servedLibraries = servedLibraries;
	}
	
	//my setter && getter
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	public void deleteRole(Role role) {
		this.roles.remove(role);
	}
	
	public void addOwnedLibrary(Library library) {
		this.ownedLibraries.add(library);
	}
	
	public void deleteOwnedLibrary(Library library) {
		this.ownedLibraries.remove(library);
	}
	
	public void addServedLibrary(Library library) {
		this.servedLibraries.add(library);
	}
	
	public void deleteServedLibrary(Library library) {
		this.servedLibraries.remove(library);
	}

	public void deleteAllServedLibrary() {
		this.servedLibraries.removeAll(this.servedLibraries);
	}
	
	public void deleteAllOwnedLibrary() {
		this.ownedLibraries.removeAll(this.ownedLibraries);
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
}