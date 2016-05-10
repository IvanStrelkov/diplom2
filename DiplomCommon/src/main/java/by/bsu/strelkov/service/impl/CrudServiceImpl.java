package by.bsu.strelkov.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import by.bsu.strelkov.dao.CrudDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.service.CrudService;

@Service
public abstract class CrudServiceImpl<T> implements CrudService<T> {
	
	private static final String INCORRECT_DATA = "Incorrect data";

	private CrudDAO<T> crudDAO;

	public CrudServiceImpl(CrudDAO<T> crudDAO) {
		this.crudDAO = crudDAO;
	}

	public T create(T model) throws DiplomException {
		if (model != null) {
			return crudDAO.create(model);
		} else {
			throw new DiplomException(INCORRECT_DATA);
		}
	}

	public T read(Long model_id) {
		return crudDAO.read(model_id);
	}

	public T update(T model) throws DiplomException {
		if (model != null) {
			return crudDAO.update(model);
		} else {
			throw new DiplomException(INCORRECT_DATA);
		}
	}

	public void delete(Long model_id) {
		crudDAO.delete(model_id);
	}

	public List<T> readAll() {
		return crudDAO.readAll();
	}

}
