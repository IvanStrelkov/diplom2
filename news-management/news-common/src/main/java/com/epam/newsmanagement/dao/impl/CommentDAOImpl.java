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

import com.epam.newsmanagement.dao.CommentDAO;
import com.epam.newsmanagement.dao.UtilsDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.CommentTO;

/**
 * The Class CommentDAO.
 */
public class CommentDAOImpl implements CommentDAO{
	
	/** The Constant SQL_ADD. */
	private final static String SQL_ADD = "INSERT INTO COMMENTS (COMMENT_ID, COMMENT_TEXT, "
			+ "CREATION_DATE, NEWS_ID) VALUES (NEWS_SEQ.NEXTVAL, ?, ?, ?)";
	
	/** The Constant SQL_DELETE. */
	private final static String SQL_DELETE = "DELETE FROM COMMENTS WHERE COMMENT_ID = ?";
	
	/** The Constant SQL_UPDATE. */
	private final static String SQL_UPDATE = "UPDATE COMMENTS SET COMMENT_TEXT = ?"
			+"WHERE COMMENT_ID = ?";
	
	/** The Constant SQL_SELECT_BY_ID. */
	private final static String SQL_SELECT_BY_ID = "SELECT COMMENT_ID, COMMENT_TEXT, "
			+ "CREATION_DATE, NEWS_ID FROM COMMENTS WHERE COMMENT_ID = ?";
	
	/** The Constant SQL_SELECT. */
	private final static String SQL_SELECT = "SELECT COMMENT_ID, COMMENT_TEXT, CREATION_DATE, "
			+ "NEWS_ID FROM COMMENTS ORDER BY CREATION_DATE";
	
	/** The Constant SQL_SELECT_BY_NEWS. */
	private final static String SQL_SELECT_BY_NEWS = "SELECT COMMENT_ID, COMMENT_TEXT, CREATION_DATE, "
			+ "NEWS_ID FROM COMMENTS WHERE NEWS_ID = ? ORDER BY CREATION_DATE";
	
	/** The data source. */
	private DataSource dataSource;
	 
	/**
	 * Sets the data source.
	 *
	 * @param dataSource the data source
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * @see com.epam.newsmanagement.dao.CommentDAO#add(com.epam.newsmanagement.model.CommentTO)
	 */
	public int add(CommentTO comment) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			Timestamp creationDate = new Timestamp(comment.getCreationDate().getTime());
			String generatedColumns[] = {"COMMENT_ID"}; 
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_ADD,generatedColumns);
			ps.setString(1, comment.getCommentText());
			ps.setTimestamp(2, creationDate);
			ps.setInt(3, comment.getNewsId());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	        	comment.setCommentId(rs.getInt(1));
	        }else {
	            throw new SQLException("Creating comment failed, no ID obtained.");
	        }
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return comment.getCommentId();
	}

	/**
	 * @see com.epam.newsmanagement.dao.CommentDAO#remove(int)
	 */
	public void remove(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_DELETE);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}

	/**
	 * @see com.epam.newsmanagement.dao.CommentDAO#update(com.epam.newsmanagement.model.CommentTO)
	 */
	public void update(CommentTO comment) throws DAOException{
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_UPDATE);
			ps.setString(1, comment.getCommentText());
			ps.setInt(2, comment.getCommentId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}

	/**
	 * @see com.epam.newsmanagement.dao.CommentDAO#getById(int)
	 */
	public CommentTO getById(int id) throws DAOException {
		CommentTO comment = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SELECT_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				comment = resultSetToComment(rs);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return comment;
	}

	/**
	 * @see com.epam.newsmanagement.dao.impl.CommentDAO#getList()
	 */
	public List<CommentTO> getList() throws DAOException {
		ArrayList<CommentTO> listComment = new ArrayList<CommentTO>();
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			while(rs.next()){
				listComment.add(resultSetToComment(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return listComment;
	}

	/**
	 * @see com.epam.newsmanagement.dao.CommentDAO#getListByNews(int)
	 */
	public List<CommentTO> getListByNews(int newsId) throws DAOException{
		ArrayList<CommentTO> listComment = null;
		Connection connection = null;
		PreparedStatement  st = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			st = connection.prepareStatement(SQL_SELECT_BY_NEWS);
			st.setInt(1, newsId);
			rs = st.executeQuery();
			listComment = new ArrayList<CommentTO>();
			while(rs.next()){
				listComment.add(resultSetToComment(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, st, rs);
		}
		return listComment;
	}
	
	/**
	 * Result set to comment.
	 *
	 * @param rs the ResultSet
	 * @return the generated comment
	 * @throws SQLException the SQL exception
	 */
	private CommentTO resultSetToComment(ResultSet rs) throws SQLException{
		int id = rs.getInt(1);
		String commentText = rs.getString(2);
		Date creationDate= rs.getTimestamp(3);
		int newsId = rs.getInt(4);
		CommentTO comment = new CommentTO(id, commentText, creationDate, newsId);
		return comment;
	}
}
