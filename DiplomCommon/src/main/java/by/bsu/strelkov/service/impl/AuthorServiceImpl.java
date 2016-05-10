package by.bsu.strelkov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.strelkov.dao.AuthorDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;
import by.bsu.strelkov.service.AuthorService;

@Service
public class AuthorServiceImpl extends CrudServiceImpl<Author> implements AuthorService {

	private AuthorDAO authorDAO;
	
	@Autowired
	public AuthorServiceImpl(AuthorDAO authorDAO) {
		super(authorDAO);
		this.authorDAO = authorDAO;
	}

	public Author getAuthorByNews(long news_id) throws DiplomException {
		return authorDAO.getAuthorByNews(news_id);
	}

}