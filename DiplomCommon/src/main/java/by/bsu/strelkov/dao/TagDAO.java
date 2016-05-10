package by.bsu.strelkov.dao;

import java.util.List;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Tag;

public interface TagDAO extends CrudDAO<Tag>{

	public List<Tag> getListByNews(int newsId) throws DiplomException;
}