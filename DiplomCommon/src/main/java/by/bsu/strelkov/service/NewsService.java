package by.bsu.strelkov.service;

import java.util.List;

import by.bsu.strelkov.model.News;

public interface NewsService extends CrudService<News> {

	public List<News> search(int start, int count, List<Long> tagsId, long authorId);
	
}