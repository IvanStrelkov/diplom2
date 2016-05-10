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

import com.epam.newsmanagement.exception.ControllerException;

import by.bsu.strelkov.service.AuthorService;
import by.bsu.strelkov.service.NewsService;
import by.bsu.strelkov.service.TagService;

@Controller
public class ListNewsController{
	
	private static final int PAGE_SIZE = 4;
	
	@Autowired
	private NewsService newsVOService;
	@Autowired
	private TagService tagService;
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(value = "/")
	public String home(Model model){
		Locale.setDefault(Locale.ENGLISH);
		return "redirect:/list/1";
	}
	
	@RequestMapping(value="/list/{page}")
	public String getList(HttpServletRequest request, ModelMap model, @PathVariable int page) throws ControllerException{
		try {
			int authorId = 0;
			List<Integer> listTagsId = null;
			String stringAuthorId = (String)request.getSession().getAttribute("selectedAuthor");
			String[] stringTagsId = (String[])request.getSession().getAttribute("selectedTags");
			if(stringAuthorId != null){
				authorId = Integer.parseInt(stringAuthorId);
			}
			if(stringTagsId != null){
				listTagsId = new ArrayList<Integer>();
				for(String id : stringTagsId){
					listTagsId.add(Integer.parseInt(id));
				}
			}
			int countNews = newsVOService.getNumberNews(authorId, listTagsId);
			int countPages = countNews/PAGE_SIZE;
			countPages+=(countNews % PAGE_SIZE == 0)? 0 : 1;
			if(page < 1){
				page = 1;
			}else if(page > countPages){
				page = countPages;
			}
			List<NewsVO> listNews = newsVOService.getList(authorId, listTagsId, (page-1)*PAGE_SIZE, PAGE_SIZE);
			List<TagTO> listTags = tagService.getList();
			List<AuthorTO> listAuthor = authorService.getList();
			model.addAttribute("menu", "listNews");
			model.addAttribute("startNews", (page-1)*PAGE_SIZE + 1);
			model.addAttribute("currentPage", page);
			model.addAttribute("countPages", countPages);
			model.addAttribute("filterTags", listTagsId);
			model.addAttribute("filterAuthor", authorId);
			model.addAttribute("listNews", listNews);
			model.addAttribute("listTags", listTags);
			model.addAttribute("listAuthor", listAuthor);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return "listNews";
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
