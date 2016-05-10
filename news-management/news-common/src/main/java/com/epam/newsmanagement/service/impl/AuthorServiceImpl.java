package com.epam.newsmanagement.service.impl;

import java.util.List;

import com.epam.newsmanagement.dao.AuthorDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;
import com.epam.newsmanagement.service.AuthorService;

/**
 * The Class AuthorService.
 */
public class AuthorServiceImpl implements AuthorService{

	/** The author dao. */
	private AuthorDAO authorDAO;
	
	/**
	 * Sets the author dao.
	 *
	 * @param authorDAO the author dao
	 */
	public void setAuthorDAO(AuthorDAO authorDAO) {
		this.authorDAO = authorDAO;
	}

	/**
	 * @see com.epam.newsmanagement.service.AuthorService#add(com.epam.newsmanagement.model.AuthorTO)
	 */
	public int add(AuthorTO author) throws ServiceException{
		try {
			authorDAO.add(author);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return author.getAuthorId();
	}

	/**
	 * @return 
	 * @see com.epam.newsmanagement.service.AuthorService#update(com.epam.newsmanagement.model.AuthorTO)
	 */
	public boolean update(AuthorTO author) throws ServiceException{
		boolean result = true;
		try {
			AuthorTO authorCheck = authorDAO.getById(author.getAuthorId());
			if(authorCheck != null){
				authorDAO.update(author);
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
	 * @see com.epam.newsmanagement.service.AuthorService#remove(int)
	 */
	public boolean remove(int id) throws ServiceException{
		boolean result = true;
		try {
			AuthorTO authorCheck = authorDAO.getById(id);
			if(authorCheck != null){
				authorDAO.remove(id);
			}else{
				result = false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}

	/**
	 * @see com.epam.newsmanagement.service.AuthorService#getById(int)
	 */
	public AuthorTO getById(int id) throws ServiceException{
		try {
			return authorDAO.getById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.service.AuthorService#getList()
	 */
	public List<AuthorTO> getList() throws ServiceException{
		try {
			return authorDAO.getList();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * @see com.epam.newsmanagement.service.AuthorService#getAuthorByNews(int)
	 */
	public AuthorTO getAuthorByNews(int newsId) throws ServiceException{
		try {
			return authorDAO.getAuthorByNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * @see com.epam.newsmanagement.service.AuthorService#attachNews(int, int)
	 */
	public void attachNews(int newsId, int authorId) throws ServiceException{
		try{
			authorDAO.attachNews(newsId, authorId);
		}catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.service.AuthorService#removeRelations(int)
	 */
	public void removeRelations(int newsId) throws ServiceException {
		try{
			authorDAO.removeRelations(newsId);
		}catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
