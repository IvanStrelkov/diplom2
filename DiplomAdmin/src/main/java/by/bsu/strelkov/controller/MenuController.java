package by.bsu.strelkov.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;
import by.bsu.strelkov.model.News;
import by.bsu.strelkov.model.Tag;
import by.bsu.strelkov.service.AuthorService;
import by.bsu.strelkov.service.NewsService;
import by.bsu.strelkov.service.TagService;

@Controller
public class MenuController{

	@Autowired
	private NewsService newsService;
	@Autowired
	private TagService tagService;
	@Autowired
	private AuthorService authorService;

	@RequestMapping(value = "/listNews")
	public String viewListNews(HttpSession session){
		session.setAttribute("selectedAuthor", null);
		session.setAttribute("selectedTags", null);
		return "redirect:/list/1";
	}

	@RequestMapping(value="/addNews")
	public String addNews(ModelMap model) throws DiplomException{
		News news = new News();
		List<Tag> listTags;
		List<Author> listAuthor;
		int newsNumber;
		listTags = tagService.readAll();
		listAuthor = authorService.readAll();
		newsNumber = newsService.getNumberNews() + 1;
		model.addAttribute("newsNumber", newsNumber);
		if(!model.containsAttribute("news")){
			news.setCreationDate(new Date());
			model.addAttribute("news", news);
		}
		model.addAttribute("menu", "addNews");
		model.addAttribute("listTags", listTags);
		model.addAttribute("listAuthor", listAuthor);
		return "editNews";
	}

	@RequestMapping(value = "/listTags")
	public String viewListTags( ModelMap model) throws DiplomException{
		List<Tag> listTags;
		listTags = tagService.readAll();
		model.addAttribute("menu", "tags");
		model.addAttribute("listTags", listTags);
		return "listTags";
	}

	@RequestMapping(value = "/listAuthors")
	public String viewListAuthors( ModelMap model) throws DiplomException{
		List<Author> listAuthors;
		listAuthors = authorService.readAll();
		model.addAttribute("menu", "authors");
		model.addAttribute("listAuthors", listAuthors);
		return "listAuthors";
	}
}
