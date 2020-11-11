package com.quangdat.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.quangdat.entities.Application;

@Repository
public class ApplicationDao extends AbstractDaoImpl<Long, Application>{
	
	@SuppressWarnings("unchecked")
	public List<Long> getLibraryIdByUsername(String username) {
		List<Long> usernames = new ArrayList<>();
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("username", username));
		List<Application> applications = criteria.list();
		for(Application e:applications) {
			usernames.add(e.getLibrary().getId());
		}
		return usernames;
	}

	@SuppressWarnings("unchecked")
	public List<Application> getApplicationByLibraryId(Long library_id) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("library_id", library_id));
		return criteria.list();
	}
}
