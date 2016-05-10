package by.bsu.strelkov.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.bsu.strelkov.dao.AuthorDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;

@Repository
public class AuthorDAOImpl extends CrudDAOImpl<Author> implements AuthorDAO{
	
	@Autowired
	private SessionFactory sessionFactory;

	public AuthorDAOImpl() {
		super(Author.class);
	}

	@Override
	public Author getAuthorByNews(long news_id) throws DiplomException {
		// TODO Auto-generated method stub
		return null;
	}
}
