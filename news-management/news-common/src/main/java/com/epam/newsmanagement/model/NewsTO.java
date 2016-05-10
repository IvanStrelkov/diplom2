package com.epam.newsmanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The Class NewsTO is associated with the table NEWS.
 */
public class NewsTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The news id. */
	private int newsId;
	
	/** The short text. */
	private String shortText;
	
	/** The full text. */
	private String fullText;
	
	/** The title. */
	private String title;
	
	/** The creation date. */
	private Date creationDate;
	
	/** The modification date. */
	private Date modificationDate;
	
	/**
	 * The Constructor.
	 */
	public NewsTO() {
		super();
	}
	
	
	/**
	 * The Constructor.
	 *
	 * @param newsId the news id
	 * @param shortText the short text
	 * @param fullText the full text
	 * @param title the title
	 * @param creationDate the creation date
	 * @param modificationDate the modification date
	 */
	public NewsTO(int newsId, String shortText, String fullText, String title,
			Date creationDate, Date modificationDate) {
		super();
		this.newsId = newsId;
		this.shortText = shortText;
		this.fullText = fullText;
		this.title = title;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
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
	 * Gets the short text.
	 *
	 * @return the short text
	 */
	public String getShortText() {
		return shortText;
	}
	
	/**
	 * Sets the short text.
	 *
	 * @param shortText the short text
	 */
	public void setShortText(String shortText) {
		this.shortText = shortText;
	}
	
	/**
	 * Gets the full text.
	 *
	 * @return the full text
	 */
	public String getFullText() {
		return fullText;
	}
	
	/**
	 * Sets the full text.
	 *
	 * @param fullText the full text
	 */
	public void setFullText(String fullText) {
		this.fullText = fullText;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * Gets the modification date.
	 *
	 * @return the modification date
	 */
	public Date getModificationDate() {
		return modificationDate;
	}
	
	/**
	 * Sets the modification date.
	 *
	 * @param modificationDate the modification date
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((fullText == null) ? 0 : fullText.hashCode());
		result = prime
				* result
				+ ((modificationDate == null) ? 0 : modificationDate.hashCode());
		result = prime * result + newsId;
		result = prime * result
				+ ((shortText == null) ? 0 : shortText.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NewsTO other = (NewsTO) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (fullText == null) {
			if (other.fullText != null)
				return false;
		} else if (!fullText.equals(other.fullText))
			return false;
		if (modificationDate == null) {
			if (other.modificationDate != null)
				return false;
		} else if (!modificationDate.equals(other.modificationDate))
			return false;
		if (newsId != other.newsId)
			return false;
		if (shortText == null) {
			if (other.shortText != null)
				return false;
		} else if (!shortText.equals(other.shortText))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
}
