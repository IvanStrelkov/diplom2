package com.epam.newsmanagement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.newsmanagement.exception.ControllerException;
import com.epam.newsmanagement.exception.ServiceException;
import com.epam.newsmanagement.model.TagTO;
import com.epam.newsmanagement.service.TagService;

@Controller
public class TagsController{
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping(value = "/editTag/{tagId}")
	public String editTag( HttpServletRequest request, ModelMap model, @PathVariable int tagId) throws ControllerException{
		String action = request.getParameter("edit");
		try {
			if("update".equals(action)){
				String tagName = request.getParameter("tagName" + tagId);
				TagTO tag = new TagTO(tagId, tagName);
				tagService.update(tag);
			}else if("remove".equals(action)){
				tagService.remove(tagId);
			}
		}catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return "redirect:/listTags";
	}

	@RequestMapping(value = "/addTag")
	public String addTag(HttpServletRequest request) throws ControllerException{
		String tagName = request.getParameter("tagName");
		TagTO tag = new TagTO(0, tagName);
		try {
			tagService.add(tag);
		} catch (ServiceException e) {
			throw new ControllerException(e);
		}
		return "redirect:/listTags";
	}

}
