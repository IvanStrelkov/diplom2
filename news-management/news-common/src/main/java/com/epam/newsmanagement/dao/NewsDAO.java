package com.epam.newsmanagement.dao;

import java.util.List;

import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.NewsTO;

/**
 * The Interface NewsDAO.
 */
public interface NewsDAO {

	/**
	 * Add news to table.
	 *
	 * @param news the news to be added
	 * @return the generated id
	 * @throws DAOException the DAO exception
	 */
	public int add(NewsTO news) throws DAOException;

	/**
	 * Removes the news by id from table.
	 *
	 * @param id the news's id
	 * @throws DAOException the DAO exception
	 */
	public void remove(int id) throws DAOException;

	/**
	 * Update news in table.
	 *
	 * @param news the news
	 * @throws DAOException the DAO exception
	 */
	public void update(NewsTO news) throws DAOException;

	/**
	 * Gets news by id.
	 *
	 * @param id the news's id
	 * @return news
	 * @throws DAOException the DAO exception
	 */
	public NewsTO getById(int newsId) throws DAOException;

	/**
	 * Gets the list of news.
	 *
	 * @return the list
	 * @throws DAOException the DAO exception
	 */
	public List<NewsTO> getList(int start, int count) throws DAOException;

	/**
	 * Gets the list by author.
	 *
	 * @param authorId the author id
	 * @return the list of news by author
	 * @throws DAOException the DAO exception
	 */
	public List<NewsTO> getList(int authorId, int start, int count) throws DAOException;

	/**
	 * Gets the list by tags.
	 *
	 * @param listId the list id
	 * @return the list of news by tags
	 * @throws DAOException the DAO exception
	 */
	public List<NewsTO> getList(List<Integer> listId, int start, int count) throws DAOException;
	
	public List<NewsTO> getList(int authorId, List<Integer> listId, int start, int count) throws DAOException;
	
	public int getNumberNews() throws DAOException;
	
	public int getNumberNews(int authorId) throws DAOException;
	
	public int getNumberNews(List<Integer> listId) throws DAOException;
	
	public int getNumberNews(int authorId, List<Integer> listId) throws DAOException;

}