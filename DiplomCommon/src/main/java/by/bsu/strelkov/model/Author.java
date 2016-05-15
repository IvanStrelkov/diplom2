package by.bsu.strelkov.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AUTHOR")
public class Author implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2240703515643099533L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;
	
	@Column(name = "NAME")
	private String name;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name="NEWS_AUTHOR", 
            joinColumns={@JoinColumn(name="AUTHOR_ID")}, 
            inverseJoinColumns={@JoinColumn(name="NEWS_ID")})
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
