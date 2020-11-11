package com.quangdat.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quangdat.model.BookSearchAdvancedModel;
import com.quangdat.service.HomeService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final static String UPLOAD_FOLDER = "/home/quangdatpham/Pictures/libsmanager/";
	
	private Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService service;

	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		addAttribute_Libraries_Categories(model);
		return "home";
	}
	
	@RequestMapping(value = "/libraryPage/{library_id}", method = RequestMethod.GET)
	public String libraryPage(@PathVariable Long library_id, ModelMap model) {
		model.addAttribute("libIds", service.getLibraryIdByUsername());
		addAttribute_Libraries_Categories(model);
		model.addAttribute("library", service.getLibrary(library_id));
		return "libraryPage";
	}
	
	@RequestMapping(value = "/getCategory/{category_id}", method = RequestMethod.GET)
	public String getCategory(@PathVariable Long category_id, ModelMap model) {
		addAttribute_Libraries_Categories(model);
		model.addAttribute("books", service.getBookByCategory(category_id));
		return "listBook";
	}
	
	@RequestMapping(value = "/getAllBooks", method = RequestMethod.GET)
	public String getAllBooks(ModelMap model) {
		addAttribute_Libraries_Categories(model);
		model.addAttribute("searchAdvanced", new BookSearchAdvancedModel());
		model.addAttribute("books", service.getAllBooks());
		return "listBook";
	}
	
	@RequestMapping(value = "/searchAdvanced", method = RequestMethod.POST)
	public String searchAdvanced(@ModelAttribute BookSearchAdvancedModel searchModel) {
		service.searchAdvanced(searchModel);
		return "listBook";
	}
	
	@RequestMapping(value = "/viewBook/{book_id}", method = RequestMethod.GET)
	public String viewBook(@PathVariable Long book_id, ModelMap model) {
		model.addAttribute("book", service.viewBook(book_id));
		return "viewBook";
	}
	
	@RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
	public void getFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {
	    try {
	    	File file = new File(UPLOAD_FOLDER + fileName);
	    	if(!file.exists()) {
	    		log.error("File doesn't exits :{}", fileName);
	    		return;
	    	}
	    	InputStream is = new BufferedInputStream(new FileInputStream(file));
	    	IOUtils.copy(is, response.getOutputStream());
	    	response.flushBuffer();
	    	log.info("Download file :{}", fileName);
	    } catch (IOException ex) {
	      log.info("Error writing file to output stream. Filename was :{}", fileName, ex);
	      throw new RuntimeException("IOError writing file to output stream");
	    }
	}
	
	private void addAttribute_Libraries_Categories(ModelMap model) {
		model.addAttribute("libraries", service.getAllLibraries());
		model.addAttribute("categories", service.getAllCategories());
	}
}
