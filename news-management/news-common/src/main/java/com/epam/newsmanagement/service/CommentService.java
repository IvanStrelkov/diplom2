package com.epam.newsmanagement.service;

import java.util.List;

import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.CommentTO;

/**
 * The Interface CommentService.
 */
public interface CommentService {

	/**
	 * Adds the comment.
	 *
	 * @param comment the comment
	 * @return the generated id
	 * @throws ServiceException the service exception
	 */
	public int add(CommentTO comment) throws ServiceException;

	/**
	 * Update comment.
	 *
	 * @param comment the comment
	 * @throws ServiceException the service exception
	 */
	public boolean update(CommentTO comment) throws ServiceException;

	/**
	 * Removes the comment.
	 *
	 * @param id the comment's id
	 * @throws ServiceException the service exception
	 */
	public boolean remove(int id) throws ServiceException;

	/**
	 * Gets the comment by id.
	 *
	 * @param id the id
	 * @return the by id
	 * @throws ServiceException the service exception
	 */
	public CommentTO getById(int id) throws ServiceException;

	/**
	 * Gets the list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	public List<CommentTO> getList() throws ServiceException;

	/**
	 * Gets the list by news.
	 *
	 * @param newsId the news id
	 * @return the list by news
	 * @throws ServiceException the service exception
	 */
	public List<CommentTO> getListByNews(int newsId) throws ServiceException;

}