package com.epam.newsmanagement.test.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.epam.newsmanagement.dao.NewsDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.NewsTO;
import com.epam.newsmanagement.service.impl.NewsServiceImpl;

public class NewsServiceTest {

	@Mock
	private NewsDAO newsDAO;
	private NewsServiceImpl newsService;
	private List<NewsTO> listNews;

	@SuppressWarnings("serial")
	@Before
	public void doSetup() throws Exception {
		MockitoAnnotations.initMocks(this);
		newsService = new NewsServiceImpl();
		newsService.setNewsDAO(newsDAO);
		listNews = new ArrayList<NewsTO>(){{
			add(new NewsTO(1, "Short1", "Full1", "Title1", getDate(), getDate()));
			add(new NewsTO(2, "Short2", "Full2", "Title2", getDate(), getDate()));
			add(new NewsTO(3, "Short3", "Full3", "Title3", getDate(), getDate()));
		}};
	}

	@Test
	public void testAddNews() throws Exception{
		int newsId = 4;
		NewsTO news1 = new NewsTO(1,"shortText1","fullText1","title1", getDate(), getDate());
		when(newsDAO.add(any(NewsTO.class))).thenAnswer(new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) {
				news1.setNewsId(listNews.size()+1);
				listNews.add(news1);
				return news1.getNewsId();
			}
		});
		int generatedId = newsService.add(news1);
		assertEquals(newsId, generatedId);
		verify(newsDAO).add(news1);
	}

	@Test(expected = ServiceException.class)
	public void testAddNewExceptions() throws Exception{
		when(newsDAO.add(any(NewsTO.class))).thenThrow(new DAOException());
		newsService.add(any(NewsTO.class));
	}

	public void testUpdate() throws Exception{
		NewsTO news = new NewsTO(1,"shortText1","fullText1","title1", getDate(), getDate());
		when(newsDAO.getById(1)).thenReturn(news);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listNews.set(news.getNewsId(), news);
				return null;
			}
		}).when(newsDAO).update(news);
		boolean updated = newsService.update(news);
		assertTrue(updated);
		verify(newsDAO).getById(1);
		verify(newsDAO).update(news);
		assertEquals(news.getNewsId(), listNews.get(1).getNewsId());
		assertEquals(news.getFullText(), listNews.get(1).getFullText());
		assertEquals(news.getShortText(), listNews.get(1).getShortText());
		assertEquals(news.getTitle(), listNews.get(1).getTitle());
		assertEquals(news.getCreationDate(), listNews.get(1).getCreationDate());
		assertEquals(news.getModificationDate(), listNews.get(1).getModificationDate());
	}

	@Test
	public void shouldNotUpdate() throws Exception{
		NewsTO news = new NewsTO(1,"shortText1","fullText1","title1", getDate(), getDate());
		when(newsDAO.getById(1)).thenReturn( null );
		boolean updated = newsService.update(news);;
		assertFalse(updated);
		verify(newsDAO).getById(1);
		verifyZeroInteractions(newsDAO);
		verifyNoMoreInteractions(newsDAO);
	}

	@Test
	public void testRemove() throws Exception{
		int id = 1;
		NewsTO news = new NewsTO(1,"shortText1","fullText1","title1", getDate(), getDate());
		when(newsDAO.getById(1)).thenReturn(news);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listNews.remove(id);
				return null;
			}
		}).when(newsDAO).remove(id);
		boolean removed = newsService.remove(id);
		verify(newsDAO).getById(id);
		verify(newsDAO).remove(id);
		assertTrue(removed);
		assertEquals(2, listNews.size());
	}

	@Test
	public void shouldNotRemove() throws Exception{
		when(newsDAO.getById(1)).thenReturn(null);
		boolean removed = newsService.remove(1);
		assertFalse( removed );
		verify(newsDAO).getById(1);
		verifyNoMoreInteractions(newsDAO);
	}

	@Test
	public void testGetNewsList() throws Exception{
		int start = 0;
		int count = 1;
		when(newsDAO.getList(start, count)).thenAnswer(new Answer<List<NewsTO>>() {
			public List<NewsTO> answer(InvocationOnMock invocation) {
				return listNews;
			}
		});
		List<NewsTO> generatedList = newsService.getList(start, count);
		NewsTO news = generatedList.get(1);
		assertEquals(3, generatedList.size());
		assertEquals(2, news.getNewsId());
		assertEquals("Short2", news.getShortText());
		assertEquals("Full2", news.getFullText());
		assertEquals("Title2", news.getTitle());
		assertEquals(getDate(), news.getCreationDate());
		assertEquals(getDate(), news.getModificationDate());
		verify(newsDAO).getList(start, count);
	}

	@Test
	public void testGetNews() throws Exception{
		int newsId = 1;
		when(newsDAO.getById(any(Integer.class))).thenAnswer(new Answer<NewsTO>() {
			public NewsTO answer(InvocationOnMock invocation) {
				return listNews.get(newsId);
			}
		});
		NewsTO news = newsService.getById(newsId);
		assertEquals(2, news.getNewsId());
		assertEquals("Short2", news.getShortText());
		assertEquals("Full2", news.getFullText());
		assertEquals("Title2", news.getTitle());
		assertEquals(getDate(), news.getCreationDate());
		assertEquals(getDate(), news.getModificationDate());
		verify(newsDAO).getById(newsId);
	}

	@Test
	public void testGetNewsListByAuthor() throws Exception{
		int authorId = 1;
		int start = 0;
		int count = 0;
		when(newsDAO.getList(authorId, start, count)).thenAnswer(new Answer<List<NewsTO>>() {
			public List<NewsTO> answer(InvocationOnMock invocation) {
				return listNews;
			}
		});
		List<NewsTO> listNews = newsService.getList(authorId, start, count);	 
		NewsTO news = listNews.get(1);
		assertEquals(2, news.getNewsId());
		assertEquals("Short2", news.getShortText());
		assertEquals("Full2", news.getFullText());
		assertEquals("Title2", news.getTitle());
		assertEquals(getDate(), news.getCreationDate());
		assertEquals(getDate(), news.getModificationDate());
		verify(newsDAO).getList(authorId, start, count);
	}

	@Test
	public void testGetNewsListByTags() throws Exception{
		int start = 0;
		int count = 1;
		List<Integer> listId = new ArrayList<Integer>();
		listId.add(1);
		listId.add(2);
		when(newsDAO.getList(listId, start, count)).thenAnswer(new Answer<List<NewsTO>>() {
			public List<NewsTO> answer(InvocationOnMock invocation) {
				return listNews;
			}
		});
		List<NewsTO> list = newsService.getList(listId, start, count);
		NewsTO news = list.get(1);
		assertEquals(2, news.getNewsId());
		assertEquals("Short2", news.getShortText());
		assertEquals("Full2", news.getFullText());
		assertEquals("Title2", news.getTitle());
		assertEquals(getDate(), news.getCreationDate());
		assertEquals(getDate(), news.getModificationDate());
		verify(newsDAO).getList(listId, start, count);
	}
	
	private Date getDate() throws Exception{
		String string = "2015-03-03 09:55:00";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(string);
		return date;
	}
}
