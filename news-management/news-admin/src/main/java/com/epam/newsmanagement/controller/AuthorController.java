package com.epam.newsmanagement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.newsmanagement.exception.ControllerException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.AuthorTO;
import com.epam.newsmanagement.service.AuthorService;

@Controller
public class AuthorController{
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(value = "/editAuthor/{authorId}")
	public String editAuthor( HttpServletRequest request, ModelMap model, @PathVariable int authorId) throws ControllerException{
		String action = request.getParameter("edit");
		try {
			if("update".equals(action)){
				String tagName = request.getParameter("authorName" + authorId);
				AuthorTO author = new AuthorTO(authorId, tagName);
				authorService.update(author);
			}else if("remove".equals(action)){
				authorService.remove(authorId);
			}
		}catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return "redirect:/listAuthors";
	}

	@RequestMapping(value = "/addAuthor")
	public String addAuthor(HttpServletRequest request) throws ControllerException{
		String authorName = request.getParameter("authorName");
		AuthorTO author = new AuthorTO(0, authorName);
		try {
			authorService.add(author);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return "redirect:/listAuthors";
	}
}
