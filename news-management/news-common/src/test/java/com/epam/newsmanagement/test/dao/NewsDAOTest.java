package com.epam.newsmanagement.test.dao;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.epam.newsmanagement.dao.NewsDAO;
import com.epam.newsmanagement.model.NewsTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("classpath:news/newsData.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class NewsDAOTest{
	
	@Autowired
	private NewsDAO newsDAO;

	private NewsTO news;
 
    public NewsDAOTest() throws Exception {
    	Locale.setDefault(Locale.ENGLISH);
    	news = new NewsTO(1, "test short", "test full","test title", getDate(), getDate());
    }
    
    @Test
    @ExpectedDatabase(value="classpath:news/newsDataAdd.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testAdd() throws Exception{
    	int id = 0;
    	id = newsDAO.add(news);
    	assertTrue(id > 0);
    }
    
    @Test
    @ExpectedDatabase(value="classpath:news/newsDataRemove.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testRemove() throws Exception{
    	newsDAO.remove(news.getNewsId());
    }
    
    @Test 
    @ExpectedDatabase(value="classpath:news/newsDataUpdate.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() throws Exception{
    	newsDAO.update(news);
    }
    
    @Test
    public void testGetAll() throws Exception{
    	int count = newsDAO.getNumberNews();
    	List<NewsTO> listNews = newsDAO.getList(0, count);
    	Assert.assertEquals(5,listNews.size());
    	assertEquals(2, listNews.get(0).getNewsId());
    	assertEquals(4, listNews.get(1).getNewsId());
    	assertEquals(1, listNews.get(2).getNewsId());
    	assertEquals(3, listNews.get(3).getNewsId());
    	assertEquals(5, listNews.get(4).getNewsId());
    	assertEquals("short2", listNews.get(0).getShortText());
    	assertEquals("full2", listNews.get(0).getFullText());
    	assertEquals("title2", listNews.get(0).getTitle());
    	assertEquals(Timestamp.valueOf("2014-03-03 09:55:00"), listNews.get(0).getCreationDate());
    	assertEquals(java.sql.Date.valueOf("2014-03-03"), listNews.get(0).getModificationDate());
    }

    @Test 
    public void testGetById() throws Exception{
    	NewsTO actuaclNews = newsDAO.getById(5);
    	NewsTO expectedNews = new NewsTO(5, "short5", "full5", "title5",  getDate(), getDate());
    	assertEquals(expectedNews.getNewsId(), actuaclNews.getNewsId());
    	assertEquals(expectedNews.getShortText(), actuaclNews.getShortText());
    	assertEquals(expectedNews.getFullText(), actuaclNews.getFullText());
    	assertEquals(expectedNews.getTitle(), actuaclNews.getTitle());
    	assertEquals(expectedNews.getCreationDate(), actuaclNews.getCreationDate());
    }
    
    @Test
    public void testSearchByAuthor() throws Exception{
    	int authorId = 1;
    	int size = 3;
    	int count = newsDAO.getNumberNews(authorId);
    	List<NewsTO> listNews = newsDAO.getList(authorId, 0, count);
    	assertEquals(size, listNews.size());
    	assertEquals(1, listNews.get(0).getNewsId());
    	assertEquals(3, listNews.get(1).getNewsId());
    	assertEquals(5, listNews.get(2).getNewsId());
    	assertEquals("short5", listNews.get(2).getShortText());
    	assertEquals("full5", listNews.get(2).getFullText());
    	assertEquals("title5", listNews.get(2).getTitle());
    	assertEquals(Timestamp.valueOf("2015-04-04 09:55:00"), listNews.get(2).getCreationDate());
    	assertEquals(java.sql.Date.valueOf("2015-04-04"), listNews.get(2).getModificationDate());	
    }
    
    @Test
    public void testSearchByTags() throws Exception{
    	int tagId1 = 1;
    	int tagId2 = 2;
    	int size = 3;
    	List<Integer> listId = new ArrayList<Integer>();
    	listId.add(tagId1);
    	listId.add(tagId2);
    	int count = newsDAO.getNumberNews(listId);
    	List<NewsTO> listNews = newsDAO.getList(listId, 0, count);
    	assertEquals(size, listNews.size());
    	assertEquals(2, listNews.get(0).getNewsId());
    	assertEquals(1, listNews.get(1).getNewsId());
    	assertEquals(3, listNews.get(2).getNewsId());
    	assertEquals("short2", listNews.get(0).getShortText());
    	assertEquals("full2", listNews.get(0).getFullText());
    	assertEquals("title2", listNews.get(0).getTitle());
    	assertEquals(Timestamp.valueOf("2014-03-03 09:55:00"), listNews.get(0).getCreationDate());
    	assertEquals(java.sql.Date.valueOf("2014-03-03"), listNews.get(0).getModificationDate());
    	
    }
    
    private Date getDate() throws Exception{
    	String string = "2015-04-04 09:55:00";
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = format.parse(string);
    	return date;
    }
}
