package com.epam.newsmanagement.controller;

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

import com.epam.newsmanagement.exception.ControllerException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;
import com.epam.newsmanagement.model.NewsTO;
import com.epam.newsmanagement.model.NewsVO;
import com.epam.newsmanagement.model.TagTO;
import com.epam.newsmanagement.service.NewsVOService;
import com.epam.newsmanagement.validator.NewsValidator;

@Controller
public class SaveNewsController{
	
	@Autowired
	private NewsVOService newsVOService;
	@Autowired
	private NewsValidator newsValidator;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = "/saveNews", method = RequestMethod.POST)
	public String saveNews(@ModelAttribute("news") NewsTO news, BindingResult result, 
			RedirectAttributes attr, HttpServletRequest request) throws ControllerException {
		int newsId;
		int newsNumber = Integer.parseInt(request.getParameter("newsNumber"));
		newsValidator.validate(news, result);
		if(!result.hasErrors()){
			int authorId = Integer.parseInt(request.getParameter("selectAuthor"));
			String[] tagsId = request.getParameterValues("selectTags"); 
			List<TagTO> listTags = new ArrayList<TagTO>();
			if(tagsId != null){
				for(String id : tagsId){
					listTags.add(new TagTO(Integer.parseInt(id), null));
				}
			}
			AuthorTO author = new AuthorTO(authorId, null);
			NewsVO newsVO = new NewsVO(author, news, null, listTags);
			try {
				if(news.getNewsId() == 0){
					news.setModificationDate(news.getCreationDate());
					newsId = newsVOService.add(newsVO);
				}else{
					newsId = news.getNewsId();
					newsVOService.update(newsVO);
				}
			} catch (ServiceException e) {
				throw new ControllerException(e);
			}
			return "redirect:/view/" + newsNumber + "/" + newsId;
		}else{
			attr.addFlashAttribute("org.springframework.validation.BindingResult.news", result);
			attr.addFlashAttribute("news", news);
			if(news.getNewsId() == 0){
				return "redirect:/addNews";
			}else{
				return "redirect:/editNews/" + newsNumber + "/" + news.getNewsId();
			}
		}
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder, Locale loc){
		String pattern = messageSource.getMessage("pattern.date", null, loc);
	    SimpleDateFormat date = new SimpleDateFormat(pattern);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(date, false));
	}
}
