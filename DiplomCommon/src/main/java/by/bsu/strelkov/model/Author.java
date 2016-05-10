package by.bsu.strelkov.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2240703515643099533L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy="authors")
	private List<News> news;

	public Author() {
		super();
	}

	public Author(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}	
}
