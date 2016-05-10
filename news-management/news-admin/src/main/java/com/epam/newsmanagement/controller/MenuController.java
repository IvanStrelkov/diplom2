package com.epam.newsmanagement.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.newsmanagement.exception.ControllerException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;
import com.epam.newsmanagement.model.NewsTO;
import com.epam.newsmanagement.model.TagTO;
import com.epam.newsmanagement.service.AuthorService;
import com.epam.newsmanagement.service.NewsService;
import com.epam.newsmanagement.service.TagService;

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
	public String addNews(ModelMap model) throws ControllerException{
		NewsTO news = new NewsTO();
		List<TagTO> listTags;
		List<AuthorTO> listAuthor;
		int newsNumber;
		try {
			listTags = tagService.getList();
			listAuthor = authorService.getList();
			newsNumber = newsService.getNumberNews() + 1;
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
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
	public String viewListTags( ModelMap model) throws ControllerException{
		List<TagTO> listTags;
		try {
			listTags = tagService.getList();
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		model.addAttribute("menu", "tags");
		model.addAttribute("listTags", listTags);
		return "listTags";
	}
	
	@RequestMapping(value = "/listAuthors")
	public String viewListAuthors( ModelMap model) throws ControllerException{
		List<AuthorTO> listAuthors;
		try {
			listAuthors = authorService.getList();
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		model.addAttribute("menu", "authors");
		model.addAttribute("listAuthors", listAuthors);
		return "listAuthors";
	}
}
