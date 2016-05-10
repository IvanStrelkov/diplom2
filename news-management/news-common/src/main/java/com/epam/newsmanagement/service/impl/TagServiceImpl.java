package com.epam.newsmanagement.service.impl;

import java.util.List;

import com.epam.newsmanagement.dao.TagDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.TagTO;
import com.epam.newsmanagement.service.TagService;

/**
 * The Class TagService.
 */
public class TagServiceImpl implements TagService{
	
	/** The tag dao. */
	private TagDAO tagDAO;
	
	/**
	 * Sets the tag dao.
	 *
	 * @param tagDAO the tag dao
	 */
	public void setTagDAO(TagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	/**
	 * @see com.epam.newsmanagement.service.TagService#add(com.epam.newsmanagement.model.TagTO)
	 */
	public int add(TagTO tag) throws ServiceException{
		try {
			tagDAO.add(tag);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return tag.getTagId();
	}
	
	/**
	 * @return 
	 * @see com.epam.newsmanagement.service.TagService#update(com.epam.newsmanagement.model.TagTO)
	 */
	public boolean update(TagTO tag) throws ServiceException{
		boolean result = true;
		try {
			TagTO tagCheck = tagDAO.getById(tag.getTagId());
			if(tagCheck != null){
				tagDAO.update(tag);
			}else{
				result = false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * @return 
	 * @see com.epam.newsmanagement.service.TagService#remove(int)
	 */
	public boolean remove(int id) throws ServiceException{
		boolean result = true;
		try {
			TagTO tagCheck = tagDAO.getById(id);
			if(tagCheck != null){
				tagDAO.remove(id);;
			}else{
				result = false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}
	
	/**
	 * @see com.epam.newsmanagement.service.TagService#getById(int)
	 */
	public TagTO getById(int id) throws ServiceException{
		TagTO tag = null;
		try {
			tag =  tagDAO.getById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return tag;
	}
	
	/**
	 * @see com.epam.newsmanagement.service.TagService#getList()
	 */
	public List<TagTO> getList() throws ServiceException{
		List<TagTO> listTag = null;
		try {
			listTag = tagDAO.getList();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return listTag;
	}
	
	/**
	 * @see com.epam.newsmanagement.service.TagService#getListByNews(int)
	 */
	public List<TagTO> getListByNews(int newsId) throws ServiceException{
		try {
			return tagDAO.getListByNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.service.TagService#attachNews(int, int)
	 */
	public void attachNews(int newsId, List<Integer> listTagsId) throws ServiceException{
		try{
			tagDAO.attachNews(newsId, listTagsId);
		}catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * @see com.epam.newsmanagement.service.TagService#removeRelations(int)
	 */
	public void removeRelations(int newsId) throws ServiceException {
		try{
			tagDAO.removeRelations(newsId);
		}catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
