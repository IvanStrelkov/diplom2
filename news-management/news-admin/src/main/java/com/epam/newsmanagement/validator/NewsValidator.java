package com.epam.newsmanagement.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.newsmanagement.model.NewsTO;

@Component
public class NewsValidator implements Validator {
 
    @Override
    public boolean supports(Class<?> aClass) {
        return NewsTO.class.isAssignableFrom(aClass);
    }
 
    @Override
    public void validate(Object o, Errors errors) {
    	NewsTO news = (NewsTO)o;
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "validate.news.titleEmpty");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shortText", "validate.news.shortEmpty");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullText", "validate.news.fullEmpty");
    	/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creationDate", "validate.news.DateEmpty");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "modificationDate", "validate.news.DateEmpty");
    	*/if ( ! errors.hasFieldErrors("title")) {
			if (news.getTitle().length() > 30)
				errors.rejectValue("title", "validate.news.titleBig");
		}
    	if ( ! errors.hasFieldErrors("shortText")) {
			if (news.getTitle().length() > 100)
				errors.rejectValue("shortText", "validate.news.shortBig");
		}
    	if ( ! errors.hasFieldErrors("fullText")) {
			if (news.getTitle().length() > 2000)
				errors.rejectValue("title", "validate.news.fulllBig");
		}
    }
 
}