package by.bsu.strelkov.dao;

import java.util.List;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.News;

public interface NewsDAO extends CrudDAO<News> {

	public List<News> getList(long authorId, long start, long count) throws DiplomException;

	public List<News> getList(List<Long> listId, long start, long count)	throws DiplomException;
	
	public List<News> getList(long authorId, List<Long> listId, long start, long count)	throws DiplomException;
	
	public int getNumberNews() throws DiplomException;
	
	public int getNumberNews(long authorId) throws DiplomException;
	
	public int getNumberNews(List<Long> listId) throws DiplomException;
	
	public int getNumberNews(long authorId, List<Long> listId) throws DiplomException;

}