package com.epam.newsmanagement.service;

import java.util.List;

import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.NewsTO;

/**
 * The Interface NewsService.
 */
public interface NewsService {

	/**
	 * Adds the news.
	 *
	 * @param news the news
	 * @return the generated id
	 * @throws ServiceException the service exception
	 */
	public int add(NewsTO news) throws ServiceException;

	/**
	 * Removes the news.
	 *
	 * @param id the news's id
	 * @throws ServiceException the service exception
	 */
	public boolean remove(int newsId) throws ServiceException;

	/**
	 * Update news.
	 *
	 * @param news the news
	 * @throws ServiceException the service exception
	 */
	public boolean update(NewsTO news) throws ServiceException;

	/**
	 * Gets the news by id.
	 *
	 * @param id the id
	 * @return the by id
	 * @throws ServiceException the service exception
	 */
	public NewsTO getById(int id) throws ServiceException;

	/**
	 * Gets the list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	public List<NewsTO> getList(int start, int count) throws ServiceException;

	/**
	 * Gets the list by author.
	 *
	 * @param authorId the author id
	 * @return the list by author
	 * @throws ServiceException the service exception
	 */
	public List<NewsTO> getList(int authorId, int start, int count) throws ServiceException;

	/**
	 * Gets the list by tags.
	 *
	 * @param listId the list id
	 * @return the list by tags
	 * @throws ServiceException the service exception
	 */
	public List<NewsTO> getList(List<Integer> listId, int start, int count)	throws ServiceException;
	
	public List<NewsTO> getList(int authorId, List<Integer> listId, int start, int count)	throws ServiceException;
	
	public int getNumberNews() throws ServiceException;
	
	public int getNumberNews(int authorId) throws ServiceException;
	
	public int getNumberNews(List<Integer> listId) throws ServiceException;
	
	public int getNumberNews(int authorId, List<Integer> listId) throws ServiceException;

}