package by.bsu.strelkov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.strelkov.dao.NewsDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.News;
import by.bsu.strelkov.service.NewsService;

@Service
public class NewsServiceImpl extends CrudServiceImpl<News> implements NewsService {
	
	private NewsDAO newsDAO;

	@Autowired
	public NewsServiceImpl(NewsDAO newsDAO) {
		super(newsDAO);
		this.newsDAO = newsDAO;
	}

	public List<News> getList(long authorId, long start, long count) throws DiplomException {
		return newsDAO.getList(authorId, start, count);
	}

	public List<News> getList(List<Long> listId, long start, long count) throws DiplomException {
		return newsDAO.getList(listId, start, count);
	}
	
	public List<News> getList(long authorId, List<Long> listId, long start, long count)	throws DiplomException {
		return newsDAO.getList(authorId, listId, start, count);
	}
	
	public int getNumberNews() throws DiplomException {
		return newsDAO.getNumberNews();
	}
	
	public int getNumberNews(long authorId) throws DiplomException {
		return newsDAO.getNumberNews(authorId);
	}
	
	public int getNumberNews(List<Long> listId) throws DiplomException {
		return newsDAO.getNumberNews(listId);
	}
	
	public int getNumberNews(long authorId, List<Long> listId) throws DiplomException {
		return newsDAO.getNumberNews(authorId, listId);
	}

	@Override
	public List<News> search(int start, int count, List<Long> tagsId, long authorId) {
		return newsDAO.search(start, count, tagsId, authorId);
	}

}