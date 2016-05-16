package by.bsu.strelkov.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping(value = "/view/{news_id}")
	public String viewNews(ModelMap model, @PathVariable long news_id) {
		model.addAttribute("news_id", news_id);
		return "viewNews";
	}
}
