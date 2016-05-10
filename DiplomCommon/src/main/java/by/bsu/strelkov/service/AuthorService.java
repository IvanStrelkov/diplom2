package by.bsu.strelkov.service;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;

/**
 * The Interface AuthorService.
 */
public interface AuthorService extends CrudService<Author> {

	public Author getAuthorByNews(long news_id) throws DiplomException;

}