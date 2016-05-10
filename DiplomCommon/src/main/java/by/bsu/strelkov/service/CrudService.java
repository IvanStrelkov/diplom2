package by.bsu.strelkov.service;

import java.util.List;

import by.bsu.strelkov.exception.DiplomException;

public interface CrudService <T>{

	public T create (T model) throws DiplomException;
	
	public T read (Long model_id);
	
	public T update (T model) throws DiplomException;

	public void delete (Long model_id);
	
	public List<T> readAll();
}
