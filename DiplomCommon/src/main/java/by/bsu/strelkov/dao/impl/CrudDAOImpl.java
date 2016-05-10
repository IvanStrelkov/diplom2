package by.bsu.strelkov.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.bsu.strelkov.dao.CrudDAO;

@Repository
public abstract class CrudDAOImpl<T> implements CrudDAO<T> {

	private Class<T> type;

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public CrudDAOImpl(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public T create(T model) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(model);
		session.getTransaction().commit();
		session.close();
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(Long model_id) {
		Session session = sessionFactory.openSession();
		T model = (T)session.get(type, model_id);
		session.close();
		return model;
	}

	@Override
	public T update(T model) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(model);
		session.getTransaction().commit();
		session.close();
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long model_id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		T client = (T)session.get(type, model_id);
		session.delete(client);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> readAll() {
		Session session = sessionFactory.openSession();
		List<T> list = session.createCriteria(type).list();
		return list;
	}

}
