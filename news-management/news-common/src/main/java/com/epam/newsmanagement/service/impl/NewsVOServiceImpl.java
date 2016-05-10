package com.epam.newsmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;
import com.epam.newsmanagement.model.CommentTO;
import com.epam.newsmanagement.model.NewsVO;
import com.epam.newsmanagement.model.NewsTO;
import com.epam.newsmanagement.model.TagTO;
import com.epam.newsmanagement.service.AuthorService;
import com.epam.newsmanagement.service.CommentService;
import com.epam.newsmanagement.service.NewsVOService;
import com.epam.newsmanagement.service.NewsService;
import com.epam.newsmanagement.service.TagService;

/**
 * The Class NewsVOServiceImpl.
 */
public class NewsVOServiceImpl implements NewsVOService {
	
	/** The author service. */
	private AuthorService authorService;
	
	/** The comment service. */
	private CommentService commentService;
	
	/** The news service. */
	private NewsService newsService;
	
	/** The tag service. */
	private TagService tagService;	
	
	/**
	 * Sets the author service.
	 *
	 * @param authorService the author service
	 */
	public void setAuthorService(AuthorService authorService) {
		this.authorService = authorService;
	}

	/**
	 * Sets the comment service.
	 *
	 * @param commentService the comment service
	 */
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	/**
	 * Sets the news service.
	 *
	 * @param newsService the news service
	 */
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	/**
	 * Sets the tag service.
	 *
	 * @param tagService the tag service
	 */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	/**
	 * @see com.epam.newsmanagement.service.NewsVOService#add(com.epam.newsmanagement.model.NewsVO)
	 */
	@Transactional
	public int add(NewsVO newsMessage) throws ServiceException {
		int generaredId = 0;
		NewsTO news = newsMessage.getNews();
		AuthorTO author = newsMessage.getAuthor();
		List<TagTO> listTags = newsMessage.getListTags();
		generaredId = newsService.add(news);
		authorService.attachNews(news.getNewsId(), author.getAuthorId());
		List<Integer> listTagsId = new ArrayList<Integer>();
		for(TagTO tag : listTags){
			listTagsId.add(tag.getTagId());
		}
		tagService.attachNews(news.getNewsId(), listTagsId);
		return generaredId;
	}

	/**
	 * @see com.epam.newsmanagement.service.NewsVOService#remove(int)
	 */
	@Transactional
	public void remove(int newsId) throws ServiceException {
		tagService.removeRelations(newsId);
		authorService.removeRelations(newsId);
		newsService.remove(newsId);
	}
	
	/**
	 * @see com.epam.newsmanagement.service.NewsVOService#update(com.epam.newsmanagement.model.NewsVO)
	 */
	@Transactional
	public void update(NewsVO newsMessage) throws ServiceException {
		NewsTO news = newsMessage.getNews();
		newsService.update(news);
		tagService.removeRelations(news.getNewsId());
		List<TagTO> listTags = newsMessage.getListTags();
		List<Integer> listTagsId = new ArrayList<Integer>();
		for(TagTO tag : listTags){
			listTagsId.add(tag.getTagId());
		}
		tagService.attachNews(news.getNewsId(), listTagsId);
		authorService.removeRelations(news.getNewsId());
		authorService.attachNews(news.getNewsId(), newsMessage.getAuthor().getAuthorId());
	}

	/**
	 * @see com.epam.newsmanagement.service.NewsVOService#getById(int)
	 */
	public NewsVO getById(int newsId) throws ServiceException {
		NewsVO newsMessage = null;
		NewsTO news = newsService.getById(newsId);
		AuthorTO author = authorService.getAuthorByNews(newsId);
		List<CommentTO> listComments = commentService.getListByNews(newsId);
		List<TagTO> listTags = tagService.getListByNews(newsId);
		newsMessage = new NewsVO(author, news, listComments, listTags);
		return newsMessage;
	}

	/**
	 * @see com.epam.newsmanagement.service.NewsVOService#getList()
	 */
	public List<NewsVO> getList(int authorId, List<Integer> tagsId, int start, int count) throws ServiceException {
		List<NewsVO> listNewsVO = new ArrayList<NewsVO>();
		List<NewsTO> listNewsTO = null;
		if(authorId != 0 && tagsId != null){
			listNewsTO = newsService.getList(authorId, tagsId, start, count);
		}else if(authorId != 0 && tagsId == null){
			listNewsTO = newsService.getList(authorId, start, count);
		}else if(authorId == 0 && tagsId != null){
			listNewsTO = newsService.getList(tagsId, start, count);
		}else{
			listNewsTO = newsService.getList(start, count);
		}
		for(NewsTO news : listNewsTO){
			AuthorTO author = authorService.getAuthorByNews(news.getNewsId());
			List<CommentTO> listComments = commentService.getListByNews(news.getNewsId());
			List<TagTO> listTags = tagService.getListByNews(news.getNewsId());
			listNewsVO.add( new NewsVO(author, news, listComments, listTags) );
		}
		return listNewsVO;
	}

	public int getNumberNews(int authorId, List<Integer> tagsId) throws ServiceException {
		int number = 0;
		if(authorId != 0 && tagsId != null){
			number = newsService.getNumberNews(authorId, tagsId);
		}else if(authorId != 0 && tagsId == null){
			number = newsService.getNumberNews(authorId);
		}else if(authorId == 0 && tagsId != null){
			number = newsService.getNumberNews(tagsId);
		}else{
			number = newsService.getNumberNews();
		}
		return number;
	}
}
