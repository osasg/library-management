package com.quangdat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quangdat.util.SecurityUtil;

@Controller
@RequestMapping(value = "/")
public class SecurityController {
	
	@Autowired
	private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeService;
	
	@Autowired
	private AuthenticationTrustResolver  authenticationTrustResolver;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		if(isCurrentAuthenticationAnonymous())
			return "login";
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDenied(Model model) {
		model.addAttribute("loggedInUser", SecurityUtil.getPricipal());
		return "accessDenied";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			persistentTokenBasedRememberMeService.logout(request, response, authentication);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?logout";
	}

	private Boolean isCurrentAuthenticationAnonymous() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}
	
}
