package com.epam.newsmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.newsmanagement.dao.TagDAO;
import com.epam.newsmanagement.dao.UtilsDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.TagTO;

/**
 * The Class TagDAO.
 */
public class TagDAOImpl implements TagDAO{
	
	/** The Constant SQL_ADD. */
	private final static String SQL_ADD = "INSERT INTO TAG(TAG_ID, TAG_NAME)" 
			+ "VALUES (TAG_SEQ.NEXTVAL, ?)";
	
	/** The Constant SQL_DELETE. */
	private final static String SQL_DELETE = "DELETE FROM TAG WHERE TAG_ID = ?";
	
	/** The Constant SQL_UPDATE. */
	private final static String SQL_UPDATE = "UPDATE TAG SET TAG_NAME = ? WHERE TAG_ID = ?";
	
	/** The Constant SQL_SELECT_BY_ID. */
	private final static String SQL_SELECT_BY_ID = "SELECT TAG_ID, TAG_NAME FROM TAG WHERE TAG_ID = ?";
	
	/** The Constant SQL_SELECT. */
	private final static String SQL_SELECT = "SELECT TAG_ID, TAG_NAME FROM TAG ORDER BY TAG_NAME";
	
	/** The Constant SQL_INSERT_RELATIONS. */
	private final static String SQL_INSERT_RELATIONS = "INSERT INTO NEWS_TAG(NEWS_ID, "
			+ "TAG_ID) VALUES (?, ?)";
	
	/** The Constant SQL_SELECT_BY_NEWS. */
	private final static String SQL_SELECT_BY_NEWS = "SELECT TAG.TAG_ID, TAG.TAG_NAME FROM TAG "
			+ "JOIN NEWS_TAG ON NEWS_TAG.TAG_ID = TAG.TAG_ID AND NEWS_TAG.NEWS_ID = ? ORDER BY TAG_NAME";
	
	private final static String SQL_REMOVE_RELATIONS = "DELETE FROM NEWS_TAG WHERE NEWS_ID = ?";
	
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
	 * @see com.epam.newsmanagement.dao.TagDAO#add(com.epam.newsmanagement.model.TagTO)
	 */
	public int add(TagTO tag) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			String generatedColumns[] = {"TAG_ID"}; 
			ps = connection.prepareStatement(SQL_ADD,generatedColumns);
			ps.setString(1, tag.getTagName());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	        	tag.setTagId(rs.getInt(1));
	        }else {
	            throw new SQLException("Creating tag failed, no ID obtained.");
	        }
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return tag.getTagId();
	}

	/**
	 * @see com.epam.newsmanagement.dao.TagDAO#remove(int)
	 */
	public void remove(int tagId) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_DELETE);
			ps.setInt(1, tagId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.dao.TagDAO#update(com.epam.newsmanagement.model.TagTO)
	 */
	public void update(TagTO tag) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_UPDATE);
			ps.setString(1, tag.getTagName());
			ps.setInt(2, tag.getTagId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}

	/**
	 * @see com.epam.newsmanagement.dao.TagDAO#getById(int)
	 */
	public TagTO getById(int id) throws DAOException {
		TagTO tag = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SELECT_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				tag = resultSetToTag(rs);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return tag;
	}

	/**
	 * @see com.epam.newsmanagement.dao.TagDAO#getList()
	 */
	public List<TagTO> getList() throws DAOException {
		ArrayList<TagTO> listTag = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SELECT);
			rs = ps.executeQuery();
			listTag = new ArrayList<TagTO>();
			while(rs.next()){
				listTag.add(resultSetToTag(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return listTag;
	}

	/**
	 * @see com.epam.newsmanagement.dao.TagDAO#attachNews(int, int)
	 */
	public void attachNews(int newsId, List<Integer> listId) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_INSERT_RELATIONS);
			for(int tagId : listId){
				ps.setInt(1, newsId);
				ps.setInt(2, tagId);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}
	
	/**
	 * @see com.epam.newsmanagement.dao.TagDAO#getListByNews(int)
	 */
	public List<TagTO> getListByNews(int newsId) throws DAOException{
		ArrayList<TagTO> listTag = null;
		Connection connection = null;
		PreparedStatement  st = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			st = connection.prepareStatement(SQL_SELECT_BY_NEWS);
			st.setInt(1, newsId);
			rs = st.executeQuery();
			listTag = new ArrayList<TagTO>();
			while(rs.next()){
				listTag.add(resultSetToTag(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, st, rs);
		}
		return listTag;
	}
	
	/**
	 * @see com.epam.newsmanagement.dao.TagDAO#removeRelations(int)
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
	 * Result set to tag.
	 *
	 * @param rs the ResultSet
	 * @return the generated tag
	 * @throws SQLException the SQL exception
	 */
	private TagTO resultSetToTag(ResultSet rs) throws SQLException{
		int id = rs.getInt(1);
		String name = rs.getString(2);
		TagTO tag = new TagTO(id, name);
		return tag;
	}
}