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

import com.epam.newsmanagement.dao.NewsDAO;
import com.epam.newsmanagement.dao.UtilsDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.model.NewsTO;

/**
 * The Class NewsDAO.
 */
public class NewsDAOImpl implements NewsDAO{
	
	/** The Constant SQL_ADD. */
	private final static String SQL_ADD = "INSERT INTO NEWS (NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE,"
			+"CREATION_DATE, MODIFICATION_DATE) VALUES (NEWS_SEQ.NEXTVAL, ?,?,?,?,?)";
	
	/** The Constant SQL_DELETE. */
	private final static String SQL_DELETE = "DELETE FROM NEWS WHERE NEWS_ID = ?";
	
	/** The Constant SQL_UPDATE. */
	private final static String SQL_UPDATE = "UPDATE NEWS SET SHORT_TEXT = ?, FULL_TEXT = ?," 
			+ "TITLE = ?, MODIFICATION_DATE = ? WHERE NEWS_ID = ?";
	
	/** The Constant SQL_SELECT_BY_ID. */
	private final static String SQL_SELECT_BY_ID = "SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE,"
			+"CREATION_DATE, MODIFICATION_DATE FROM NEWS WHERE NEWS_ID = ?";
	
	/** The Constant SQL_SELECT. */
	private final static String SQL_LIMIT_SELECT = "SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE"
			+ " FROM ( SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE, ROWNUM RNUM"
			+ " FROM (SELECT NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE"
			+ " FROM NEWS LEFT JOIN COMMENTS ON NEWS.NEWS_ID = COMMENTS.NEWS_ID"
			+ " GROUP BY NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE"
			+ " ORDER BY COUNT(COMMENTS.NEWS_ID) DESC, NEWS.MODIFICATION_DATE DESC)"
			+ " WHERE ROWNUM <= ?)"
			+ " WHERE rnum > ?";
	
	/** The Constant SQL_SEARCH_BY_AUTHOR. */
	private final static String SQL_SEARCH_BY_AUTHOR = "SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE "
			+ "FROM ( SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE, ROWNUM RNUM "
			+ "FROM (SELECT NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, "
			+ "NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE FROM NEWS "
			+ "JOIN NEWS_AUTHOR ON NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ? "
			+ "LEFT JOIN COMMENTS ON NEWS.NEWS_ID = COMMENTS.NEWS_ID "
			+ "GROUP BY NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
			+ "ORDER BY COUNT(COMMENTS.NEWS_ID) DESC, NEWS.MODIFICATION_DATE DESC) "
			+ "WHERE ROWNUM <= ?) "
			+ "WHERE rnum > ?";
	
	/** The Constant SQL_SEARCH_BY_TAGS_1. */
	private final static String SQL_SEARCH_BY_TAGS_1 = "SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE "
			+ "FROM ( SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE, ROWNUM RNUM "
			+ "FROM (SELECT NEWS.NEWS_ID, NEWS.SHORT_TEXT, "
			+ "NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE FROM NEWS "
			+ "JOIN NEWS_TAG ON NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID IN (";
	
	/** The Constant SQL_SEARCH_BY_TAGS_2. */
	private final static String SQL_SEARCH_BY_TAGS_2 = ")"
			+ "LEFT JOIN COMMENTS ON NEWS.NEWS_ID = COMMENTS.NEWS_ID "
			+ "GROUP BY NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
			+ "ORDER BY COUNT(COMMENTS.NEWS_ID) DESC, NEWS.MODIFICATION_DATE DESC) "
			+ "WHERE ROWNUM <= ?) "
			+ "WHERE rnum > ?";
	
	private final static String SQL_SEARCH_BY_AUTHOR_TAGS_1 = "SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE "
			+ "FROM ( SELECT NEWS_ID, SHORT_TEXT, FULL_TEXT, TITLE, CREATION_DATE, MODIFICATION_DATE, ROWNUM RNUM "
			+ "FROM (SELECT NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, "
			+ "NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE FROM NEWS "
			+ "JOIN NEWS_AUTHOR ON NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ? "
			+ "JOIN NEWS_TAG ON NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID IN (";
	
	private final static String SQL_SEARCH_BY_AUTHOR_TAGS_2 = ") "
			+ "LEFT JOIN COMMENTS ON NEWS.NEWS_ID = COMMENTS.NEWS_ID "
			+ "GROUP BY NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
			+ "ORDER BY COUNT(COMMENTS.NEWS_ID) DESC, NEWS.MODIFICATION_DATE DESC) "
			+ "WHERE ROWNUM <= ?) "
			+ "WHERE rnum > ?";
	
