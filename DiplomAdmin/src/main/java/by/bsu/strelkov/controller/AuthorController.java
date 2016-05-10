package by.bsu.strelkov.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;
import by.bsu.strelkov.service.AuthorService;

@Controller
public class AuthorController{

	@Autowired
	private AuthorService authorService;

	@RequestMapping(value = "/editAuthor/{authorId}")
	public String editAuthor( HttpServletRequest request, ModelMap model, @PathVariable long authorId) throws DiplomException{
		String action = request.getParameter("edit");
		if("update".equals(action)){
			String tagName = request.getParameter("authorName" + authorId);
			Author author = new Author(authorId, tagName);
			authorService.update(author);
		}else if("remove".equals(action)){
			authorService.delete(authorId);
		}
		return "redirect:/listAuthors";
	}

	@RequestMapping(value = "/addAuthor")
	public String addAuthor(HttpServletRequest request) throws DiplomException{
		String authorName = request.getParameter("authorName");
		Author author = new Author(0, authorName);
		authorService.create(author);
		return "redirect:/listAuthors";
	}
}
