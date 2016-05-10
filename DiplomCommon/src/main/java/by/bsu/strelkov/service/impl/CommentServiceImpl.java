package by.bsu.strelkov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.strelkov.dao.CommentDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Comment;
import by.bsu.strelkov.service.CommentService;

@Service
public class CommentServiceImpl extends CrudServiceImpl<Comment> implements CommentService {

	private CommentDAO commentDAO;
	
	@Autowired
	public CommentServiceImpl(CommentDAO commentDAO) {
		super(commentDAO);
		this.commentDAO = commentDAO;
	}

	public List<Comment> getListByNews(long news_id) throws DiplomException {
		return commentDAO.getListByNews(news_id);
	}

}