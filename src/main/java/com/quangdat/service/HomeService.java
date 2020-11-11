package com.quangdat.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quangdat.dao.ApplicationDao;
import com.quangdat.dao.BookDao;
import com.quangdat.dao.CategoryDao;
import com.quangdat.dao.LibraryDao;
import com.quangdat.dao.UserDao;
import com.quangdat.entities.Book;
import com.quangdat.entities.BookImage;
import com.quangdat.entities.Category;
import com.quangdat.entities.Library;
import com.quangdat.entities.Shelf;
import com.quangdat.model.BookSearchAdvancedModel;
import com.quangdat.util.FIleCompareColorUtil;
import com.quangdat.util.SecurityUtil;

@Transactional
@Service
public class HomeService {
	
	private static final String UPLOADED_FOLDER = "E://UPLOAD_FOLDER//";
	
	@Autowired
	private LibraryDao libraryDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	public Library getLibrary(Long library_id) {
		Library library = libraryDao.get(library_id);
		library.getShelves().size();
		return library;
	}
	
	public List<Library> getAllLibraries(){
		return libraryDao.getAll();
	}

	public List<Book> getBookByCategory(Long category_id) {
		Category category = categoryDao.get(category_id);
		category.getBooks().size();
		return category.getBooks();
	}
	
	public List<Category> getAllCategories() {
		return categoryDao.getAll();
	}
	
	public List<Book> getAllBooks()	{
		List<Book> books = new ArrayList<>();
		for (Library lib:libraryDao.getAll()) {
			for(Shelf s:lib.getShelves()) {
				books.addAll(s.getBooks());
			}
		}
		return books;
	}
	
	public List<Long> getLibraryIdByUsername(){
		List<Long> libIds = new ArrayList<>();
		String username = SecurityUtil.getPricipal();
		if(username != "anonymousUser") {
			libIds.addAll(applicationDao.getLibraryIdByUsername(username));
			for(Library lib:userDao.get(username).getServedLibraries()) {
				libIds.add(lib.getId());
			}
		}
		
		return libIds;
	}
	
	public Book viewBook(Long book_id) {
		return bookDao.get(book_id);
	}
	
	public List<Book> searchAdvanced(BookSearchAdvancedModel searchModel){
		List<Book> books = new ArrayList<>();
		books.addAll(bookDao.searchAdvanced(searchModel.getName(), libraryDao.get(searchModel.getLibrary_id()), categoryDao.get(searchModel.getCategory_id())));
		//color
		Integer r = searchModel.getRed();
		Integer g = searchModel.getGreen();
		Integer b = searchModel.getBlue();
		if(r != null && g != null && b != null) {
			for(Book book:books) {
				for(BookImage bi:book.getBookImages()) {
					File fileImage = new File(UPLOADED_FOLDER + bi.getName());
					if(FIleCompareColorUtil.compare(fileImage, r, g, b)) {
						books.add(book);
						break;
					}
				}
			}
		}
		return books;
	}
}
