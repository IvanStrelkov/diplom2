package com.epam.newsmanagement.dao;

import java.util.List;

import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.CommentTO;

/**
 * The Interface CommentDAO.
 */
public interface CommentDAO {

	/**
	 * Add comment to table.
	 *
	 * @param comment the comment to be added
	 * @return the generated id
	 * @throws DAOException the DAO exception
	 */
	public int add(CommentTO comment) throws DAOException;

	/**
	 * Removes the comment by id from table.
	 *
	 * @param id the comment's id
	 * @throws DAOException the DAO exception
	 */
	public void remove(int id) throws DAOException;

	/**
	 * Update comment in table.
	 *
	 * @param comment the comment
	 * @throws DAOException the DAO exception
	 */
	public void update(CommentTO comment) throws DAOException;

	/**
	 * Gets comment by id.
	 *
	 * @param id the comment's id
	 * @return comment
	 * @throws DAOException the DAO exception
	 */
	public CommentTO getById(int id) throws DAOException;

	/**
	 * Gets the list of comments.
	 *
	 * @return the list
	 * @throws DAOException the DAO exception
	 */
	public List<CommentTO> getList() throws DAOException;

	/**
	 * Gets the list by news.
	 *
	 * @param newsId the news id
	 * @return the list by news
	 * @throws DAOException the DAO exception
	 */
	public List<CommentTO> getListByNews(int newsId) throws DAOException;
}