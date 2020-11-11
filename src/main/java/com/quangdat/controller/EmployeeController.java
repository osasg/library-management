package com.quangdat.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.quangdat.entities.Book;
import com.quangdat.entities.Library;
import com.quangdat.entities.Shelf;
import com.quangdat.model.BookImageModel;
import com.quangdat.model.BookModel;
import com.quangdat.model.TrialBookModel;
import com.quangdat.service.EmployeeService;
import com.quangdat.validator.BookModelValidator;
import com.quangdat.validator.ShelfValidator;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@Autowired
	private BookModelValidator bookValidator;
	
	@Autowired
	private ShelfValidator shelfValidator;
	
	@InitBinder("bookModel")
	private void initBinderBook(WebDataBinder binder) {
		binder.addValidators(bookValidator);
	}
	
	@InitBinder("shelf")
	private void initBinderShelf(WebDataBinder binder) {
		binder.addValidators(shelfValidator);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String employeePage(ModelMap model) {
		model.addAttribute("libraries", service.getServedLibraries());
		return "employee/employee";
	}
	
	@RequestMapping(value = "/getBooks/{library_id}", method = RequestMethod.GET)
	public String getBooks(@PathVariable Long library_id, ModelMap model) {
		model.addAttribute("library", service.getLibrary(library_id));
		model.addAttribute("books", service.getBookByLibrary(library_id));
		model.addAttribute("categories", service.getCategories());
		return "book/book";
	}
	
	@RequestMapping(value = "/newBook/{library_id}", method = RequestMethod.GET)
	public String newBook(@PathVariable Long library_id, ModelMap model) throws JsonProcessingException {
		model.addAttribute("shelves", new Gson().toJson(service.getShelvesViewModel(library_id)));
		model.addAttribute("bookModel", new BookModel(library_id));
		model.addAttribute("categories", service.getCategories());
		return "book/bookDetail";
	}
	
	@RequestMapping(value = "/updateBook/{book_id}", method = RequestMethod.GET)
	public String updateBook(@PathVariable Long book_id, ModelMap model) {
		Book b = service.getBook(book_id);
		model.addAttribute("bookModel", new BookModel(b.getId(), b.getName(), b.getShelf().getLibrary().getId(), b.getCategory().getId(), b.getShelf().getId(), b.getDrawer()));
		model.addAttribute("shelves", new Gson().toJson(service.getShelvesViewModel(b.getShelf().getLibrary().getId())));
		model.addAttribute("categories", service.getCategories());
		return "book/bookDetail";
	}
	
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST)
	public String saveBook(@ModelAttribute("bookModel") @Valid BookModel bookModel, BindingResult result, ModelMap model) {
		Book book = new Book();
		if(result.hasErrors()) {
			model.addAttribute("shelves", new Gson().toJson(service.getShelvesViewModel(bookModel.getLibrary_id())));
			model.addAttribute("categories", service.getCategories());
			return "book/bookDetail";
		}
		if(bookModel.getId() == null) {
			book = service.addBook(bookModel);
		} else {
			book = service.updateBook(bookModel);
		}
		return "redirect:/employee/viewBook/" + book.getId();
	}
	
	@RequestMapping(value = "/viewBook/{book_id}", method = RequestMethod.GET)
	public String viewBook(@PathVariable Long book_id, ModelMap model) {
		model.addAttribute("trialBookModel", new TrialBookModel(book_id));
		model.addAttribute("book", service.getBook(book_id));
		model.addAttribute("bookImageModel", new BookImageModel(book_id));
		return "book/viewBook";
	}
	
	@RequestMapping(value = "/deleteBook/{book_id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable Long book_id) {
		Long library_id = service.deleteBook(book_id);
		return "redirect:/employee/getBooks/" + library_id;
	}
	
	@RequestMapping(value = "/addBookImage", method = RequestMethod.POST)
	public String addBookImage(@ModelAttribute BookImageModel bookImageModel) {
		service.addImagesForBook(bookImageModel.getFiles(), bookImageModel.getBook_id());
		return "redirect:/employee/viewBook/" + bookImageModel.getBook_id();
	}
	
	@RequestMapping(value = "/deleteImage/{image_id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long image_id, @RequestParam Long book_id) {
		service.deleteImage(image_id);
		return "redirect:/employee/viewBook/" + book_id;
	}
	
	@RequestMapping(value = "/alterTrialBook", method = RequestMethod.POST)
	public String alterTrialBook(@ModelAttribute TrialBookModel trialBookModel) {
		service.alterTrialBook(trialBookModel);
		return "redirect:/employee/viewBook/" + trialBookModel.getBook_id();
	}
	
	@RequestMapping(value = "/getShelves/{library_id}", method = RequestMethod.GET)
	public String getShelves(@PathVariable Long library_id, ModelMap model) {
		model.addAttribute("library", service.getLibrary(library_id));
		model.addAttribute("shelves", service.getShelfByLibrary(library_id));
		return "employee/shelf";
	}
	
	@RequestMapping(value = "/newShelf/{library_id}", method = RequestMethod.GET)
	public String newShelf(@PathVariable Long library_id, ModelMap model) {
		model.addAttribute("shelf", new Shelf(null, null, null, null, new Library(library_id,null)));
		return "employee/shelfDetail";
	}
	
	@RequestMapping(value = "/updateShelf/{shelf_id}", method = RequestMethod.GET)
	public String updateShelf(@PathVariable Long shelf_id, ModelMap model) {
		model.addAttribute("shelf", service.getShelf(shelf_id));
		return "employee/shelfDetail";
	}
	
	@RequestMapping(value = "/saveShelf", method = RequestMethod.POST)
	public String saveShelf(@ModelAttribute @Valid Shelf shelf, BindingResult result) {
		if(result.hasErrors())
			return "employee/shelfDetail";
		if(shelf.getId() == null) {
			service.addShelf(shelf);
		} else {
			service.updateShelf(shelf);
		}
		return "redirect:/employee/getShelves/" + shelf.getLibrary().getId();
	}
	
	@RequestMapping(value = "/deleteShelf/{shelf_id}", method = RequestMethod.GET)
	public String deleteShelf(@PathVariable Long shelf_id) {
		Long library_id = service.getShelf(shelf_id).getLibrary().getId();
		service.deleteShelf(shelf_id);
		return "redirect:/employee/getShelves/" + library_id;
	}
}
