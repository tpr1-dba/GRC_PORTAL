package com.samodule.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface Dao<T> {

	/**
	 * create
	 */
	public void create( T t );
	public void save( T t ) throws Exception;
	
	/**
	 * create all
	 */
	public void create( Collection<T> entities );
	public void saveAll( Collection<T> entities );
	
	/**
	 * update
	 */
	public void update( T t );
	
	/**
	 * update
	 */
	public int update(String idName,Object idValue
			,String property,Object originalValue,Object newValue );
	
	/**
	 * update all 
	 */
	public void update( Collection<T> entities );
	
	/**
	 * read
	 */
	public T read( T t);
	
	/**
	 * read
	 */
	public T read(Serializable identity);
	
	/**
	 * read with lock
	 */
	public T readWithLock(Serializable identity);
			
	/**
	 * delete
	 * @return 
	 */
	public long delete( T t );
	
	/**
	 * delete
	 */
	public void delete( Serializable identity );
	
	/**
	 * delete all
	 */
	public void delete( Collection<T> entities );	
	
	/**
	 * delete all
	 */
	public void deleteAll( );	
	
	/**
	 * list all
	 * @return
	 */
	public List<T> listAll( );
	

}
