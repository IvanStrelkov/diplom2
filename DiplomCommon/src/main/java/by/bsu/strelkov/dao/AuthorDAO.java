package by.bsu.strelkov.dao;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;

/**
 * The Interface AuthorDAO.
 */
public interface AuthorDAO extends CrudDAO<Author> {

	public Author getAuthorByNews(long news_id) throws DiplomException;
}