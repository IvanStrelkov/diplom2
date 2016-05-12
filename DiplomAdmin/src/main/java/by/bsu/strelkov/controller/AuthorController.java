package by.bsu.strelkov.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Author;
import by.bsu.strelkov.service.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {
	
	@Autowired
	private AuthorService authorService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Author> readAll() {
		return authorService.readAll();
    }

	@RequestMapping(value = "/{author_id}", method = RequestMethod.GET)
	@ResponseBody
	public Author read(Model model, @PathVariable long author_id) {
		return authorService.read(author_id);
    }

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Author create(Model model, @ModelAttribute Author author) throws DiplomException {
		return authorService.create(author);
    }

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Author update(Model model, @ModelAttribute Author author) throws DiplomException {
		return authorService.update(author);
    }

	@RequestMapping(value = "/{author_id}",method = RequestMethod.DELETE)
	public @ResponseBody void delete(Model model, @PathVariable long author_id) {
		authorService.delete(author_id);
    }
}
