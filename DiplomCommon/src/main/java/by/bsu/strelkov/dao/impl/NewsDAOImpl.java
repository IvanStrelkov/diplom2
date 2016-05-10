package by.bsu.strelkov.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.bsu.strelkov.dao.NewsDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.News;

@Repository
public class NewsDAOImpl extends CrudDAOImpl<News> implements NewsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public NewsDAOImpl() {
		super(News.class);
	}

	@Override
	public List<News> getList(long authorId, long start, long count) throws DiplomException {
		return readAll();
	}

	@Override
	public List<News> getList(List<Long> listId, long start, long count) throws DiplomException {
		return readAll();
	}

	@Override
	public List<News> getList(long authorId, List<Long> listId, long start, long count) throws DiplomException {
		return readAll();
	}

	@Override
	public int getNumberNews() throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getNumberNews(long authorId) throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getNumberNews(List<Long> listId) throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getNumberNews(long authorId, List<Long> listId) throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

}
