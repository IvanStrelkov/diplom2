package com.epam.newsmanagement.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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

import com.epam.newsmanagement.dao.CommentDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.CommentTO;
import com.epam.newsmanagement.service.impl.CommentServiceImpl;

public class CommentServiceTest {

	@Mock
	private CommentDAO commentDAO;
	private CommentServiceImpl commentService;
	private List<CommentTO> listComments;

	@SuppressWarnings("serial")
	@Before
	public void doSetup() throws Exception {
		MockitoAnnotations.initMocks(this);
		commentService = new CommentServiceImpl();
		commentService.setCommentDAO(commentDAO);
		listComments = new ArrayList<CommentTO>(){{
			add(new CommentTO(1, "Comment1", getDate(), 1));
			add(new CommentTO(2, "Comment2", getDate(), 1));
			add(new CommentTO(3, "Comment3", getDate(), 2));
		}};
	}

	@Test
	public void testAddComment() throws Exception{
		CommentTO comment1 = new CommentTO(0,"comment1", getDate(), 1);
		when(commentDAO.add(any(CommentTO.class))).thenAnswer(new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) {
				comment1.setCommentId(listComments.size()+1);
				listComments.add(comment1);
				return comment1.getCommentId();
			}
		});
		int generatedId = commentService.add(comment1);
		assertEquals(comment1.getCommentId(), generatedId);
		verify(commentDAO).add(comment1);
	}

	@Test(expected = ServiceException.class)
	public void testAddCommentException() throws Exception{
		when(commentDAO.add(any(CommentTO.class))).thenThrow(new DAOException());
		commentService.add(any(CommentTO.class));
	}

	public void testUpdate() throws Exception{
		CommentTO comment = new CommentTO(1,"comment update", getDate(), 1);
		when(commentDAO.getById(1)).thenReturn(comment);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listComments.set(comment.getCommentId(), comment);
				return null;
			}
		}).when(commentDAO).update(comment);
		boolean updated = commentService.update(comment);
		assertTrue(updated);
		verify(commentDAO).getById(1);
		verify(commentDAO).update(comment);
		assertEquals(comment.getCommentText(), listComments.get(1).getCommentText());
		assertEquals(comment.getCreationDate(), listComments.get(1).getCreationDate());
		assertEquals(comment.getNewsId(), listComments.get(1).getNewsId());
	}

	@Test
	public void shouldNotUpdate() throws Exception{
		CommentTO comment = new CommentTO(1,"comment1", getDate(), 1);
		when(commentDAO.getById(1)).thenReturn( null );
		boolean updated = commentService.update(comment);;
		assertFalse( updated );
		verify(commentDAO).getById(1);
		verifyZeroInteractions(commentDAO);
		verifyNoMoreInteractions(commentDAO);
	}

	@Test
	public void testRemove() throws Exception{
		int id = 1;
		CommentTO comment = new CommentTO(1,"comment1", getDate(), 1);
		when(commentDAO.getById(1)).thenReturn(comment);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listComments.remove(id);
				return null;
			}
		}).when(commentDAO).remove(id);
		boolean removed = commentService.remove(id);
		assertTrue(removed);
		verify(commentDAO).getById(id);
		verify(commentDAO).remove(id);
		assertEquals(2, listComments.size());
	}

	@Test
	public void shouldNotRemove() throws Exception{
		when(commentDAO.getById(1)).thenReturn( null );
		boolean removed = commentService.remove(1);
		assertFalse( removed );
		verify(commentDAO).getById(1);
		verifyNoMoreInteractions(commentDAO);
	}

	@Test
	public void testGetComment() throws Exception{
		int id = 1;
		when(commentDAO.getById(id)).thenAnswer(new Answer<CommentTO>() {
			public CommentTO answer(InvocationOnMock invocation) {
				return listComments.get(id);
			}
		});
		CommentTO comment = commentService.getById(id);
		assertEquals(listComments.get(id).getCommentId(), comment.getCommentId());
		assertEquals(listComments.get(id).getCommentText(), comment.getCommentText());
		assertEquals(listComments.get(id).getCreationDate(), comment.getCreationDate());
		assertEquals(listComments.get(id).getNewsId(), comment.getNewsId());
		verify(commentDAO).getById(id);
	}

	@Test(expected=ServiceException.class)
	public void testGetCommentException() throws Exception{
		int id = 1;
		when(commentDAO.getById(id)).thenThrow(new DAOException());
		commentService.getById(id);
	}

	@Test
	public void testGetCommentList() throws Exception{
		int size = 3;
		CommentTO comment2 = new CommentTO(2,"Comment2", getDate(), 1);
		when(commentDAO.getList()).thenAnswer(new Answer<List<CommentTO>>() {
			public List<CommentTO> answer(InvocationOnMock invocation) {
				return listComments;
			}
		});
		List<CommentTO> listComment = commentService.getList();
		CommentTO comment = listComment.get(1);
		assertEquals(size,listComment.size());
		assertEquals(comment2.getCommentId(), comment.getCommentId());
		assertEquals(comment2.getCommentText(), comment.getCommentText());
		assertEquals(comment2.getCreationDate(), comment.getCreationDate());
		assertEquals(comment2.getNewsId(), comment.getNewsId());
		verify(commentDAO).getList();
	}

	@Test
	public void testGetCommentListByNews() throws Exception{
		int size = 2;
		int newsId = 1;
		CommentTO expectedComment = new CommentTO(2,"Comment2", getDate(), 1);
		when(commentDAO.getListByNews(newsId)).thenAnswer(new Answer<List<CommentTO>>() {
			public List<CommentTO> answer(InvocationOnMock invocation) {
				List<CommentTO> listByNewsId = new ArrayList<CommentTO>();
				for(CommentTO comment : listComments){
					if(newsId == comment.getNewsId()){
						listByNewsId.add(comment);
					}
				}
				return listByNewsId;
			}
		});
		List<CommentTO> listComment = commentService.getListByNews(newsId);
		CommentTO comment = listComment.get(1);
		assertEquals(size,listComment.size());
		assertEquals(expectedComment.getCommentId(), comment.getCommentId());
		assertEquals(expectedComment.getCommentText(), comment.getCommentText());
		assertEquals(expectedComment.getCreationDate(), comment.getCreationDate());
		assertEquals(expectedComment.getNewsId(), comment.getNewsId());
		verify(commentDAO).getListByNews(newsId);
	}

	private Date getDate() throws Exception{
		String string = "2015-03-03 09:55:00";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = format.parse(string);
		return date;
	}
}
