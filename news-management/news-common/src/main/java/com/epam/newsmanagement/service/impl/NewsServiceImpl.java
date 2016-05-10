package com.epam.newsmanagement.service.impl;

import java.util.List;

import com.epam.newsmanagement.dao.NewsDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.NewsTO;
import com.epam.newsmanagement.service.NewsService;

/**
 * The Class NewsService.
 */
public class NewsServiceImpl implements NewsService{

	/** The news dao. */
	private NewsDAO newsDAO;

	/**
	 * Sets the news dao.
	 *
	 * @param newsDAO the news dao
	 */
	public void setNewsDAO(NewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	/**
	 * @see com.epam.newsmanagement.service.NewsService#add(com.epam.newsmanagement.model.NewsTO)
	 */
	public int add(NewsTO news) throws ServiceException{
		try {
			newsDAO.add(news);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return news.getNewsId();
	}
	
	/**
	 * @see com.epam.newsmanagement.service.NewsService#remove(int)
	 */
	public boolean remove(int newsId) throws ServiceException{
		boolean result = true;
		try {
			NewsTO newsCheck = newsDAO.getById(newsId);
			if(newsCheck != null){
				newsDAO.remove(newsId);
			}else{
				result = false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * @see com.epam.newsmanagement.service.NewsService#update(com.epam.newsmanagement.model.NewsTO)
	 */
	public boolean update(NewsTO news) throws ServiceException{
		boolean result = true;
		try {
			NewsTO newsCheck = newsDAO.getById(news.getNewsId());
			if(newsCheck != null){
				newsDAO.update(news);
			}else{
				result = false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * @see com.epam.newsmanagement.service.NewsService#getById(int)
	 */
	public NewsTO getById(int id) throws ServiceException{
		try {
			return newsDAO.getById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.service.NewsService#getList()
	 */
	public List<NewsTO> getList(int start, int count) throws ServiceException{
		try {
			return newsDAO.getList(start, count);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.service.NewsService#getListByAuthor(int)
	 */
	public List<NewsTO> getList(int authorId, int start, int count) throws ServiceException{
		try {
			return newsDAO.getList(authorId, start, count);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.service.NewsService#getListByTags(java.util.List)
	 */
	public List<NewsTO> getList(List<Integer> listId, int start, int count) throws ServiceException{
		try {
			return newsDAO.getList(listId, start, count);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public List<NewsTO> getList(int authorId, List<Integer> listId,
			int start, int count) throws ServiceException {
		try {
			return newsDAO.getList(authorId, listId, start, count);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}	
	public int getNumberNews() throws ServiceException{
		try {
			return newsDAO.getNumberNews();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public int getNumberNews(int authorId) throws ServiceException {
		try {
			return newsDAO.getNumberNews(authorId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public int getNumberNews(List<Integer> listId) throws ServiceException {
		try {
			return newsDAO.getNumberNews(listId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	public int getNumberNews(int authorId, List<Integer> listId)
			throws ServiceException {
		try {
			return newsDAO.getNumberNews(authorId, listId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
