package com.epam.newsmanagement.service;

import java.util.List;

import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;

/**
 * The Interface AuthorService.
 */
public interface AuthorService {

	/**
	 * Adds the author.
	 *
	 * @param author the author
	 * @return the generated id
	 * @throws ServiceException the service exception
	 */
	public int add(AuthorTO author) throws ServiceException;

	/**
	 * Update author.
	 *
	 * @param author the author
	 * @throws ServiceException the service exception
	 */
	public boolean update(AuthorTO author) throws ServiceException;

	/**
	 * Removes the author.
	 *
	 * @param id the author's id
	 * @throws ServiceException the service exception
	 */
	public boolean remove(int id) throws ServiceException;

	/**
	 * Gets the author by id.
	 *
	 * @param id the id
	 * @return the by id
	 * @throws ServiceException the service exception
	 */
	public AuthorTO getById(int id) throws ServiceException;

	/**
	 * Gets the list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	public List<AuthorTO> getList() throws ServiceException;

	/**
	 * Gets the list by news.
	 *
	 * @param newsId the news id
	 * @return the list by news
	 * @throws ServiceException the service exception
	 */
	public AuthorTO getAuthorByNews(int newsId) throws ServiceException;

	/**
	 * Attach news.
	 *
	 * @param newsId the news id
	 * @param authorId the author id
	 * @throws ServiceException the service exception
	 */
	public void attachNews(int newsId, int authorId) throws ServiceException;
	
	/**
	 * Removes the news_author.
	 *
	 * @param id the news's id
	 * @throws ServiceException the service exception
	 */
	public void removeRelations(int newsId) throws ServiceException;
}