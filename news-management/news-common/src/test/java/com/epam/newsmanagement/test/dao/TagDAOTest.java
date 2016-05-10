package com.epam.newsmanagement.test.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.epam.newsmanagement.dao.TagDAO;
import com.epam.newsmanagement.model.TagTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@DatabaseSetup("classpath:tag/tagData.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class TagDAOTest{
	
	@Autowired
	private TagDAO tagDAO;

	private TagTO tag;
 
    public TagDAOTest(){
    	Locale.setDefault(Locale.ENGLISH);
        tag = new TagTO(1, "test tag");
    }
    
    @Test
    @ExpectedDatabase(value="classpath:tag/tagDataAdd.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testAdd() throws Exception{
    	int id = 0;
    	id = tagDAO.add(tag);
    	assertTrue(id > 0);
    }
    
    @Test
    @ExpectedDatabase(value="classpath:tag/tagDataRemove.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testRemove() throws Exception{
    	tagDAO.remove(tag.getTagId());
    }
    
    @Test 
    @ExpectedDatabase(value="classpath:tag/tagDataUpdate.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testUpdate() throws Exception{
    	tagDAO.update(tag);
    }
    
    @Test
    public void testGetAll() throws Exception{
    	List<TagTO> listTag = tagDAO.getList();
    	assertEquals(4, listTag.size());
    	assertEquals(1, listTag.get(0).getTagId());
    	assertEquals(2, listTag.get(1).getTagId());
    	assertEquals(3, listTag.get(2).getTagId());
    	assertEquals(4, listTag.get(3).getTagId());
    	assertEquals("tag1", listTag.get(0).getTagName());
    }
    
    @Test 
    public void testGetById() throws Exception{
    	TagTO actualTag = tagDAO.getById(1);
    	TagTO expectedTag = new TagTO(1, "tag1");
    	assertEquals(expectedTag.getTagId(), actualTag.getTagId());
    	assertEquals(expectedTag.getTagName(), actualTag.getTagName());
    }
    
    @Test 
    @ExpectedDatabase(value="classpath:tag/tagDataAttachNews.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testAttachNews() throws Exception{
    	int newsId = 1;
    	List<Integer> listTagsId = new ArrayList<Integer>();
    	listTagsId.add(4);
    	listTagsId.add(3);
    	tagDAO.attachNews(newsId, listTagsId);
    }
    
    @Test 
    public void testGetListByNews() throws Exception{
    	int newsId = 1;
    	int idTag1 = 1;
    	int idTag2 = 2;
    	int size = 2;
    	List<TagTO> listTag = tagDAO.getListByNews(newsId);
    	assertEquals(size,listTag.size());
    	assertEquals(idTag1, listTag.get(0).getTagId());
    	assertEquals(idTag2, listTag.get(1).getTagId());
    	assertEquals("tag1", listTag.get(0).getTagName());
    }
    
    @Test
    @ExpectedDatabase(value="classpath:tag/tagDataRemoveRelations.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
    public void testRemoveRelations() throws Exception{
    	int newsId = 1;
    	tagDAO.removeRelations(newsId);
    }
}
