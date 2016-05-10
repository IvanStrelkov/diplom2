package com.epam.newsmanagement.service;

import java.util.List;

import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.TagTO;

/**
 * The Interface TagService.
 */
public interface TagService {

	/**
	 * Adds the tag.
	 *
	 * @param tag the tag
	 * @return the generated id
	 * @throws ServiceException the service exception
	 */
	public int add(TagTO tag) throws ServiceException;

	/**
	 * Update tag.
	 *
	 * @param tag the tag
	 * @throws ServiceException the service exception
	 */
	public boolean update(TagTO tag) throws ServiceException;

	/**
	 * Removes the tag.
	 *
	 * @param id the tag's id
	 * @throws ServiceException the service exception
	 */
	public boolean remove(int id) throws ServiceException;

	/**
	 * Gets the tag by id.
	 *
	 * @param id the id
	 * @return the by id
	 * @throws ServiceException the service exception
	 */
	public TagTO getById(int id) throws ServiceException;

	/**
	 * Gets the list.
	 *
	 * @return the list
	 * @throws ServiceException the service exception
	 */
	public List<TagTO> getList() throws ServiceException;

	/**
	 * Gets the list by news.
	 *
	 * @param newsId the news id
	 * @return the list by news
	 * @throws ServiceException the service exception
	 */
	public List<TagTO> getListByNews(int newsId) throws ServiceException;

	/**
	 * Attach news.
	 *
	 * @param newsId the news id
	 * @param listTagsId the list of tag id
	 * @throws ServiceException the service exception
	 */
	public void attachNews(int newsId, List<Integer> listTagsId) throws ServiceException;
	
	/**
	 * Removes the news_tag.
	 *
	 * @param id the news's id
	 * @throws ServiceException the service exception
	 */
	public void removeRelations(int newsId) throws ServiceException;
}