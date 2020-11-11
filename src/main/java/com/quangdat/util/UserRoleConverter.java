package com.quangdat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.quangdat.entities.Role;
import com.quangdat.service.UserService;

@Component
public class UserRoleConverter implements Converter<Object, Role> {

	static final Logger logger = LoggerFactory.getLogger(UserRoleConverter.class);

	@Autowired
	private UserService userService;

	public Role convert(Object object) {
		Role role = userService.getRole(Long.parseLong((String) object));
		return role;
	}
}
