package com.quangdat.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoImpl<K extends Serializable, T> implements DaoInteface<K, T> {
	@Autowired
	private SessionFactory sessionFactory;
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	public AbstractDaoImpl(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void add(T entity) {
		Session session = getSession();
		session.persist(entity);
	}
	
	@Override
	public T get(K key) {
		return getSession().get(this.persistentClass, key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		return getSession().createQuery("from " + persistentClass.getName()).list();
	}
	
	@Override
	public void update(T entity) {
		getSession().update(entity);
	}
	
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	@Override
	public void deleteByKey(K key) {
		T entity = getSession().load(this.persistentClass, key);
		delete(entity);
	}
	
	@SuppressWarnings("deprecation")
	public Criteria createCriteria() {
		return getSession().createCriteria(persistentClass);
	}
	
}
