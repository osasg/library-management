package com.quangdat.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.quangdat.entities.Role;
import com.quangdat.entities.RoleName;

@Repository
public class RoleDao extends AbstractDaoImpl<Long, Role>{
	
	public Role getRoleByName(RoleName name) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Role) criteria.uniqueResult();
	}
}
