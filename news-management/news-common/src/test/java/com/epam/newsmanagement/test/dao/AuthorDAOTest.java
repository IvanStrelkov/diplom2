package com.epam.newsmanagement.test.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.epam.newsmanagement.dao.AuthorDAO;
import com.epam.newsmanagement.model.AuthorTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("classpath:author/authorData.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class AuthorDAOTest{
	
	@Autowired
	private AuthorDAO authorDAO;
	
	private AuthorTO author;
 
    public AuthorDAOTest() {
    	Locale.setDefault(Locale.ENGLISH);
        author = new AuthorTO(1, "test author");
    }
    
    @Test
    @ExpectedDatabase(value="classpath:author/authorDataAdd.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testAdd() throws Exception{
    	int id = 0;
    	id = authorDAO.add(author);
    	assertTrue(id > 0);
    }
    
    @Test
    public void testRemove() throws Exception{
    	authorDAO.remove(author.getAuthorId());
    	AuthorTO actualAuthor = authorDAO.getById(author.getAuthorId());
    	assertNotNull(actualAuthor);
    }
    
    @Test 
    @ExpectedDatabase(value="classpath:author/authorDataUpdate.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() throws Exception{
    	authorDAO.update(author);
    }
    
    @Test
    public void testGetAll() throws Exception{
    	List<AuthorTO> listAuthor = authorDAO.getList();
    	AuthorTO expectedAuthor = listAuthor.get(0);
    	assertEquals(4, listAuthor.size());
    	assertEquals(1, listAuthor.get(0).getAuthorId());
    	assertEquals(2, listAuthor.get(1).getAuthorId());
    	assertEquals(3, listAuthor.get(2).getAuthorId());
    	assertEquals(4, listAuthor.get(3).getAuthorId());
    	assertEquals("author1", expectedAuthor.getAuthorName());
    }
    
    @Test
    public void testGetById() throws Exception{
    	AuthorTO actualAuthor = authorDAO.getById(1);
    	AuthorTO expectedAuthor = new AuthorTO(1, "author1");
    	assertEquals(expectedAuthor.getAuthorId(), actualAuthor.getAuthorId());
    	assertEquals(expectedAuthor.getAuthorName(), actualAuthor.getAuthorName());
    }
    
    @Test 
    @ExpectedDatabase(value="classpath:author/authorDataAttachNews.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testAttachNews() throws Exception{
    	int newsId = 1;
    	int authorId = 4;
    	authorDAO.attachNews(newsId, authorId);
    }
    
    @Test 
    public void testGetAuthorByNews() throws Exception{
    	int newsId = 1;
    	AuthorTO author = authorDAO.getAuthorByNews(newsId);
    	assertEquals(1, author.getAuthorId());
    	assertEquals("author1", author.getAuthorName());
    }
    
    @Test
    @ExpectedDatabase(value="classpath:author/authorDataRemoveRelation.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testRemoveRelations() throws Exception{
    	int newsId = 1;
    	authorDAO.removeRelations(newsId);
    }
}
