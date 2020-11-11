package com.quangdat.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.quangdat.entities.Book;
import com.quangdat.entities.Category;
import com.quangdat.entities.Library;

@Repository
public class BookDao extends AbstractDaoImpl<Long, Book>{

	@SuppressWarnings("unchecked")
	public List<Book> searchAdvanced(String name, Library library, Category category) {
		Criteria criteria = createCriteria();
		if(name != null) {
			String[] splitedName = name.split("\\s+");
			for(int i = 0; i < splitedName.length; i++) {
				criteria.add(Restrictions.eq("name", "%" + splitedName[i] + "%"));
			}
		}
		if(category != null) {
			criteria.add(Restrictions.eq("category", category));
		}
		if(library !=null) {
			criteria.add(Restrictions.eq("shelf.library", library));
		}
		return criteria.list();
	}
}
