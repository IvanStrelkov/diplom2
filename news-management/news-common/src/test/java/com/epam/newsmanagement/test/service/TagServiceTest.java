package com.epam.newsmanagement.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.epam.newsmanagement.dao.TagDAO;
import com.epam.newsmanagement.exception.DAOException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.TagTO;
import com.epam.newsmanagement.service.impl.TagServiceImpl;

public class TagServiceTest {
	@Mock
	private TagDAO tagDAO;
	private TagServiceImpl tagService;
	private List<TagTO> listTags;

	@SuppressWarnings("serial")
	@Before
	public void doSetup() throws Exception {
		MockitoAnnotations.initMocks(this);
		tagService = new TagServiceImpl();
		tagService.setTagDAO(tagDAO);
		listTags = new ArrayList<TagTO>(){{
			add(new TagTO(1, "Tag1"));
			add(new TagTO(2, "Tag2"));
			add(new TagTO(3, "Tag3"));
		}};
	}

	@Test
	public void testAddTag() throws Exception{
		int tagId = 4;
		TagTO tag = new TagTO(0, "Tag4");
		when(tagDAO.add(any(TagTO.class))).thenAnswer(new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) {
				tag.setTagId(listTags.size()+1);
				listTags.add(tag);
				return tag.getTagId();
			}
		});
		int generatedId = tagService.add(tag);
		assertEquals(tagId, generatedId);
		verify(tagDAO).add(tag);
	}

	@Test(expected = ServiceException.class)
	public void testAddTagException() throws Exception{
		when(tagDAO.add(any(TagTO.class))).thenThrow(new DAOException());
		tagService.add(any(TagTO.class));
	}

	@Test
	public void testUpdate() throws Exception{
		int id = 1;
		TagTO tag = new TagTO(id, "tag Update");
		when(tagDAO.getById(1)).thenReturn(tag);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listTags.set(tag.getTagId(), tag);
				return null;
			}
		}).when(tagDAO).update(tag);
		boolean updated = tagService.update(tag);
		assertTrue(updated);
		assertEquals(tag.getTagName(), listTags.get(id).getTagName());
		verify(tagDAO).getById(1);
		verify(tagDAO).update(tag);
	}

	@Test
	public void shouldNotUpdate() throws Exception{
		TagTO tag = new TagTO(1, "tag1");
		when(tagDAO.getById(1)).thenReturn( null );
		boolean updated = tagService.update(tag);;
		assertFalse( updated );
		verify(tagDAO).getById(1);
		verifyZeroInteractions(tagDAO);
		verifyNoMoreInteractions(tagDAO);
	}

	@Test(expected = ServiceException.class)
	public void testUpdateTagException() throws Exception{
		TagTO tag = new TagTO(1, "tag1");
		when(tagDAO.getById(1)).thenThrow(new DAOException());
		tagService.update(tag);
	}

	@Test
	public void testRemove() throws Exception{
		int id = 1;
		TagTO tag = new TagTO(1, "tag1");
		when(tagDAO.getById(1)).thenReturn(tag);
		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invocation) {
				listTags.remove(id);
				return null;
			}
		}).when(tagDAO).remove(id);
		boolean removed = tagService.remove(id);
		assertTrue(removed);
		verify(tagDAO).getById(id);
		verify(tagDAO).remove(id);
		assertEquals(2, listTags.size());
	}

	@Test
	public void shouldNotRemove() throws Exception{
		when(tagDAO.getById(1)).thenReturn( null );
		boolean removed = tagService.remove(1);
		assertFalse( removed );
		verify(tagDAO).getById(1);
		verifyNoMoreInteractions(tagDAO);
	}

	@Test
	public void testGetTag() throws Exception{
		int id = 1;
		TagTO tag1 = new TagTO(2,"Tag2");
		when(tagDAO.getById(id)).thenAnswer(new Answer<TagTO>() {
			public TagTO answer(InvocationOnMock invocation) {
				return listTags.get(id);
			}
		});
		TagTO tag = tagService.getById(id);
		assertEquals(tag1.getTagId(), tag.getTagId());
		assertEquals(tag1.getTagName(), tag.getTagName());
		verify(tagDAO).getById(id);
	}

	@Test(expected = ServiceException.class)
	public void testGetTagExceprion() throws Exception{
		when(tagDAO.getById(1)).thenThrow(new DAOException());
		tagService.getById(1);
	}

	@Test
	public void testGetTagList() throws Exception{
		int size = 3;
		when(tagDAO.getList()).thenAnswer(new Answer<List<TagTO>>() {
			public List<TagTO> answer(InvocationOnMock invocation) {
				return listTags;
			}
		});
		List<TagTO> listTag = tagService.getList();
		TagTO tag = listTag.get(1);
		assertEquals(size,listTag.size());
		assertEquals(2, tag.getTagId());
		assertEquals("Tag2", tag.getTagName());
		verify(tagDAO).getList();
	}

	@Test
	public void testGetTagListByNews() throws Exception{
		int size = 2;
		int newsId = 1;
		TagTO tag1 = new TagTO(1,"tag1");
		TagTO tag2 = new TagTO(2,"tag2");
		when(tagDAO.getListByNews(newsId)).thenReturn(Arrays.asList(tag1,tag2));
		List<TagTO> listTag = tagService.getListByNews(newsId);
		TagTO tag = listTag.get(1);
		assertEquals(size, listTag.size());
		assertEquals(tag2.getTagId(), tag.getTagId());
		assertEquals(tag2.getTagName(), tag.getTagName());
		verify(tagDAO).getListByNews(newsId);
	}

	@Test
	public void testAttachNews() throws Exception{
		int newsId = 1;
		List<Integer> listTagsId = new ArrayList<Integer>();
		listTagsId.add(4);
		listTagsId.add(3);
		doNothing().when(tagDAO).attachNews(newsId, listTagsId);
		tagService.attachNews(newsId, listTagsId);
		verify(tagDAO).attachNews(newsId, listTagsId);
	}

	@Test
	public void testRemoveRelations() throws Exception{
		int newsId = 1;
		doNothing().when(tagDAO).removeRelations(newsId);;
		tagService.removeRelations(newsId);
		verify(tagDAO).removeRelations(newsId);
	}

	@Test(expected = ServiceException.class)
	public void testRemoveRelationsException() throws Exception{
		int newsId = 1;
		doThrow(new DAOException()).when(tagDAO).removeRelations(newsId);;
		tagService.removeRelations(newsId);
		verify(tagDAO).removeRelations(newsId);
	}
}
