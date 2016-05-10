package com.epam.newsmanagement.model;

import java.io.Serializable;

/**
 * The Class TagTO is associated with the table TAG.
 */
public class TagTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The tag id. */
	private int tagId;
	
	/** The tag name. */
	private String tagName;
	
	/**
	 * The Constructor.
	 */
	public TagTO() {
		super();
	}

	/**
	 * The Constructor.
	 *
	 * @param tagId the tag id
	 * @param tagName the tag name
	 */
	public TagTO(int tagId, String tagName) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
	}

	/**
	 * Gets the tag id.
	 *
	 * @return the tag id
	 */
	public int getTagId() {
		return tagId;
	}

	/**
	 * Sets the tag id.
	 *
	 * @param tagId the tag id
	 */
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	/**
	 * Gets the tag name.
	 *
	 * @return the tag name
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * Sets the tag name.
	 *
	 * @param tagName the tag name
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tagId;
		result = prime * result + ((tagName == null) ? 0 : tagName.hashCode());
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
		TagTO other = (TagTO) obj;
		if (tagId != other.tagId)
			return false;
		if (tagName == null) {
			if (other.tagName != null)
				return false;
		} else if (!tagName.equals(other.tagName))
			return false;
		return true;
	}
}
