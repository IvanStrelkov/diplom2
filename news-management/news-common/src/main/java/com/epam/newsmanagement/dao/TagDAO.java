package com.epam.newsmanagement.dao;

import java.util.List;

import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.TagTO;

/**
 * The Interface TagDAO.
 */
public interface TagDAO {

	/**
	 * Add tag to table.
	 *
	 * @param tag the tag to be added
	 * @return the generated id
	 * @throws DAOException the DAO exception
	 */
	public int add(TagTO tag) throws DAOException;

	/**
	 * Removes the tag by id from table.
	 *
	 * @param id the tag's id
	 * @throws DAOException the DAO exception
	 */
	public void remove(int tagId) throws DAOException;

	/**
	 * Update tag in table.
	 *
	 * @param tag the tag
	 * @throws DAOException the DAO exception
	 */
	public void update(TagTO tag) throws DAOException;

	/**
	 * Gets tag by id.
	 *
	 * @param id the tag's id
	 * @return tag
	 * @throws DAOException the DAO exception
	 */
	public TagTO getById(int id) throws DAOException;

	/**
	 * Gets the list of tags.
	 *
	 * @return the list
	 * @throws DAOException the DAO exception
	 */
	public List<TagTO> getList() throws DAOException;

	/**
	 * Attach news to tag.
	 *
	 * @param newsId the news id
	 * @param tagId the tag id
	 * @throws DAOException the DAO exception
	 */
	public void attachNews(int newsId, List<Integer> listTagsId) throws DAOException;

	/**
	 * Gets the list by news.
	 *
	 * @param newsId the news id
	 * @return the list by news
	 * @throws DAOException the DAO exception
	 */
	public List<TagTO> getListByNews(int newsId) throws DAOException;

	/**
	 * Removes the news & tag relations by news id from table.
	 *
	 * @param id the tag's id
	 * @throws DAOException the DAO exception
	 */
	public void removeRelations(int newsId) throws DAOException;
}