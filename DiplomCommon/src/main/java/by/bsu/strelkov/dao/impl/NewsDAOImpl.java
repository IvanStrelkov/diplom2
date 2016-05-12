package by.bsu.strelkov.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.bsu.strelkov.dao.NewsDAO;
import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.News;

@Repository
public class NewsDAOImpl extends CrudDAOImpl<News> implements NewsDAO{
	
	private final static String SQL_SEARCH = "SELECT FROM NEWS ORDER BY MODIFICATION_DATE DESK, CREATION_DATE DESK"
			+ " LIMIT :START, :COUNT";
	
	private final static String SQL_SEARCH_BY_AUTHOR = "SELECT FROM NEWS JOIN NEWS_AUTHOR ON NEWS.ID = NEWS_AUTHOR.NEWS_ID "
			+ "AND NEWS_AUTHOR.AUTHOR_ID = :AUTHOR ORDER BY MODIFICATION_DATE DESK, CREATION_DATE DESK"
			+ " LIMIT :START, :COUNT";
	
	private final static String SQL_SEARCH_BY_TAGS = "SELECT FROM NEWS JOIN NEWS_TAG ON NEWS.ID = NEWS_TAG.NEWS_ID "
			+ "AND NEWS_TAG.TAG_ID IN (:TAGSID) ORDER BY MODIFICATION_DATE DESK, CREATION_DATE DESK"
			+ " LIMIT :START, :COUNT";
	
	private final static String SQL_SEARCH_BY_AUTHOR_TAGS = "SELECT FROM NEWS "
			+ "JOIN NEWS_AUTHOR ON NEWS.ID = NEWS_AUTHOR.NEWS_ID AND NEWS_AUTHOR.AUTHOR_ID = :AUTHOR "
			+ "JOIN NEWS_TAG ON NEWS.ID = NEWS_TAG.NEWS_ID AND NEWS_TAG.TAG_ID IN (:TAGSID) "
			+ "ORDER BY MODIFICATION_DATE DESK, CREATION_DATE DESK "
			+ "LIMIT :START, :COUNT";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public NewsDAOImpl() {
		super(News.class);
	}

	@Override
	public List<News> getList(long authorId, long start, long count) throws DiplomException {
		return readAll();
	}

	@Override
	public List<News> getList(List<Long> listId, long start, long count) throws DiplomException {
		return readAll();
	}

	@Override
	public List<News> getList(long authorId, List<Long> listId, long start, long count) throws DiplomException {
		return readAll();
	}

	@Override
	public int getNumberNews() throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getNumberNews(long authorId) throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getNumberNews(List<Long> listId) throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getNumberNews(long authorId, List<Long> listId) throws DiplomException {
		// TODO Auto-generated method stub
		return 10;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> search(int start, int count, List<Long> tagsId, long authorId) {
		Session session = sessionFactory.openSession();
		Query query;
		
		if ((authorId > 0) && tagsId.size() > 0) {
			query = session.createQuery(SQL_SEARCH_BY_AUTHOR_TAGS);
			query.setParameter("AUTHOR", authorId);
			query.setParameter("TAGSID", tagsId.toArray());
		} else if (authorId > 0) {
			query = session.createQuery(SQL_SEARCH_BY_AUTHOR);
			query.setParameter("AUTHOR", authorId);
		} else if (tagsId.size() > 0) {
			query = session.createQuery(SQL_SEARCH_BY_TAGS);
			query.setParameter("TAGSID", tagsId.toArray());
		} else {
			query = session.createQuery(SQL_SEARCH);
		}
		query.setParameter("START", start);
		query.setParameter("COUNT", count);
		List<News> news = (List<News>)query.list();
		session.close();
		return news;
	}

}
