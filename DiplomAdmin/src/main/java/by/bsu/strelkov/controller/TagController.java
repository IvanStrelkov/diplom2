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
import by.bsu.strelkov.model.Tag;
import by.bsu.strelkov.service.TagService;

@Controller
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	private TagService tagService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Tag> readAll() {
		return tagService.readAll();
    }

	@RequestMapping(value = "/{tag_id}", method = RequestMethod.GET)
	@ResponseBody
	public Tag read(Model model, @PathVariable long tag_id) {
		return tagService.read(tag_id);
    }

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Tag create(Model model, @ModelAttribute Tag tag) throws DiplomException {
		return tagService.create(tag);
    }

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Tag update(Model model, @ModelAttribute Tag tag) throws DiplomException {
		return tagService.update(tag);
    }

	@RequestMapping(value = "/{tag_id}",method = RequestMethod.DELETE)
	public @ResponseBody void delete(Model model, @PathVariable long tag_id) {
		tagService.delete(tag_id);
    }
}
