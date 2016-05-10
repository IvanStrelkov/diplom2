package com.epam.newsmanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class CommentTO is associated with the table COMMENTS.
 */
public class CommentTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The comment id. */
	private int commentId;
	
	/** The comment text. */
	private String commentText;
	
	/** The creation date. */
	private Date creationDate;
	
	/** The news id. */
	private int newsId;
	
	/**
	 * The Constructor.
	 */
	public CommentTO() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param commentId the comment id
	 * @param commentText the comment text
	 * @param creationDate the creation date
	 * @param newsId the news id
	 */
	public CommentTO(int commentId, String commentText, Date creationDate,
			int newsId) {
		super();
		this.commentText = commentText;
		this.commentId = commentId;
		this.creationDate = creationDate;
		this.newsId = newsId;
	}

	/**
	 * Gets the comment id.
	 *
	 * @return the comment id
	 */
	public int getCommentId() {
		return commentId;
	}

	/**
	 * Sets the comment id.
	 *
	 * @param commentId the comment id
	 */
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the news id.
	 *
	 * @return the news id
	 */
	public int getNewsId() {
		return newsId;
	}

	/**
	 * Sets the news id.
	 *
	 * @param newsId the news id
	 */
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	/**
	 * Gets the comment text.
	 *
	 * @return the comment text
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * Sets the comment text.
	 *
	 * @param commentText the comment text
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commentId;
		result = prime * result
				+ ((commentText == null) ? 0 : commentText.hashCode());
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + newsId;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentTO other = (CommentTO) obj;
		if (commentId != other.commentId)
			return false;
		if (commentText == null) {
			if (other.commentText != null)
				return false;
		} else if (!commentText.equals(other.commentText))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (newsId != other.newsId)
			return false;
		return true;
	}
}
