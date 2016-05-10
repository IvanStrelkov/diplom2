package by.bsu.strelkov.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;
import by.bsu.strelkov.model.News;
import by.bsu.strelkov.model.Tag;
import by.bsu.strelkov.service.NewsService;

@Controller
public class SaveNewsController{

	@Autowired
	private NewsService newsService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/saveNews", method = RequestMethod.POST)
	public String saveNews(@ModelAttribute("news") News news, BindingResult result, 
			RedirectAttributes attr, HttpServletRequest request) throws DiplomException {
		int newsId;
		int newsNumber = Integer.parseInt(request.getParameter("newsNumber"));
		int authorId = Integer.parseInt(request.getParameter("selectAuthor"));
		String[] tagsId = request.getParameterValues("selectTags"); 
		List<Tag> listTags = new ArrayList<Tag>();
		if(tagsId != null){
			for(String id : tagsId){
				listTags.add(new Tag(Long.parseLong(id), null));
			}
		}
		Author author = new Author(authorId, null);
		if(news.getId() == 0){
			news.setModificationDate(news.getCreationDate());
			newsService.create(news);
		}else{
			newsService.update(news);
		}
		return "redirect:/view/" + newsNumber + "/" + news.getId();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder, Locale loc){
		String pattern = messageSource.getMessage("pattern.date", null, loc);
		SimpleDateFormat date = new SimpleDateFormat(pattern);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(date, false));
	}
}
