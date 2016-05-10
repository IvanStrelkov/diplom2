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

import com.epam.newsmanagement.exception.ControllerException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.CommentTO;
import com.epam.newsmanagement.model.NewsVO;
import com.epam.newsmanagement.service.AuthorService;
import com.epam.newsmanagement.service.CommentService;
import com.epam.newsmanagement.service.NewsService;
import com.epam.newsmanagement.service.NewsVOService;
import com.epam.newsmanagement.service.TagService;
import com.epam.newsmanagement.validator.CommentValidator;

@Controller
public class ViewNewsController{
	
	private static final int PAGE_SIZE = 4;
	
	@Autowired
	private NewsVOService newsVOService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private TagService tagService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private CommentService commentService;

	@RequestMapping(value="/view/{number}/{newsId}")
	public String getNews(HttpServletRequest request, ModelMap model, @PathVariable int number, @PathVariable int newsId) throws ControllerException{
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
			int numberNews = newsVOService.getNumberNews(authorId, listTagsId);
			List<NewsVO> listNews = newsVOService.getList(authorId, listTagsId, number-2, 3);
			int previousId = listNews.get(0).getNews().getNewsId();
			int nextId = listNews.get(listNews.size()-1).getNews().getNewsId();
			if(number < 1){
				number = 1;
			}else if(number > numberNews){
				number = numberNews;
			}
			NewsVO news = newsVOService.getById(newsId);
			if(!model.containsAttribute("comment")){
				model.addAttribute("comment", new CommentTO());
			}
			model.addAttribute("previousId", previousId);
			model.addAttribute("nextId", nextId);
			model.addAttribute("currentNews", number);
			model.addAttribute("numberNews", numberNews);
			model.addAttribute("news", news);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return "view";
	}
	
	@RequestMapping(value = "/back/{currentNews}")
	public String back(@PathVariable int currentNews){
		int page = currentNews/PAGE_SIZE;
		page += (currentNews % PAGE_SIZE == 0)? 0 : 1;
		return "redirect:/list/" + page;
	}

	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public String addComment(@ModelAttribute("comment") CommentTO comment, BindingResult result,
			RedirectAttributes attr, HttpServletRequest request) throws ControllerException{
		int newsId = Integer.parseInt(request.getParameter("newsId"));
		int currentNews = Integer.parseInt(request.getParameter("currentNews"));
		CommentValidator validator = new CommentValidator();
		validator.validate(comment, result);
		if(!result.hasErrors()){
			comment.setCreationDate(new Date());
			try {
				commentService.add(comment);
			} catch (ServiceException e) {
				throw new ControllerException(e);
			}
		}else{
			attr.addFlashAttribute("org.springframework.validation.BindingResult.comment", result);
			attr.addFlashAttribute("comment", comment);
		}
		return "redirect:/view/" + currentNews + "/" + newsId;
	}
}
