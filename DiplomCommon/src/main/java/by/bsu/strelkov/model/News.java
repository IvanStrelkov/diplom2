package by.bsu.strelkov.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "NEWS")
public class News implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2922439210937196808L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;

	@Column(name = "SHORT_TEXT")
	private String shortText;
	
	@Column(name = "FULL_TEXT")
	private String fullText;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "MODIFICATION_DATE")
	private Date modificationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name="NEWS_AUTHOR", 
    	joinColumns={@JoinColumn(name="NEWS_ID")}, 
    	inverseJoinColumns={@JoinColumn(name="AUTHOR_ID")})
	private Author author;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name="NEWS_TAG", 
                joinColumns={@JoinColumn(name="NEWS_ID")}, 
                inverseJoinColumns={@JoinColumn(name="TAG_ID")})
    private List<Tag> tags;
    
	@OneToMany(mappedBy = "news", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> comments;

	public News() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
