package by.bsu.strelkov.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.Tag;
import by.bsu.strelkov.service.TagService;

@Controller
public class TagsController{

	@Autowired
	private TagService tagService;

	@RequestMapping(value = "/editTag/{tagId}")
	public String editTag( HttpServletRequest request, ModelMap model, @PathVariable long tagId) throws DiplomException{
		String action = request.getParameter("edit");
		if("update".equals(action)){
			String tagName = request.getParameter("tagName" + tagId);
			Tag tag = new Tag(tagId, tagName);
			tagService.update(tag);
		}else if("remove".equals(action)){
			tagService.delete(tagId);
		}
		return "redirect:/listTags";
	}

	@RequestMapping(value = "/addTag")
	public String addTag(HttpServletRequest request) throws DiplomException{
		String tagName = request.getParameter("tagName");
		Tag tag = new Tag(0, tagName);
		tagService.create(tag);
		return "redirect:/listTags";
	}

}
