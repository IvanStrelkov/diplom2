package com.epam.newsmanagement.test.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.epam.newsmanagement.dao.AuthorDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;
import com.epam.newsmanagement.service.impl.AuthorServiceImpl;

public class AuthorServiceTest {

	@Mock
	private AuthorDAO authorDAO;
	private AuthorServiceImpl authorService;
	private List<AuthorTO> listAuthors;

	@SuppressWarnings("serial")
	@Before
	public void doSetup() throws Exception {
		MockitoAnnotations.initMocks(this);
		authorService = new AuthorServiceImpl();
		authorService.setAuthorDAO(authorDAO);
		listAuthors = new ArrayList<AuthorTO>(){{
			add(new AuthorTO(1, "Author1"));
			add(new AuthorTO(2, "Author2"));
			add(new AuthorTO(3, "Author3"));
		}};
	}

	@Test
	public void testAddTag() throws Exception{
		int authorId = 4;
		AuthorTO author = new AuthorTO(0, "Author4");
		when(authorDAO.add(any(AuthorTO.class))).thenAnswer(new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) {
				author.setAuthorId(listAuthors.size()+1);
				listAuthors.add(author);
				return author.getAuthorId();
			}
		});
		int generatedId = authorService.add(author);
		assertEquals(authorId, generatedId);
		verify(authorDAO).add(author);
	}

	@Test(expected = ServiceException.class)
	public void testAddTagException() throws Exception{
		when(authorDAO.add(any(AuthorTO.class))).thenThrow(new DAOException());
		authorService.add(any(AuthorTO.class));
	}

	@Test
	public void testUpdate() throws Exception{
		int id = 1;
		AuthorTO author = new AuthorTO(id, "author Update");
		when(authorDAO.getById(1)).thenReturn(author);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listAuthors.set(author.getAuthorId(), author);
				return null;
			}
		}).when(authorDAO).update(author);
		boolean updated = authorService.update(author);
		assertTrue(updated);
		assertEquals(author.getAuthorName(), listAuthors.get(id).getAuthorName());
		verify(authorDAO).getById(1);
		verify(authorDAO).update(author);
	}

	@Test
	public void shouldNotUpdate() throws Exception{
		AuthorTO author = new AuthorTO(1, "author1");
		when(authorDAO.getById(1)).thenReturn( null );
		boolean updated = authorService.update(author);;
		assertFalse( updated );
		verify(authorDAO).getById(1);
		verifyZeroInteractions(authorDAO);
		verifyNoMoreInteractions(authorDAO);
	}

	@Test(expected = ServiceException.class)
	public void testUpdateTagException() throws Exception{
		AuthorTO author = new AuthorTO(1, "author1");
		when(authorDAO.getById(1)).thenThrow(new DAOException());
		authorService.update(author);
	}

	@Test
	public void testRemove() throws Exception{
		int id = 1;
		AuthorTO author = new AuthorTO(1, "author1");
		when(authorDAO.getById(1)).thenReturn(author);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listAuthors.remove(id);
				return null;
			}
		}).when(authorDAO).remove(id);
		boolean removed = authorService.remove(id);
		assertTrue(removed);
		verify(authorDAO).getById(id);
		verify(authorDAO).remove(id);
		assertEquals(2, listAuthors.size());
	}

	@Test
	public void shouldNotRemove() throws Exception{
		when(authorDAO.getById(1)).thenReturn( null );
		boolean removed = authorService.remove(1);
		assertFalse( removed );
		verify(authorDAO).getById(1);
		verifyNoMoreInteractions(authorDAO);
	}

	@Test
	public void testGetAuthor() throws Exception{
		int id = 1;
		AuthorTO author1 = new AuthorTO(2,"Author2");
		when(authorDAO.getById(id)).thenAnswer(new Answer<AuthorTO>() {
			public AuthorTO answer(InvocationOnMock invocation) {
				return listAuthors.get(id);
			}
		});
		AuthorTO author = authorService.getById(id);
		assertEquals(author1.getAuthorId(), author.getAuthorId());
		assertEquals(author1.getAuthorName(), author.getAuthorName());
		verify(authorDAO).getById(id);
	}

	@Test(expected = ServiceException.class)
	public void testGetAuthorExceprion() throws Exception{
		when(authorDAO.getById(1)).thenThrow(new DAOException());
		authorService.getById(1);
	}

	@Test
	public void testGetAuthorList() throws Exception{
		int size = 3;
		when(authorDAO.getList()).thenAnswer(new Answer<List<AuthorTO>>() {
			public List<AuthorTO> answer(InvocationOnMock invocation) {
				return listAuthors;
			}
		});
		List<AuthorTO> listAuthor = authorService.getList();
		AuthorTO author = listAuthor.get(1);
		assertEquals(size,listAuthor.size());
		assertEquals(2, author.getAuthorId());
		assertEquals("Author2", author.getAuthorName());
		verify(authorDAO).getList();
	}

	@Test
	public void testGetListByNews() throws Exception{
		int newsId = 1;
		AuthorTO author1 = new AuthorTO(1,"author1");
		when(authorDAO.getAuthorByNews(newsId)).thenReturn(author1);
		AuthorTO authorActual = authorService.getAuthorByNews(newsId);
		assertEquals(author1.getAuthorId(), authorActual.getAuthorId());
		assertEquals(author1.getAuthorName(), authorActual.getAuthorName());
		verify(authorDAO).getAuthorByNews(newsId);
	}

	@Test
	public void testAttachNews() throws Exception{
		int newsId = 1;
		int authorId = 1;
		doNothing().when(authorDAO).attachNews(newsId, authorId);
		authorService.attachNews(newsId, authorId);
		verify(authorDAO).attachNews(newsId, authorId);
	}

	@Test
	public void testRemoveRelations() throws Exception{
		int newsId = 1;
		doNothing().when(authorDAO).removeRelations(newsId);;
		authorService.removeRelations(newsId);
		verify(authorDAO).removeRelations(newsId);
	}

	@Test(expected = ServiceException.class)
	public void testRemoveRelationsException() throws Exception{
		int newsId = 1;
		doThrow(new DAOException()).when(authorDAO).removeRelations(newsId);;
		authorService.removeRelations(newsId);
		verify(authorDAO).removeRelations(newsId);
	}
}
