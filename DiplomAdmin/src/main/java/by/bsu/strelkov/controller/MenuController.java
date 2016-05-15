package by.bsu.strelkov.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import by.bsu.strelkov.exception.DiplomException;

@Controller
public class MenuController{
	
	@RequestMapping(value = "/")
	public String home(Model model){
		Locale.setDefault(Locale.ENGLISH);
		return "redirect:/portal";
	}

	@RequestMapping(value = "/portal")
	public String portal(Model model){
		return "portal";
	}
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, ModelMap model){
		boolean error = Boolean.parseBoolean(request.getParameter("error"));
		model.addAttribute("error", error);
		return "login";
	}

	@RequestMapping(value = "/listNews")
	public String viewListNews(HttpSession session){
		return "redirect:/portal";
	}

	@RequestMapping(value = "/listTags")
	public String viewListTags( ModelMap model) throws DiplomException{
		return "listTags";
	}

	@RequestMapping(value = "/listAuthors")
	public String viewListAuthors( ModelMap model) throws DiplomException{
		return "listAuthors";
	}
	
	@RequestMapping(value = "/view/{news_id}")
	public String viewNews(ModelMap model, @PathVariable long news_id) {
		model.addAttribute("news_id", news_id);
		return "viewNews";
	}
	
	@RequestMapping(value = "/addNews")
	public String addNews(ModelMap model) {
		return "viewNews";
	}
}
