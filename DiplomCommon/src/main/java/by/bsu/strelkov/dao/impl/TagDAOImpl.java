package by.bsu.strelkov.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.bsu.strelkov.dao.TagDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Tag;

@Repository
public class TagDAOImpl extends CrudDAOImpl<Tag> implements TagDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public TagDAOImpl() {
		super(Tag.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Tag> getListByNews(int newsId) throws DiplomException {
		// TODO Auto-generated method stub
		return null;
	}
}