package com.quangdat.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.quangdat.entities.Shelf;
import com.quangdat.model.BookModel;
import com.quangdat.service.EmployeeService;

@Component
public class BookModelValidator implements Validator{

	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return BookModel.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors err) {
		BookModel bookModel = (BookModel) obj;
		ValidationUtils.rejectIfEmpty(err, "name", "book.name.empty");
		ValidationUtils.rejectIfEmpty(err, "category_id", "book.category.empty");
		ValidationUtils.rejectIfEmpty(err, "drawer", "book.drawer.empty");
		if(bookModel.getShelf_id() == -1) {
			err.rejectValue("shelf_id", "book.shelf.empty");
		} else {
			int c = 0;
			for(Shelf s:employeeService.getAllShelfByLibraries()) {
				if (s.getId() == bookModel.getShelf_id())
					c = 1;
			}
			if(c == 0) {
				err.rejectValue("shelf_id", "book.shelf.invalid");
			} else if(bookModel.getDrawer() > employeeService.getShelf(bookModel.getShelf_id()).getNumberOfDrawer()) {
				err.rejectValue("drawer", "book.drawer.invalid");
			}
		}
		
	}

}
