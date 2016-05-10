package com.epam.newsmanagement.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.epam.newsmanagement.model.CommentTO;

@Component
public class CommentValidator implements Validator {
 
    @Override
    public boolean supports(Class<?> aClass) {
        return CommentTO.class.isAssignableFrom(aClass);
    }
 
    @Override
    public void validate(Object o, Errors errors) {
    	CommentTO comment = (CommentTO)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commentText", "validate.commentEmpty");
        if ( ! errors.hasFieldErrors("commentText")) {
			if (comment.getCommentText().length() > 100)
				errors.rejectValue("commentText", "validate.commentBig");
		}
    }
 
}