package com.epam.newsmanagement.service;

import java.util.List;

import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.NewsVO;

/**
 * The Interface NewsVOService.
 */
public interface NewsVOService {

	/**
	 * Adds the.
	 *
	 * @param newsMessage the news message
	 * @return the generated id
	 * @throws ServiceException the service exception
	 */
	public int add(NewsVO newsMessage) throws ServiceException;

	/**
	 * Removes the.
	 *
	 * @param newsId the id of news
	 * @throws ServiceException the service exception
	 */
	public void remove(int newsId) throws ServiceException;
	
	/**
	 * Update.
	 *
	 * @param news the news
	 * @throws ServiceException the service exception
	 */
	public void update(NewsVO news) throws ServiceException;

	/**
	 * Gets the by id.
	 *
	 * @param id the id of news
	 * @return the news by id
	 * @throws ServiceException the service exception
	 */
	public NewsVO getById(int id) throws ServiceException;

	/**
	 * Gets the list of news.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	public List<NewsVO> getList(int authorId, List<Integer> tagsId, int start, int count) throws ServiceException;
	
	public int getNumberNews(int authorId, List<Integer> tagsId) throws ServiceException;
}
