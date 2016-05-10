package com.epam.newsmanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class AuthorTO is associated with the table AUTHOR.
 */
public class AuthorTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The author id. */
	private int authorId;
	
	/** The name. */
	private String authorName;
	
	private Date expired;
	/**
	 * The Constructor.
	 */
	public AuthorTO() {
		super();
	}
	
	/**
	 * The Constructor.
	 *
	 * @param authorId the author id
	 * @param name the name
	 */
	public AuthorTO(int authorId, String name) {
		super();
		this.authorId = authorId;
		this.authorName = name;
		this.expired = null;
	}
	
	public AuthorTO(int authorId, String name, Date expected){
		super();
		this.authorId = authorId;
		this.authorName = name;
		this.expired = expected;
	}

	/**
	 * Gets the author id.
	 *
	 * @return the author id
	 */
	public int getAuthorId() {
		return authorId;
	}

	/**
	 * Sets the author id.
	 *
	 * @param authorId the author id
	 */
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name
	 */
	public void setAuthorName(String name) {
		this.authorName = name;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authorId;
		result = prime * result
				+ ((authorName == null) ? 0 : authorName.hashCode());
		result = prime * result
				+ ((expired == null) ? 0 : expired.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorTO other = (AuthorTO) obj;
		if (authorId != other.authorId)
			return false;
		if (authorName == null) {
			if (other.authorName != null)
				return false;
		} else if (!authorName.equals(other.authorName))
			return false;
		if (expired == null) {
			if (other.expired != null)
				return false;
		} else if (!expired.equals(other.expired))
			return false;
		return true;
	}	
	
	
}
