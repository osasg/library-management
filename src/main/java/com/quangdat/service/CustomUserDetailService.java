package com.quangdat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quangdat.entities.Role;

@Service
@Qualifier("customUserDetailsService")
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.quangdat.entities.User user =  userService.get(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(com.quangdat.entities.User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		}
		return authorities;
	}
}
