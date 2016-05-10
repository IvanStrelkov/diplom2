package com.epam.newsmanagement.dao;

import java.util.List;

import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.AuthorTO;

/**
 * The Interface AuthorDAO.
 */
public interface AuthorDAO {

	/**
	 * Add author to table.
	 *
	 * @param author the author to be added
	 * @return the generated id
	 * @throws DAOException the DAO exception
	 */
	public int add(AuthorTO author) throws DAOException;

	/**
	 * Removes the author by id from table.
	 *
	 * @param id the author's id
	 * @throws DAOException the DAO exception
	 */
	public void remove(int id) throws DAOException;

	/**
	 * Update author in table.
	 *
	 * @param author the author
	 * @throws DAOException the DAO exception
	 */
	public void update(AuthorTO author) throws DAOException;

	/**
	 * Gets author by id.
	 *
	 * @param id the author's id
	 * @return author
	 * @throws DAOException the DAO exception
	 */
	public AuthorTO getById(int id) throws DAOException;

	/**
	 * Gets the list of authors.
	 *
	 * @return the list
	 * @throws DAOException the DAO exception
	 */
	public List<AuthorTO> getList() throws DAOException;

	/**
	 * Attach news to author.
	 *
	 * @param newsId the news id
	 * @param authorId the author id
	 * @throws DAOException the DAO exception
	 */
	public void attachNews(int newsId, int authorId) throws DAOException;

	/**
	 * Gets the list by news.
	 *
	 * @param newsId the news id
	 * @return the list by news
	 * @throws DAOException the DAO exception
	 */
	public AuthorTO getAuthorByNews(int newsId) throws DAOException;
	
	/**
	 * Removes the news author relations by news id from table.
	 *
	 * @param id the author's id
	 * @throws DAOException the DAO exception
	 */
	public void removeRelations(int newsId) throws DAOException;
}