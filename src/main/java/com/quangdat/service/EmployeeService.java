package com.quangdat.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.quangdat.dao.BookDao;
import com.quangdat.dao.BookImageDao;
import com.quangdat.dao.CategoryDao;
import com.quangdat.dao.LibraryDao;
import com.quangdat.dao.ShelfDao;
import com.quangdat.dao.UserDao;
import com.quangdat.entities.Book;
import com.quangdat.entities.BookImage;
import com.quangdat.entities.Category;
import com.quangdat.entities.Library;
import com.quangdat.entities.Shelf;
import com.quangdat.entities.TrialBook;
import com.quangdat.entities.User;
import com.quangdat.model.BookModel;
import com.quangdat.model.ShelvesViewModel;
import com.quangdat.model.TrialBookModel;
import com.quangdat.util.SecurityUtil;

@Transactional
@Service
public class EmployeeService {
	
	private static final String UPLOADED_FOLDER = "/home/quangdatpham/Pictures/libsmanager/";
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LibraryDao libraryDao;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ShelfDao shelfDao;
	
	@Autowired
	private BookImageDao bookImageDao;
	
	public Library getLibrary(Long library_id) {
		return libraryDao.get(library_id);
	}
	
	public List<Book> getBookByLibrary(Long library_id){
		Library library = libraryDao.get(library_id);
		List<Book> books = new ArrayList<>();
		if(! check(library))
			return null;
		library.getShelves().size();
		for(Shelf s:library.getShelves()) {
			books.addAll(s.getBooks());
		}
		return books;
	}

	public List<Library> getServedLibraries(){
		User employee = userDao.get(SecurityUtil.getPricipal());
		employee.getServedLibraries().size();
		return employee.getServedLibraries();
	}
	
	public List<Shelf> getAllShelfByLibraries() {
		List<Shelf> shelves = new ArrayList<Shelf>();
		for (Library lib:getServedLibraries()) {
			shelves.addAll(lib.getShelves());
		}
		return shelves;
	}
	
	//book
	public Book addBook(BookModel bookModel) {
		Library library = libraryDao.get(bookModel.getLibrary_id());
		if(! check(library))
			return null;
		Book book = new Book();
		book.setCategory(categoryDao.get(bookModel.getCategory_id()));
		book.setDrawer(bookModel.getDrawer());
		book.setName(bookModel.getName());
		book.setShelf(shelfDao.get(bookModel.getShelf_id()));
		bookDao.add(book);
		return book;
	}
	
	public Book getBook(Long book_id) {
		Book book = bookDao.get(book_id);
		if(! check(book.getShelf().getLibrary()))
			return null;
		return book;
	}
	
	public Book updateBook(BookModel bookModel) {
		Library library = libraryDao.get(bookModel.getLibrary_id());
		if(! check(library))
			return null;
		Book oldBook = bookDao.get(bookModel.getId());
		oldBook.setCategory(categoryDao.get(bookModel.getCategory_id()));
		oldBook.setDrawer(bookModel.getDrawer());
		oldBook.setName(bookModel.getName());
		oldBook.setShelf(shelfDao.get(bookModel.getShelf_id()));
		return oldBook;
	}
	
	public Long deleteBook(Long book_id) {
		File fileImage = new File("");
		Book book = bookDao.get(book_id);
		Shelf shelf = shelfDao.get(book.getShelf().getId());
		if(check(shelf.getLibrary())) {
			for(BookImage bi:book.getBookImages()) {
				fileImage = new File(UPLOADED_FOLDER + bi.getName());
				if(fileImage.delete()) {
					bookImageDao.delete(bi);
				}
			}
			shelf.deleteBook(book);
			book.setShelf(null);
		}
		return shelf.getLibrary().getId();
	}
	
	public void addImagesForBook(List<MultipartFile> files, Long book_id) {
		Book book = bookDao.get(book_id);
		BookImage bookImage;
		for(int i = 0; i < files.size(); i++) {
			String imageName = saveImage(files.get(i));
			if(! imageName.equalsIgnoreCase("")) {
				bookImage = new BookImage();
				bookImage.setName(imageName);
				bookImage.setBook(book);
				bookImageDao.add(bookImage);
			}
		}
	}
	
	public void deleteImage(Long image_id) {
		BookImage bookImage = bookImageDao.get(image_id);
		Book book = bookImage.getBook();
		if(check(book.getShelf().getLibrary())) {
			File file = new File(UPLOADED_FOLDER + bookImageDao.get(image_id).getName());
			if(file.delete()) {
				book.deleteImage(bookImage);
				bookImage.setBook(null);
			}
		}
	}
	
	public void alterTrialBook(TrialBookModel trialBookModel){
		TrialBook trialBook = new TrialBook();
		MultipartFile file = trialBookModel.getFile();
		try {
			trialBook.setName(file.getOriginalFilename());
			trialBook.setContent(file.getBytes());
			Book book = bookDao.get(trialBookModel.getBook_id());
			book.setTrialBook(trialBook);
			trialBook.setBook(book);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//shelf
	public List<ShelvesViewModel> getShelvesViewModel(Long library_id){
		List<ShelvesViewModel> shelves = new ArrayList<>();
		Library library = libraryDao.get(library_id);
		if(! check(library))
			return null;
		for(Shelf s:library.getShelves()) {
			shelves.add(new ShelvesViewModel(s.getId(), s.getCode(), s.getNumberOfDrawer(), s.getName()));
		}
		return shelves;
	}
	
	public Shelf newShelf(Long library_id) {
		return new Shelf(null, null, null, null, libraryDao.get(library_id));
	}
	
	public void addShelf(Shelf shelf) {
		Library library = libraryDao.get(shelf.getLibrary().getId());
		if(check(library)) {
			shelf.setLibrary(library);
			shelfDao.add(shelf);
		}
	}
	
	public Shelf getShelf(Long shelf_id) {
		return shelfDao.get(shelf_id);
	}
	
	public void updateShelf(Shelf shelf) {
		Library library = libraryDao.get(shelf.getLibrary().getId());
		if(check(library)) {
			Shelf oldShelf =shelfDao.get(shelf.getId());
			oldShelf.setCode(shelf.getCode());
			oldShelf.setName(shelf.getName());
			oldShelf.setNumberOfDrawer(shelf.getNumberOfDrawer());
		}
	}
	
	public void deleteShelf(Long shelf_id) {
		File fileImage = new File("");
		Shelf shelf = shelfDao.get(shelf_id);
		Library library = libraryDao.get(shelf.getLibrary().getId());
//		if(check(library)) {
			for(Book b:shelf.getBooks()) {
				for(BookImage bi:b.getBookImages()) {
					fileImage = new File(UPLOADED_FOLDER + bi.getName());
					fileImage.delete();
				}
			}
			library.deleteShelf(shelf);
			shelf.setLibrary(null);
//		}
	}
	
	public List<Category> getCategories(){
		return categoryDao.getAll();
	}
	
	//check if this employee serve for that library
	private Boolean check(Library library) {
		return getServedLibraries().contains(library);
	}
	
	private String saveImage(MultipartFile file) {
		String fileName = "";
		try {
			if(file.getSize() != 0) {
				fileName = new Date().getTime() + file.getOriginalFilename();
				FileCopyUtils.copy(file.getBytes(), new File(UPLOADED_FOLDER + fileName));
			}
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
		return fileName;
	}

	public List<Shelf> getShelfByLibrary(Long library_id) {
		Library library = libraryDao.get(library_id);
		library.getShelves().size();
		return library.getShelves();
	}
	
}
