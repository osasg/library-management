package com.quangdat.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.quangdat.model.LibraryModel;

@Component
public class LibraryModelValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return LibraryModel.class.equals(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors err) {
//		LibraryModel libraryModel = (LibraryModel) obj;
		ValidationUtils.rejectIfEmpty(err, "name", "libraryModel.name.empty");
//		if(libraryModel.getFile() != null && libraryModel.getFile().getSize() == 0)
//			err.rejectValue("file", "missing.file");
	}
}
