package com.quangdat.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quangdat.entities.User;
import com.quangdat.model.AjaxResponseModel;
import com.quangdat.service.UserService;
import com.quangdat.util.SecurityUtil;
import com.quangdat.validator.UserValidator;


@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@InitBinder("user")
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}
	
	@ResponseBody
	@RequestMapping(value = "/applyForEmployee/")
	public AjaxResponseModel applyForEmployee(@RequestBody String library_id) {
		AjaxResponseModel result = new AjaxResponseModel();
		if(userService.applyForEmployee(Long.valueOf(library_id))) {
			result.setCode("200");
			result.setMsg("Applied successfully");
			result.setResult("OK");
		} else {
			result.setCode("404");
			result.setMsg("Not found");
			result.setResult("ERROR");
		}
		return result;//response
	}
	
	@RequestMapping(value = "/createAccount", method = RequestMethod.GET)
	public String getNewAccount(Model model) {
		model.addAttribute("mode", "NEW");
		model.addAttribute("user", new User());
		return "user/registration";
	}
	
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public String createAccuont(@ModelAttribute("user") @Validated User user, BindingResult result, HttpServletRequest request) {
		if(result.hasErrors())
			return "user/registration";
		userService.createAccount(user);
		
		//login after registered
		try {
			request.login(user.getUsername(), user.getPassword());
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/home/";
	}
	
	@RequestMapping(value = "/updateAccount", method = RequestMethod.GET)
	public String getAccount(Model model) {
		model.addAttribute("mode", "UPDATE");
		model.addAttribute("user", userService.get(SecurityUtil.getPricipal()));
		return "user/registration";
	}
	
	@RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
	public String updateAccount(@ModelAttribute("user") @Validated User user, BindingResult result) {
		userService.update(userService.get(SecurityUtil.getPricipal()), user);
		return "redirect:/home/";
	}
	
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public String deleteUser() {
		userService.delete(SecurityUtil.getPricipal());
		return "redirect:/logout";
	}
	
	@RequestMapping(value = "/buyBook/{book_id}", method = RequestMethod.GET)
	public String buyBook(@PathVariable Long book_id, ModelMap model) {
		userService.buyBook(book_id);
		return "redirect:/member/myBook";
	}
	
	@RequestMapping(value = "/myBook", method = RequestMethod.GET)
	public String getMyBook(ModelMap model) {
		model.addAttribute("books", userService.getMyBook());
		return "myBook";
	}
}
