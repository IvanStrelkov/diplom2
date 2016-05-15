package by.bsu.strelkov.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsu.strelkov.dao.NewsDAO;
import by.bsu.strelkov.model.News;
import by.bsu.strelkov.service.NewsService;

@Service
public class NewsServiceImpl extends CrudServiceImpl<News> implements NewsService {
	
	private NewsDAO newsDAO;

	@Autowired
	public NewsServiceImpl(NewsDAO newsDAO) {
		super(newsDAO);
		this.newsDAO = newsDAO;
	}

	@Override
	public List<News> search(int start, int count, List<Long> tagsId, long authorId) {
		return newsDAO.search(start, count, tagsId, authorId);
	}

}