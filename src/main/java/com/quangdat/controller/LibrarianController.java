package com.quangdat.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quangdat.entities.Library;
import com.quangdat.model.AjaxResponseModel;
import com.quangdat.model.LibraryImageModel;
import com.quangdat.model.LibraryModel;
import com.quangdat.service.LibrarianService;
import com.quangdat.validator.LibraryModelValidator;

@Controller
@RequestMapping(value = "/librarian")
public class LibrarianController {

	@Autowired
	private LibrarianService service;
	
	@Autowired
	private LibraryModelValidator libraryModelValidator;
	
	@InitBinder("libraryModel")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(libraryModelValidator);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String librarianPage(ModelMap model) {
		model.addAttribute("libraryImageModel", new LibraryImageModel());
		model.addAttribute("applicationModels", service.getApplicationModels());
		model.addAttribute("libraries", service.getOwnedLibraries());
		return "librarian/librarian";
	}
	
	@RequestMapping(value = "/getEmployees/{library_id}", method = RequestMethod.GET)
	public String getEmployees(@PathVariable Long library_id, ModelMap model) {
		model.addAttribute("applicationModels", service.getApplicationsByLibrary(library_id));
		model.addAttribute("mode", "LIBRARIAN");
		model.addAttribute("users", service.getEmployeesForLibrary(library_id));
		model.addAttribute("library", service.getLibrary(library_id));
		return "user/user";
	}
	
	@RequestMapping(value = "/getBooks/{library_id}", method = RequestMethod.GET)
	public String getBooks(@PathVariable Long library_id, ModelMap model) {
		model.addAttribute("library", service.getBooksForLibrary(library_id));
		return "book/book";
	}
	
	@RequestMapping(value = "/libService", method = RequestMethod.GET)
	public String getLibraries(ModelMap model) {
		model.addAttribute("libraries", service.getOwnedLibraries());
		return "librarian/library";
	}
	
	public String listMyUser() {
		return "user/user";
	}
	
	@ResponseBody
	@RequestMapping(value = "/applyEmployee")
	public AjaxResponseModel applyEmployee(@RequestBody String application_id) {
		AjaxResponseModel result = new AjaxResponseModel();
		if(service.applyEmployee(Long.parseLong(application_id))){
			result.setCode("200");
			result.setMsg("You applied this employee successfully");
			result.setResult("OK");
		} else {
			result.setCode("204");
			result.setResult("No content");
			result.setMsg("No application");
		}
		return result;
	}
	
	@RequestMapping(value = "/rejectEmployee/{username}/{library_id}", method = RequestMethod.DELETE)
	public void rejectEmployee(@PathVariable String username, @PathVariable Long library_id) {
		service.rejectEmployee(username, library_id);
	}
	
	//register Library
	@RequestMapping(value = "/registerLibrary", method = RequestMethod.GET)
	public String getNewLibrary(Model model) {
		model.addAttribute("libraryModel", new LibraryModel());
		return "librarian/registerLibrary";
	}
	
	@RequestMapping(value = "/updateLibrary/{library_id}", method = RequestMethod.GET)
	public String getLibrary(@PathVariable Long library_id, ModelMap model) {
		Library library = service.getLibrary(library_id);
		model.addAttribute("libraryModel", new LibraryModel(library.getId(), library.getName()));
		return "librarian/registerLibrary";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String updateLibrary(@ModelAttribute("libraryModel") @Valid LibraryModel libraryModel, BindingResult result) throws IOException {
		if(result.hasErrors())
			return "librarian/registerLibrary";
		if(libraryModel.getId() != null) {
			service.updateLibrary(new Library(libraryModel.getId(), libraryModel.getName()));
		} else {
			service.createLibrary(new Library(null, libraryModel.getName()));
		}
		return "redirect:/librarian/";
	}
	
	@RequestMapping(value = "/changeImage", method = RequestMethod.POST)
	public String changeImage(@ModelAttribute LibraryImageModel libraryImageModel) {
		service.changeImageLibrary(libraryImageModel.getAvatarFile(), libraryImageModel.getCoverImageFile(), libraryImageModel.getLibrary_id());
		return "redirect:/librarian/";
	}
	
	@RequestMapping(value = "/deleteLibrary/{library_id}", method = RequestMethod.GET)
	public String deleteLibrary(@PathVariable Long library_id) {
		service.deleteLibrary(library_id);
		return "redirect:/librarian/";
	}
	
	@RequestMapping(value = "/getShelves/{library_id}", method = RequestMethod.GET)
	public String getShelves(@PathVariable Long library_id, ModelMap model) {
		model.addAttribute("shelves", service.getShelves(library_id));
		return "librarian/shelf";
	}
}
