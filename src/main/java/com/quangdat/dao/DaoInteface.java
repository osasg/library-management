package com.quangdat.dao;

import java.util.List;

public interface DaoInteface<K, T> {
	
	void add(T entity);
	
	T get(K key);
	
	List<T> getAll();
	
	void update(T entity);
	
	void delete(T entity);
	
	void deleteByKey(K key);
}
