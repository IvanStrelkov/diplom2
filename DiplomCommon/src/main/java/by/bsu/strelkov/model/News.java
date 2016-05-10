package by.bsu.strelkov.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class News implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2922439210937196808L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private long id;

	@Column(name = "short_text")
	private String shortText;
	
	@Column(name = "full_text")
	private String fullText;

	@Column(name = "title")
	private String title;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "modification_date")
	private Date modificationDate;
	
	@ManyToMany
    @JoinTable(name="news_author", 
                joinColumns={@JoinColumn(name="news_id")}, 
                inverseJoinColumns={@JoinColumn(name="author_id")})
	private List<Author> authors;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="news_tag", 
                joinColumns={@JoinColumn(name="news_id")}, 
                inverseJoinColumns={@JoinColumn(name="tag_id")})
    private List<Tag> tags;
    
	@OneToMany(mappedBy = "news")
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
		return authors.get(0);
	}

	public void setAuthor(Author author) {
		this.authors = new ArrayList<Author>();
		this.authors.add(author);
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

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
}
