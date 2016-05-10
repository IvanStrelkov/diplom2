package by.bsu.strelkov.dao;

import java.util.List;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Comment;

/**
 * The Interface CommentDAO.
 */
public interface CommentDAO extends CrudDAO<Comment> {

	public List<Comment> getListByNews(long news_id) throws DiplomException;
}