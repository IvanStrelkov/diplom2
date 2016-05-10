package com.epam.newsmanagement.service.impl;

import java.util.List;

import com.epam.newsmanagement.dao.CommentDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.CommentTO;
import com.epam.newsmanagement.service.CommentService;

/**
 * The Class CommentService.
 */
public class CommentServiceImpl implements CommentService{
	
	/** The comment dao. */
	private CommentDAO commentDAO;

	/**
	 * Sets the comment dao.
	 *
	 * @param commentDAO the comment dao
	 */
	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	/**
	 * @see com.epam.newsmanagement.service.CommentService#add(com.epam.newsmanagement.model.CommentTO)
	 */
	public int add(CommentTO comment) throws ServiceException{
		try {
			commentDAO.add(comment);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return comment.getCommentId();
	}

	/**
	 * @return 
	 * @see com.epam.newsmanagement.service.CommentService#update(com.epam.newsmanagement.model.CommentTO)
	 */
	public boolean update(CommentTO comment) throws ServiceException{
		boolean result = true;
		try {
			CommentTO commentCheck = commentDAO.getById(comment.getCommentId());
			if(commentCheck != null){
				commentDAO.update(comment);	
			}else{
				result = false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}

	/**
	 * @see com.epam.newsmanagement.service.CommentService#remove(int)
	 */
	public boolean remove(int id) throws ServiceException{
		boolean result = true;
		try {
			CommentTO commentCheck = commentDAO.getById(id);
			if(commentCheck != null){
				commentDAO.remove(id);	
			}else{
				result = false;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		return result;
	}

	/**
	 * @see com.epam.newsmanagement.service.CommentService#getById(int)
	 */
	public CommentTO getById(int id) throws ServiceException{
		try {
			return commentDAO.getById(id);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * @see com.epam.newsmanagement.service.CommentService#getList()
	 */
	public List<CommentTO> getList() throws ServiceException{
		try {
			return commentDAO.getList();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * @see com.epam.newsmanagement.service.CommentService#getListByNews(int)
	 */
	public List<CommentTO> getListByNews(int newsId) throws ServiceException{
		try {
			return commentDAO.getListByNews(newsId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}