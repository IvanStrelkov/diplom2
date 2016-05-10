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

import static org.mockito.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;
import com.epam.newsmanagement.model.CommentTO;
import com.epam.newsmanagement.model.NewsTO;
import com.epam.newsmanagement.model.NewsVO;
import com.epam.newsmanagement.model.TagTO;
import com.epam.newsmanagement.service.AuthorService;
import com.epam.newsmanagement.service.CommentService;
import com.epam.newsmanagement.service.NewsService;
import com.epam.newsmanagement.service.TagService;
import com.epam.newsmanagement.service.impl.NewsVOServiceImpl;

public class NewsVOServiceTest {

	private NewsVOServiceImpl newsVOService;
	private NewsVO newsVO;
	private NewsTO news;
	private AuthorTO author;
	private List<TagTO> listTags;
	private List<CommentTO> listComments;
	
	@Mock
	private NewsService newsService;
	@Mock
	private AuthorService authorService;
	@Mock
	private TagService tagService;
	@Mock
	private CommentService commentService;
	
	@SuppressWarnings("serial")
	@Before
	public void doSetup() throws Exception {
		MockitoAnnotations.initMocks(this);
		newsVOService = new NewsVOServiceImpl();
		newsVOService.setAuthorService(authorService);
		newsVOService.setCommentService(commentService);
		newsVOService.setNewsService(newsService);
		newsVOService.setTagService(tagService);
		news = new NewsTO(1, "short", "full", "title", getDate(), getDate());
		author = new AuthorTO(1, "author");
		listTags = new ArrayList<TagTO>(){{
			add(new TagTO(1, "tag"));
		}};
		listComments = new ArrayList<CommentTO>(){{
			add(new CommentTO(1, "comment", getDate(), 1));
		}};
		newsVO = new NewsVO(author, news, listComments, listTags);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAdd() throws Exception{
		when(newsService.add(any(NewsTO.class))).thenReturn(1);
		doNothing().when(authorService).attachNews(any(Integer.class), any(Integer.class));
		doNothing().when(tagService).attachNews(any(Integer.class), any(List.class));
		int generatedId = newsVOService.add(newsVO);
		assertEquals(1, generatedId);
		verify(newsService).add(any(NewsTO.class));
		verify(authorService).attachNews(any(Integer.class), any(Integer.class));
		verify(tagService).attachNews(any(Integer.class), any(List.class));
	}
	
	@Test(expected = ServiceException.class)
	public void testAddException() throws Exception{
		when(newsService.add(any(NewsTO.class))).thenReturn(1);
		doThrow(new ServiceException()).when(authorService).attachNews(any(Integer.class), any(Integer.class));
		newsVOService.add(newsVO);
	}

	@Test
	public void testRemove() throws Exception{
		int newsId = 1;
		doNothing().when(authorService).removeRelations(any(Integer.class));
		doNothing().when(tagService).removeRelations(any(Integer.class));
		when(newsService.remove(any(Integer.class))).thenReturn(true);
		newsVOService.remove(newsId);
		verify(tagService).removeRelations(any(Integer.class));
		verify(authorService).removeRelations(any(Integer.class));
		verify(newsService).remove(any(Integer.class));
	}
	
	@Test(expected = ServiceException.class)
	public void testRemoveException() throws Exception{
		doNothing().when(authorService).removeRelations(any(Integer.class));
		doThrow(new ServiceException()).when(tagService).removeRelations(any(Integer.class));
		when(newsService.remove(any(Integer.class))).thenReturn(true);
		newsVOService.remove(1);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdate() throws Exception{
		when(newsService.update(any(NewsTO.class))).thenReturn(true);
		doNothing().when(tagService).removeRelations(any(Integer.class));
		doNothing().when(tagService).attachNews(any(Integer.class), any(List.class));
		doNothing().when(authorService).removeRelations(any(Integer.class));
		doNothing().when(authorService).attachNews(any(Integer.class), any(Integer.class));
		newsVOService.update(newsVO);
		verify(newsService).update(any(NewsTO.class));
		verify(tagService).removeRelations(any(Integer.class));
		verify(tagService).attachNews(any(Integer.class), any(List.class));
		verify(authorService).removeRelations(any(Integer.class));
		verify(authorService).attachNews(any(Integer.class), any(Integer.class));
	}

	@Test
	public void testGetById() throws Exception{
		int newsId = 1;
		when(newsService.getById(any(Integer.class))).thenReturn(news);
		when(authorService.getAuthorByNews(any(Integer.class))).thenReturn(author);
		when(commentService.getListByNews(any(Integer.class))).thenReturn(listComments);
		when(tagService.getListByNews(any(Integer.class))).thenReturn(listTags);
		NewsVO actualNews = newsVOService.getById(newsId);
		verify(newsService).getById(any(Integer.class));
		verify(authorService).getAuthorByNews(any(Integer.class));
		verify(tagService).getListByNews(any(Integer.class));
		verify(commentService).getListByNews(any(Integer.class));
		assertEquals(newsVO.getNews(), actualNews.getNews());
		assertEquals(newsVO.getAuthor(), actualNews.getAuthor());
		assertEquals(newsVO.getListComments(), actualNews.getListComments());
		assertEquals(newsVO.getListTags(), actualNews.getListTags());
	}

	@SuppressWarnings("serial")
	@Test
	public void testGetList() throws Exception{
		int start = 0;
		int count = 1;
		when(newsService.getList(start, count)).thenReturn(new ArrayList<NewsTO>(){{
			add(news);
		}});
		when(authorService.getAuthorByNews(any(Integer.class))).thenReturn(author);
		when(commentService.getListByNews(any(Integer.class))).thenReturn(listComments);
		when(tagService.getListByNews(any(Integer.class))).thenReturn(listTags);
		List<NewsVO> listNewsVO = newsVOService.getList(0, null, start, count);
		NewsVO actualNews = listNewsVO.get(0);
		verify(newsService).getList(start, count);
		verify(authorService).getAuthorByNews(any(Integer.class));
		verify(tagService).getListByNews(any(Integer.class));
		verify(commentService).getListByNews(any(Integer.class));
		assertEquals(1, listNewsVO.size());
		assertEquals(newsVO.getNews(), actualNews.getNews());
		assertEquals(newsVO.getAuthor(), actualNews.getAuthor());
		assertEquals(newsVO.getListComments(), actualNews.getListComments());
		assertEquals(newsVO.getListTags(), actualNews.getListTags());
	}
	
	private Date getDate() throws Exception{
		String string = "2015-03-03 09:55:00";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(string);
		return date;
	}
}
