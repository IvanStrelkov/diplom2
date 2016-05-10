package by.bsu.strelkov.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Comment;
import by.bsu.strelkov.model.News;
import by.bsu.strelkov.service.AuthorService;
import by.bsu.strelkov.service.CommentService;
import by.bsu.strelkov.service.NewsService;
import by.bsu.strelkov.service.TagService;

@Controller
public class ViewNewsController{

	private static final int PAGE_SIZE = 4;

	@Autowired
	private NewsService newsService;
	@Autowired
	private TagService tagService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CommentService commentService;

	@RequestMapping(value="/view/{number}/{newsId}")
	public String getNews(HttpServletRequest request, ModelMap model, @PathVariable int number, @PathVariable long newsId) throws DiplomException{
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
		int numberNews = newsService.getNumberNews(authorId, listTagsId);
		List<News> listNews = newsService.getList(authorId, listTagsId, number-2, 3);
		long previousId = listNews.get(0).getId();
		long nextId = listNews.get(listNews.size()-1).getId();
		if(number < 1){
			number = 1;
		}else if(number > numberNews){
			number = numberNews;
		}
		News news = newsService.read(newsId);
		if(!model.containsAttribute("comment")){
			model.addAttribute("comment", new Comment());
		}
		model.addAttribute("previousId", previousId);
		model.addAttribute("nextId", nextId);
		model.addAttribute("currentNews", number);
		model.addAttribute("numberNews", numberNews);
		model.addAttribute("news", news);
		return "view";
	}

	@RequestMapping(value = "/back/{currentNews}")
	public String back(@PathVariable int currentNews){
		int page = currentNews/PAGE_SIZE;
		page += (currentNews % PAGE_SIZE == 0)? 0 : 1;
		return "redirect:/list/" + page;
	}

	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public String addComment(@ModelAttribute("comment") Comment comment, BindingResult result,
			RedirectAttributes attr, HttpServletRequest request) throws DiplomException{
		int newsId = Integer.parseInt(request.getParameter("newsId"));
		int currentNews = Integer.parseInt(request.getParameter("currentNews"));
		comment.setCreationDate(new Date());
		commentService.create(comment);

		return "redirect:/view/" + currentNews + "/" + newsId;
	}

	@RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
	public String deleteComment(HttpServletRequest request) throws DiplomException{

		int newsId = Integer.parseInt(request.getParameter("newsId"));
		int currentNews = Integer.parseInt(request.getParameter("currentNews"));
		long commentId = Integer.parseInt(request.getParameter("commentId"));
		commentService.delete(commentId);
		return "redirect:/view/" + currentNews + "/" + newsId;
	}
}
