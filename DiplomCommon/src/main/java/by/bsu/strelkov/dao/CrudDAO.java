package by.bsu.strelkov.dao;

import java.util.List;

public interface CrudDAO<T> {

	public T create (T model);
	
	public T read (Long model_id);
	
	public T update (T model);

	public void delete (Long model_id);
	
	public List<T> readAll();
}
