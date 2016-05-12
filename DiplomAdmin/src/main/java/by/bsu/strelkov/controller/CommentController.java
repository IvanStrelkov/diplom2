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
import by.bsu.strelkov.model.Comment;
import by.bsu.strelkov.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Comment> readAll() {
		return commentService.readAll();
    }

	@RequestMapping(value = "/{comment_id}", method = RequestMethod.GET)
	@ResponseBody
	public Comment read(Model model, @PathVariable long comment_id) {
		return commentService.read(comment_id);
    }

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Comment create(Model model, @ModelAttribute Comment comment) throws DiplomException {
		return commentService.create(comment);
    }

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Comment update(Model model, @ModelAttribute Comment comment) throws DiplomException {
		return commentService.update(comment);
    }

	@RequestMapping(value = "/{comment_id}",method = RequestMethod.DELETE)
	public @ResponseBody void delete(Model model, @PathVariable long comment_id) {
		commentService.delete(comment_id);
    }
}
