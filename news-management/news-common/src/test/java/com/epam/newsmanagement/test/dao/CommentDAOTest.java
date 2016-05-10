package com.epam.newsmanagement.test.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.epam.newsmanagement.dao.CommentDAO;
import com.epam.newsmanagement.model.CommentTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("classpath:comment/commentData.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class CommentDAOTest{
	
	@Autowired
	private CommentDAO commentDAO;

	private CommentTO comment;
 
    public CommentDAOTest() throws Exception{
    	Locale.setDefault(Locale.ENGLISH);
        comment = new CommentTO(1, "test comment", getDate(), 1);
    }
    
    @Test
    @ExpectedDatabase(value="classpath:comment/commentDataAdd.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testAdd() throws Exception{
    	int id = 0;
    	id = commentDAO.add(comment);
    	assertTrue(id > 0);
    }
    
    @Test
    @ExpectedDatabase(value="classpath:comment/commentDataRemove.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testRemove() throws Exception{
    	commentDAO.remove(comment.getCommentId());
    }
    
    @Test 
    @ExpectedDatabase(value="classpath:comment/commentDataUpdate.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() throws Exception{
    	commentDAO.update(comment);
    }

	@Test
    public void testGetAll() throws Exception{
    	List<CommentTO> listComment = commentDAO.getList();
    	assertEquals(4, listComment.size());
    	assertEquals(4, listComment.get(0).getCommentId());
    	assertEquals(3, listComment.get(1).getCommentId());
    	assertEquals(2, listComment.get(2).getCommentId());
    	assertEquals(1, listComment.get(3).getCommentId());
    	assertEquals("Text4", listComment.get(0).getCommentText());
    	assertEquals(Timestamp.valueOf("2012-03-03 04:55:00"), listComment.get(0).getCreationDate());
    	assertEquals(2, listComment.get(0).getNewsId());
    }
    
    @Test 
    public void testGetById() throws Exception{
    	CommentTO actualComment = commentDAO.getById(1);
    	CommentTO expectedComment = new CommentTO(1, "Text1", getDate(), 1);
    	assertEquals(expectedComment.getCommentId(), actualComment.getCommentId());
    	assertEquals(expectedComment.getCommentText(), actualComment.getCommentText());
    	assertEquals(expectedComment.getCreationDate(), actualComment.getCreationDate());
    	assertEquals(expectedComment.getNewsId(), actualComment.getNewsId());
    }
    
    @Test 
    public void testGetListByNews() throws Exception{
    	int newsId = 1;
    	int idComment1 = 1;
    	int idComment2 = 2;
    	int size = 2;
    	List<CommentTO> listComment = commentDAO.getListByNews(newsId);
    	assertEquals(size,listComment.size());
    	assertEquals(idComment2, listComment.get(0).getCommentId());
    	assertEquals(idComment1, listComment.get(1).getCommentId());
      	assertEquals("Text2", listComment.get(0).getCommentText());
    	assertEquals(Timestamp.valueOf("2014-03-03 07:55:00"), listComment.get(0).getCreationDate());
    	assertEquals(1, listComment.get(0).getNewsId());
    }
    
    private Date getDate() throws Exception{
    	String string = "2015-03-03 09:55:00";
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = format.parse(string);
    	return date;
    }
}
