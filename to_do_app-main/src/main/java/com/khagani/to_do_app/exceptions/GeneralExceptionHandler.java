package com.khagani.to_do_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GeneralExceptionHandler  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date() ,ex.getBindingResult().getFieldError().getField().toString() , null);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  @NotNull HttpHeaders headers,
//                                                                  @NotNull HttpStatusCode status,
//                                                                  @NotNull WebRequest request
//    ) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String>  userNotFoundExceptionHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
