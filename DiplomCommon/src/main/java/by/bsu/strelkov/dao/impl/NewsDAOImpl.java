package by.bsu.strelkov.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.bsu.strelkov.dao.NewsDAO;
import by.bsu.strelkov.model.News;

@Repository
public class NewsDAOImpl extends CrudDAOImpl<News> implements NewsDAO{
	
	private final static String SQL_SEARCH = "from News n order by n.modificationDate desc, n.creationDate desc";
	
	private final static String SQL_SEARCH_BY_AUTHOR = "select distinct n from News n join n.author na where na.id = :author "
			+ "order by modification_date desc, creation_date desc";
	
	private final static String SQL_SEARCH_BY_TAGS = "select distinct n from News n join n.tags nt where nt.id in (:tagsid) "
			+ "order by modification_date desc, creation_date desc";
	
	private final static String SQL_SEARCH_BY_AUTHOR_TAGS = "select distinct n from News n join n.author na "
			+ "join n.tags nt "
			+ "where na.id = :author and nt.id in (:tagsid) "
			+ "order by modification_date desc, creation_date desc";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public NewsDAOImpl() {
		super(News.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> search(int start, int count, List<Long> tagsId, long authorId) {
		Session session = sessionFactory.openSession();
		Query query;
		
		if ((authorId > 0) && tagsId != null) {
			query = session.createQuery(SQL_SEARCH_BY_AUTHOR_TAGS);
			query.setParameter("author", authorId);
			query.setParameterList("tagsid", tagsId);
		} else if (authorId > 0) {
			query = session.createQuery(SQL_SEARCH_BY_AUTHOR);
			query.setParameter("author", authorId);
		} else if (tagsId != null) {
			query = session.createQuery(SQL_SEARCH_BY_TAGS);
			query.setParameterList("tagsid", tagsId);
		} else {
			query = session.createQuery(SQL_SEARCH);
		}
		query.setFirstResult(start);
		query.setMaxResults(count);
		List<News> news = (List<News>)query.list();
		session.close();
		return news;
	}

}