	private final static String SQL_GET_COUNT = "SELECT COUNT(*) FROM NEWS";
	
	private final static String SQL_GET_COUNT_AUTHOR = "SELECT COUNT(*) FROM NEWS "
			+ "JOIN NEWS_AUTHOR ON NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ? ";
	
	private final static String SQL_GET_COUNT_TAGS_1 = "SELECT COUNT(*) FROM( "
			+ "SELECT NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
			+ "FROM NEWS "
			+ "JOIN NEWS_TAG ON NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID IN (";
	
	private final static String SQL_GET_COUNT_TAGS_2 = ") "
			+ "GROUP BY NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE)";
	
	private final static String SQL_GET_COUNT_AUTHOR_TAGS_1 = "SELECT COUNT(*) FROM( "
			+ "SELECT NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE "
			+ "FROM NEWS "
			+ "JOIN NEWS_AUTHOR ON NEWS.NEWS_ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = ? "
			+ "JOIN NEWS_TAG ON NEWS.NEWS_ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID IN (";
	
	private final static String SQL_GET_COUNT_AUTHOR_TAGS_2 = ") "
			+ "GROUP BY NEWS.NEWS_ID, NEWS.SHORT_TEXT, NEWS.FULL_TEXT, NEWS.TITLE, NEWS.CREATION_DATE, NEWS.MODIFICATION_DATE)";
	
	
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
	 * @see com.epam.newsmanagement.dao.NewsDAO#add(com.epam.newsmanagement.model.NewsTO)
	 */
	public int add(NewsTO news) throws DAOException {
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			java.sql.Date modificationDate = new java.sql.Date(news.getModificationDate().getTime());
			Timestamp creationDate = new Timestamp(news.getCreationDate().getTime());
			String generatedColumns[] = {"NEWS_ID"}; 
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_ADD,generatedColumns);
			ps.setString(1, news.getShortText());
			ps.setString(2, news.getFullText());
			ps.setString(3, news.getTitle());
			ps.setTimestamp(4, creationDate);
			ps.setDate(5, modificationDate);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	        	news.setNewsId(rs.getInt(1));
	        }else {
	            throw new SQLException("Creating news failed, no ID obtained.");
	        }
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return news.getNewsId();
	}

	/**
	 * @see com.epam.newsmanagement.dao.NewsDAO#remove(int)
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
	 * @see com.epam.newsmanagement.dao.NewsDAO#update(com.epam.newsmanagement.model.NewsTO)
	 */
	public void update(NewsTO news) throws DAOException{
		Connection connection = null;
		PreparedStatement  ps = null;
		try {
			java.sql.Date modificationDate = new java.sql.Date(news.getModificationDate().getTime());
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_UPDATE);
			ps.setString(1, news.getShortText());
			ps.setString(2, news.getFullText());
			ps.setString(3, news.getTitle());
			ps.setDate(4, modificationDate);
			ps.setInt(5, news.getNewsId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, null);
		}
	}

	/**
	 * @see com.epam.newsmanagement.dao.NewsDAO#getById(int)
	 */
	public NewsTO getById(int newsId) throws DAOException {
		NewsTO news = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SELECT_BY_ID);
			ps.setInt(1, newsId);
			rs = ps.executeQuery();
			if(rs.next()){
				news = resultSetToNews(rs);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return news;
	}

	/**
	 * @see com.epam.newsmanagement.dao.NewsDAO#getList()
	 */
	public List<NewsTO> getList(int start, int count) throws DAOException {
		ArrayList<NewsTO> listNews = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_LIMIT_SELECT);
			ps.setInt(1, start + count);
			ps.setInt(2, start);
			rs = ps.executeQuery();
			listNews = new ArrayList<NewsTO>();
			while(rs.next()){
				listNews.add(resultSetToNews(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return listNews;
	}
	
	/**
	 * @see com.epam.newsmanagement.dao.NewsDAO#getListByAuthor(int)
	 */
	public List<NewsTO> getList(int authorId, int start, int count) throws DAOException{
		ArrayList<NewsTO> listNews = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_SEARCH_BY_AUTHOR);
			ps.setInt(1, authorId);
			ps.setInt(2, start + count);
			ps.setInt(3, start);
			rs = ps.executeQuery();
			listNews = new ArrayList<NewsTO>();
			while(rs.next()){
				listNews.add(resultSetToNews(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return listNews;
	}

	/**
	 * @see com.epam.newsmanagement.dao.NewsDAO#getListByTags(java.util.List)
	 */
	public List<NewsTO> getList(List<Integer> listId, int start, int count) throws DAOException{
		ArrayList<NewsTO> listNews = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			StringBuilder searchTags = new StringBuilder(SQL_SEARCH_BY_TAGS_1);
			for(int i = 0; i < listId.size(); i++){
				searchTags.append("?,");
			}
			searchTags.setLength(searchTags.length()-1);
			searchTags.append(SQL_SEARCH_BY_TAGS_2);
			connection = dataSource.getConnection();
			String sqlSearchByTags = searchTags.toString();
			ps = connection.prepareStatement(sqlSearchByTags);
			for(int i = 0; i < listId.size(); i++){
				ps.setInt(i + 1, listId.get(i));
			}
			ps.setInt(listId.size() + 1, start + count);
			ps.setInt(listId.size() + 2, start);
			rs = ps.executeQuery();
			listNews = new ArrayList<NewsTO>();
			while(rs.next()){
				listNews.add(resultSetToNews(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return listNews;
	}
	
	public List<NewsTO> getList(int authorId, List<Integer> listId,
			int start, int count) throws DAOException {
		ArrayList<NewsTO> listNews = null;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			StringBuilder searchTags = new StringBuilder(SQL_SEARCH_BY_AUTHOR_TAGS_1);
			for(int i = 0; i < listId.size(); i++){
				searchTags.append("?,");
			}
			searchTags.setLength(searchTags.length()-1);
			searchTags.append(SQL_SEARCH_BY_AUTHOR_TAGS_2);
			connection = dataSource.getConnection();
			String sqlSearchByAuthorTags = searchTags.toString();
			ps = connection.prepareStatement(sqlSearchByAuthorTags);
			ps.setInt(1, authorId);
			for(int i = 0; i < listId.size(); i++){
				ps.setInt(i + 2, listId.get(i));
			}
			ps.setInt(listId.size() + 2, start + count);
			ps.setInt(listId.size() + 3, start);
			rs = ps.executeQuery();
			listNews = new ArrayList<NewsTO>();
			while(rs.next()){
				listNews.add(resultSetToNews(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return listNews;
	}
	
	public int getNumberNews() throws DAOException{
		int count = 0;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try{
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_GET_COUNT);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return count;
	}
	
	public int getNumberNews(int authorId) throws DAOException{
		int count = 0;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try{
			connection = dataSource.getConnection();
			ps = connection.prepareStatement(SQL_GET_COUNT_AUTHOR);
			ps.setInt(1, authorId);
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		}catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return count;
	}
	
	public int getNumberNews(List<Integer> listId) throws DAOException{
		int count = 0;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			StringBuilder searchTags = new StringBuilder(SQL_GET_COUNT_TAGS_1);
			for(int i = 0; i < listId.size(); i++){
				searchTags.append("?,");
			}
			searchTags.setLength(searchTags.length()-1);
			searchTags.append(SQL_GET_COUNT_TAGS_2);
			connection = dataSource.getConnection();
			String sqlSearchByTags = searchTags.toString();
			ps = connection.prepareStatement(sqlSearchByTags);
			for(int i = 0; i < listId.size(); i++){
				ps.setInt(i + 1, listId.get(i));
			}
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return count;
	}
	
	public int getNumberNews(int authorId, List<Integer> listId) throws DAOException{
		int count = 0;
		Connection connection = null;
		PreparedStatement  ps = null;
		ResultSet rs = null;
		try {
			StringBuilder searchTags = new StringBuilder(SQL_GET_COUNT_AUTHOR_TAGS_1);
			for(int i = 0; i < listId.size(); i++){
				searchTags.append("?,");
			}
			searchTags.setLength(searchTags.length()-1);
			searchTags.append(SQL_GET_COUNT_AUTHOR_TAGS_2);
			connection = dataSource.getConnection();
			String sqlSearchByTags = searchTags.toString();
			ps = connection.prepareStatement(sqlSearchByTags);
			ps.setInt(1, authorId);
			for(int i = 0; i < listId.size(); i++){
				ps.setInt(i + 2, listId.get(i));
			}
			rs = ps.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}finally{
			UtilsDAO.close(connection, ps, rs);
		}
		return count;
	}
	
	/**
	 * Result set to news.
	 *
	 * @param rs the ResultSet
	 * @return the generated news
	 * @throws SQLException the SQL exception
	 */
	private NewsTO resultSetToNews(ResultSet rs) throws SQLException{
		int id = rs.getInt(1);
		String shortText = rs.getString(2);
		String fullText = rs.getString(3);
		String title = rs.getString(4);
		Date creationDate = rs.getTimestamp(5);
		Date modificationDate = rs.getDate(6);
		NewsTO news = new NewsTO(id, shortText, fullText, title, creationDate, modificationDate);
		return news;
	}
}
