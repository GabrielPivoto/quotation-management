package br.com.inatel.quotationManagement.handler;

import br.com.inatel.quotationManagement.exception.StockNotFoundException;
import br.com.inatel.quotationManagement.model.dto.ErrorDto;
import br.com.inatel.quotationManagement.model.dto.StockDtoError;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class Handler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<StockDtoError> handle(MethodArgumentNotValidException exception){
        List<StockDtoError> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            StockDtoError error = new StockDtoError(e.getField(),message);
            errors.add(error);
        });
        return errors;
    }

    @ExceptionHandler(StockNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto generateException(StockNotFoundException e){
        ErrorDto dto = new ErrorDto();
        dto.setStatus(HttpStatus.NOT_FOUND);
        dto.setErrorMessage(e.getMessage());
        return dto;
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto generateException(DateTimeParseException e){
        ErrorDto dto = new ErrorDto();
        dto.setStatus(HttpStatus.BAD_REQUEST);
        dto.setErrorMessage("Date doesn't exist or is in invalid format");
        return dto;
    }

    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDto generateException(JDBCConnectionException e){
        ErrorDto dto = new ErrorDto();
        dto.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
        dto.setErrorMessage(e.getMessage());
        return dto;
    }

}
