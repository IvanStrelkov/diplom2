package by.bsu.strelkov.service;

import java.util.List;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Comment;

/**
 * The Interface CommentService.
 */
public interface CommentService extends CrudService<Comment> {

	public List<Comment> getListByNews(long news_id) throws DiplomException;

}