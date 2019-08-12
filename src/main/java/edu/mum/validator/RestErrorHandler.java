package edu.mum.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class RestErrorHandler {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DomainErrors processRestExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();

        DomainErrors domainErrors = new DomainErrors();
        domainErrors.setErrorType("ValidationError");

        for (FieldError error : errors) {
            domainErrors.addErrorField(error.getField(),
                    messageSourceAccessor.getMessage(error));
        }

        return domainErrors;
    }
}

