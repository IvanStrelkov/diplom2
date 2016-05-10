package by.bsu.strelkov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.strelkov.dao.TagDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Tag;
import by.bsu.strelkov.service.TagService;

@Service
public class TagServiceImpl extends CrudServiceImpl<Tag> implements TagService {

	private TagDAO tagDAO;
	
	@Autowired
	public TagServiceImpl(TagDAO tagDAO) {
		super(tagDAO);
		this.tagDAO = tagDAO;
	}

	public List<Tag> getListByNews(int newsId) throws DiplomException {
		return tagDAO.getListByNews(newsId);
	}
}