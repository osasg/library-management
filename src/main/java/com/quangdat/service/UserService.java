package com.quangdat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quangdat.dao.ApplicationDao;
import com.quangdat.dao.BookDao;
import com.quangdat.dao.CategoryDao;
import com.quangdat.dao.LibraryDao;
import com.quangdat.dao.RoleDao;
import com.quangdat.dao.UserDao;
import com.quangdat.entities.Application;
import com.quangdat.entities.Book;
import com.quangdat.entities.Category;
import com.quangdat.entities.Library;
import com.quangdat.entities.Role;
import com.quangdat.entities.RoleName;
import com.quangdat.entities.User;
import com.quangdat.util.SecurityUtil;

@Transactional
@Service
public class UserService {
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private LibraryDao libraryDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	public void addNewUser(User user) {
		user.setRegistrationDate(new Date());
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		dao.add(user);
	}
	
	public User get(String username) {
		return dao.get(username);
	}
	
	public List<User> getAll(){
		return dao.getAll();
	}
	
	public void delete(String username) {
		dao.deleteByKey(username);
	}
	
	public void update(User oldUser, User user) {
		if(!oldUser.getPassword().equalsIgnoreCase(user.getPassword())) {
			oldUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		}
		oldUser.setAddress(user.getAddress());
		oldUser.setBirthday(user.getBirthday());
		oldUser.setEmail(user.getEmail());
		oldUser.setRoles(user.getRoles());
		dao.update(oldUser);
	}
	
	//member
	public void createAccount(User user) {
		List<Role> roles = new ArrayList<>();
		roles.add(roleDao.getRoleByName(RoleName.MEMBER));
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRoles(roles);
		user.setRegistrationDate(new Date());
		dao.add(user);
	}
	
	//apply for employee
	public Boolean applyForEmployee(Long library_id) {
		String username = SecurityUtil.getPricipal();
		Library library = libraryDao.get(library_id);
		//require library //require not yet apply for this library
		if(library != null && ! applicationDao.getLibraryIdByUsername(username).contains(library_id) && ! library.getEmployees().contains(dao.get(username))) {
			applicationDao.add(new Application(null, username, library));
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public void buyBook(Long book_id) {
		Book book = bookDao.get(book_id);
		User user = dao.get(SecurityUtil.getPricipal());
		if (!user.getBooks().contains(book))
			user.getBooks().add(book);
	}
	
	public List<Book> getMyBook() {
		List<Book> books = new ArrayList<>();
		for (Book b:dao.get(SecurityUtil.getPricipal()).getBooks()) {
			books.add(b);
		}
		return books;
	}
	
	//get roles
	public Role getRole(Long id) {
		return roleDao.get(id);
	}
	
	//category //admin
	public List<Role> getAllRoles(){
		return roleDao.getAll();
	}
	
	public Category getCategory(Long category_id) {
		return categoryDao.get(category_id);
	}
	
	public List<Category> getCategories(){
		return categoryDao.getAll();
	}
	
	public List<Category> getCategoriesAndBookSize() {
		List<Category> categories = categoryDao.getAll();
		for (Category c:categories) {
			c.getBooks().size();
		}
		return categories;
	}
	
	public void addCategory(Category category) {
		categoryDao.add(category);
	}
	
	public void deleteCategory(Long category_id) {
		categoryDao.deleteByKey(category_id);
	}
	
	public void updateCategory(Category category) {
		categoryDao.update(category);
	}
}
