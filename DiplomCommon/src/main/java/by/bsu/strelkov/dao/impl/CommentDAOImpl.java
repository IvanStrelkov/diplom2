package by.bsu.strelkov.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.bsu.strelkov.dao.CommentDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Comment;

@Repository
public class CommentDAOImpl extends CrudDAOImpl<Comment> implements CommentDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public CommentDAOImpl() {
		super(Comment.class);
	}

	@Override
	public List<Comment> getListByNews(long news_id) throws DiplomException {
		// TODO Auto-generated method stub
		return null;
	}
}
