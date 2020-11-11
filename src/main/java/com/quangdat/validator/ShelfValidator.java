package com.quangdat.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.quangdat.entities.Shelf;

@Component
public class ShelfValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Shelf.class.equals(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmpty(err, "code", "shelf.code.empty");
		ValidationUtils.rejectIfEmpty(err, "name", "shelf.name.empty");
		ValidationUtils.rejectIfEmpty(err, "numberOfDrawer", "shelf.numberOfDrawer.empty");
	}
}
