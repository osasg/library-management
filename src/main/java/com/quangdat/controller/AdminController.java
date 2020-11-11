package com.quangdat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quangdat.entities.Category;
import com.quangdat.entities.User;
import com.quangdat.service.UserService;
import com.quangdat.validator.CategoryValidator;
import com.quangdat.validator.UserValidator;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CategoryValidator categoryValidator;
	
	@InitBinder("user")
	private void initBinderUser(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@InitBinder("category")
	private void initBinderCategory(WebDataBinder binder) {
		binder.addValidators(categoryValidator);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String adminPage() {
		return "admin/admin";
	}
	
	@RequestMapping(value = "/listUser", method = RequestMethod.GET)
	public String getAllUser(Model model) {
		model.addAttribute("mode", "ADMIN");
		model.addAttribute("users", userService.getAll());
		return "user/user";
	}
	
	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String getNewUser(Model model) {
		model.addAttribute("mode", "NEW");
		model.addAttribute("roles", userService.getAllRoles());
		model.addAttribute("user", new User());
		return "user/registration";
	}
	
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String setNewUser(@ModelAttribute("user") @Validated User user, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("roles", userService.getAllRoles());
			return "user/registration";
		}
		userService.addNewUser(user);
		return "redirect:/admin/listUser";
	}
	
	@RequestMapping(value = "/updateUser/{username}", method = RequestMethod.GET)
	public String getUser(Model model, @PathVariable String username) {
		model.addAttribute("mode", "UPDATE");
		model.addAttribute("roles", userService.getAllRoles());
		model.addAttribute("user", userService.get(username));
		return "user/registration";
	}
	
	@RequestMapping(value = "/updateUser/{username}", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") @Validated User user, BindingResult result) {
		userService.update(userService.get(user.getUsername()), user);
		return "redirect:/admin/listUser";
	}
	
	@RequestMapping(value = "/deleteUser/{username}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable String username) {
		userService.delete(username);
		return "redirect:/admin/listUser";
	}
	
	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public String listCategory(ModelMap model) {
		model.addAttribute("categories", userService.getCategoriesAndBookSize());
		return "admin/category";
	}
	
	@RequestMapping(value = "/newCategory", method = RequestMethod.GET)
	public String newCategory(ModelMap model) {
		model.addAttribute("category", new Category());
		return "admin/categoryDetail";
	}
	
	@RequestMapping(value = "/updateCategory/{category_id}", method = RequestMethod.GET)
	public String updateCategory(@PathVariable Long category_id, ModelMap model) {
		model.addAttribute("category", userService.getCategory(category_id));
		return "admin/categoryDetail";
	}
	
	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute @Validated Category category, BindingResult result) {
		if(result.hasErrors())
			return "admin/categoryDetail";
		if(category.getId() == null) {
			userService.addCategory(category);
		} else {
			userService.updateCategory(category);
		}
		return "redirect:/admin/listCategory";
	}
	
	@RequestMapping(value = "/deleteCategory/{category_id}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable Long category_id) {
		userService.deleteCategory(category_id);
		return "redirect:/admin/listCategory";
	}
}
