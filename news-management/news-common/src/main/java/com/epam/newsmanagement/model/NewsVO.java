package com.epam.newsmanagement.model;

import java.io.Serializable;
import java.util.List;

public class NewsVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AuthorTO author;
	private NewsTO news;
	private List<CommentTO> listComments;
	private List<TagTO> listTags;
	
	public NewsVO() {
		super();
	}

	public NewsVO(AuthorTO author, NewsTO news,
			List<CommentTO> listComments, List<TagTO> listTags) {
		super();
		this.author = author;
		this.news = news;
		this.listComments = listComments;
		this.listTags = listTags;
	}

	public AuthorTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorTO author) {
		this.author = author;
	}

	public NewsTO getNews() {
		return news;
	}

	public void setNews(NewsTO news) {
		this.news = news;
	}

	public List<CommentTO> getListComments() {
		return listComments;
	}

	public void setListComments(List<CommentTO> listComments) {
		this.listComments = listComments;
	}

	public List<TagTO> getListTags() {
		return listTags;
	}

	public void setListTags(List<TagTO> listTags) {
		this.listTags = listTags;
	}
	
	public int getCommentsCount(){
		return listComments.size();
	}
}
