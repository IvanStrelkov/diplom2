package by.bsu.strelkov.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ListNewsController{

	private static final int PAGE_SIZE = 4;

	@Autowired
	private NewsService newsService;
	@Autowired
	private TagService tagService;
	@Autowired
	private AuthorService authorService;

	@RequestMapping(value = "/")
	public String home(Model model){
		Locale.setDefault(Locale.ENGLISH);
		return "redirect:/list/1";
	}

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, ModelMap model){
		boolean error = Boolean.parseBoolean(request.getParameter("error"));
		model.addAttribute("error", error);
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, ModelMap model){
		return "redirect:/login";
	}

	@RequestMapping(value="/list/{page}")
	public String getList(HttpServletRequest request, ModelMap model, @PathVariable int page) throws DiplomException{
		int authorId = 0;
		List<Long> listTagsId = null;
		String stringAuthorId = (String)request.getSession().getAttribute("selectedAuthor");
		String[] stringTagsId = (String[])request.getSession().getAttribute("selectedTags");
		if(stringAuthorId != null){
			authorId = Integer.parseInt(stringAuthorId);
		}
		if(stringTagsId != null){
			listTagsId = new ArrayList<Long>();
			for(String id : stringTagsId){
				listTagsId.add(Long.parseLong(id));
			}
		}
		int countNews = newsService.getNumberNews(authorId, listTagsId);
		int countPages = countNews/PAGE_SIZE;
		countPages+=(countNews % PAGE_SIZE == 0)? 0 : 1;
		if(page < 1){
			page = 1;
		}else if(page > countPages){
			page = countPages;
		}
		List<News> listNews = newsService.getList(authorId, listTagsId, (page-1)*PAGE_SIZE, PAGE_SIZE);
		List<Tag> listTags = tagService.readAll();
		List<Author> listAuthor = authorService.readAll();
		model.addAttribute("menu", "listNews");
		model.addAttribute("startNews", (page-1)*PAGE_SIZE + 1);
		model.addAttribute("currentPage", page);
		model.addAttribute("countPages", countPages);
		model.addAttribute("filterTags", listTagsId);
		model.addAttribute("filterAuthor", authorId);
		model.addAttribute("listNews", listNews);
		model.addAttribute("listTags", listTags);
		model.addAttribute("listAuthor", listAuthor);
		return "listNews";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteNews(HttpServletRequest request) throws DiplomException{
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String[] newsId = request.getParameterValues("deletedNews");
		if(newsId != null){
			for(String stringId: newsId){
				long id = Long.parseLong(stringId);
				newsService.delete(id);
			}
		}
		return "redirect:/list/" + currentPage;
	}

	@RequestMapping(value="/editNews/{number}/{newsId}")
	public String editNews(ModelMap model, @PathVariable int number, @PathVariable long newsId) throws DiplomException {
		News news;
		List<Tag> listTags;
		List<Author> listAuthor;
			news = newsService.read(newsId);
			listTags = tagService.readAll();
			listAuthor = authorService.readAll();
		Author author = news.getAuthor();
		if(!listAuthor.contains(author)){
			listAuthor.add(author);
		}
		model.addAttribute("newsNumber", number);
		if(!model.containsAttribute("news")){
			model.addAttribute("news", news);
		}
		model.addAttribute("menu", "addNews");
		model.addAttribute("newsTags", news.getTags());
		model.addAttribute("newsAuthor", news.getAuthor());
		model.addAttribute("listTags", listTags);
		model.addAttribute("listAuthor", listAuthor);
		return "editNews";
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public String filter(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		String choise = request.getParameter("filter");
		if("filter".equals(choise)){
			String authorId = request.getParameter("selectAuthor");
			String[] tagsId = request.getParameterValues("selectTags");
			session.setAttribute("selectedAuthor", authorId);
			session.setAttribute("selectedTags", tagsId);
		}else if("reset".equals(choise)){
			session.setAttribute("selectedAuthor", null);
			session.setAttribute("selectedTags", null);
		}
		return "redirect:/list/1";
	}
}
