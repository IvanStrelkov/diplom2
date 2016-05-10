package com.epam.newsmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.epam.newsmanagement.dao.AuthorDAO;
import com.epam.newsmanagement.dao.UtilsDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.AuthorTO;

/**
 * The Class AuthorDAO.
 */
public class AuthorDAOImpl implements AuthorDAO{

	/** The Constant SQL_ADD. */
	private final static String SQL_ADD = "INSERT INTO AUTHOR(AUTHOR_ID, NAME)" 
			+ "VALUES (AUTHOR_SEQ.NEXTVAL, ?)";
	
	/** The Constant SQL_DELETE. */
	private final static String SQL_DELETE = "UPDATE AUTHOR SET EXPIRED = ? WHERE AUTHOR_ID = ?";
	
	/** The Constant SQL_UPDATE. */
	private final static String SQL_UPDATE = "UPDATE AUTHOR SET NAME = ? WHERE AUTHOR_ID = ?";
	
	/** The Constant SQL_SELECT_BY_ID. */
	private final static String SQL_SELECT_BY_ID = "SELECT AUTHOR_ID, NAME, EXPIRED FROM AUTHOR WHERE AUTHOR_ID = ?";
	
	/** The Constant SQL_SELECT. */
	private final static String SQL_SELECT = "SELECT AUTHOR_ID, NAME, EXPIRED FROM AUTHOR WHERE EXPIRED IS NULL ORDER BY NAME";
	
	/** The Constant SQL_INSERT_RELATIONS. */
	private final static String SQL_INSERT_RELATIONS = "INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID)"
			+"VALUES (?, ?)";
	
	/** The Constant SQL_SELECT_BY_NEWS. */
	private final static String SQL_SELECT_BY_NEWS = "SELECT AUTHOR.AUTHOR_ID, AUTHOR.NAME, AUTHOR.EXPIRED FROM AUTHOR "
			+ "JOIN NEWS_AUTHOR ON NEWS_AUTHOR.AUTHOR_ID = AUTHOR.AUTHOR_ID AND NEWS_AUTHOR.NEWS_ID = ?";
	
	private final static String SQL_REMOVE_RELATIONS = "DELETE FROM NEWS_AUTHOR WHERE NEWS_ID = ?";
	
	/** The data source. */
	private DataSource dataSource;
	 
	/**
	 * Sets the data source!!.
	 *
	 * @param dataSource the data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#add(com.epam.newsmanagement.model.AuthorTO)
	 */
	public int add(AuthorTO author) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			String generatedColumns[] = {"AUTHOR_ID"}; 
			ps = connection.prepareStatement(SQL_ADD, generatedColumns);
			ps.setString(1, author.getAuthorName());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	        	author.setAuthorId(rs.getInt(1));
	        }else {
	            throw new SQLException("Creating author failed, no ID obtained.");
	        }
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return author.getAuthorId();
	}

	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#remove(int)
	 */
	public void remove(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_DELETE);
			ps.setTimestamp(1, new Timestamp(new Date().getTime()));
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}

	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#update(com.epam.newsmanagement.model.AuthorTO)
	 */
	public void update(AuthorTO author) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_UPDATE);
			ps.setString(1, author.getAuthorName());
			ps.setInt(2, author.getAuthorId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}

	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#getById(int)
	 */
	public AuthorTO getById(int id) throws DAOException {
		AuthorTO author = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SELECT_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				author = resultSetToAuthor(rs);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return author;
	}

	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#getList()
	 */
	public List<AuthorTO> getList() throws DAOException {
		ArrayList<AuthorTO> listAuthor = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			listAuthor = new ArrayList<AuthorTO>();
			while(rs.next()){
				listAuthor.add(resultSetToAuthor(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return listAuthor;
	}

	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#attachNews(int, int)
	 */
	public void attachNews(int newsId, int authorId) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection(); 
			ps = connection.prepareStatement(SQL_INSERT_RELATIONS);
			ps.setInt(1, newsId);
			ps.setInt(2, authorId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#getAuthorByNews(int)
	 */
	public AuthorTO getAuthorByNews(int newsId) throws DAOException{
		AuthorTO author = null;
		Connection connection = null;
		PreparedStatement  st = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			st = connection.prepareStatement(SQL_SELECT_BY_NEWS);
			st.setInt(1, newsId);
			rs = st.executeQuery();
			if(rs.next()){
				author = resultSetToAuthor(rs);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, st, rs);
		}
		return author;
	}
	
	/**
	 * @see com.epam.newsmanagement.dao.AuthorDAO#removeRelations(int)
	 */
	public void removeRelations(int newsId) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection(); 
			ps = connection.prepareStatement(SQL_REMOVE_RELATIONS);
			ps.setInt(1, newsId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}

	/**
	 * Result set to author.
	 *
	 * @param rs the ResultSet
	 * @return the generated author
	 * @throws SQLException the SQL exception
	 */
	private AuthorTO resultSetToAuthor(ResultSet rs) throws SQLException{
		int id = rs.getInt(1);
		String name = rs.getString(2);
		Date expired = rs.getTimestamp(3);
		AuthorTO author = new AuthorTO(id, name, expired);
		return author;
	}
}
