package by.bsu.strelkov.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import by.bsu.strelkov.exception.DiplomException;
import by.bsu.strelkov.model.News;
import by.bsu.strelkov.service.NewsService;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<News> readAll() {
		return newsService.readAll();
    }

	@RequestMapping(value = "/{news_id}", method = RequestMethod.GET)
	@ResponseBody
	public News read(Model model, @PathVariable long news_id) {
		return newsService.read(news_id);
    }

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public News create(@RequestBody News news) throws DiplomException {
		return newsService.create(news);
    }

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public News update(@RequestBody News news) throws DiplomException {
		return newsService.update(news);
    }

	@RequestMapping(value = "/{news_id}",method = RequestMethod.DELETE)
	public @ResponseBody void delete(Model model, @PathVariable long news_id) {
		newsService.delete(news_id);
    }
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public List<News> filterNews(
			 @RequestParam(value = "start", required = true) int start,
			 @RequestParam(value = "count", required = true) int count,
			 @RequestParam(value = "tagsId", required = false) String tagsId,
			 @RequestParam(value = "authorId", required = false) String authorId) {
		List<Long> ids = null; 
		if (!"".equals(tagsId)) {
			ids = new ArrayList<Long>();
			for (String tagId : tagsId.split(",")) {
				ids.add(Long.parseLong(tagId));
			}
		}
		long aId = 0;
		if (!"0".equals(authorId)) {
			aId = Long.parseLong(authorId);
		}
		return newsService.search(start, count, ids, aId);
    }
}
