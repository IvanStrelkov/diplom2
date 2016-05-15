package by.bsu.strelkov.dao;

import java.util.List;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.News;

public interface NewsDAO extends CrudDAO<News> {

	public List<News> search(int start, int count, List<Long> tagsId, long authorId);

}