package com.quangdat.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.quangdat.entities.Library;

@Repository
public class LibraryDao extends AbstractDaoImpl<Long, Library>{
	public Library getLastRecord() {
		Criteria criteria = createCriteria();
		criteria.setMaxResults(1);
		criteria.addOrder(Order.desc("id"));
		return (Library) criteria.uniqueResult();
	}
}
