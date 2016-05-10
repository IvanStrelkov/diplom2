package by.bsu.strelkov.service;

import java.util.List;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Tag;

public interface TagService extends CrudService<Tag> {

	public List<Tag> getListByNews(int newsId) throws DiplomException;
}