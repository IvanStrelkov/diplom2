package com.epam.newsmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.newsmanagement.exception.DAOException;

/**
 * The Class UtilsDAO.
 */
public class UtilsDAO {
	
	/**
	 * Close.
	 *
	 * @param connection the connection
	 * @param statement the statement
	 * @param resultSet the result set
	 * @throws DAOException the DAO exception
	 */
	public static void close(Connection connection, Statement statement, ResultSet resultSet) throws DAOException{
		try{
			try{
				try{
					if(resultSet!= null){
						resultSet.close();
					}
				}finally{
					if(statement != null){
						statement.close();
					}
				}
			}finally{
				if(connection != null){
					connection.close();
				}
			}
		}catch(SQLException e){
			throw new DAOException(e);
		}
	}
}
