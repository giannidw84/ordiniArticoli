package eu.winwinit.bcc.advices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

//    @ExceptionHandler(value = { DownloaderInternalException.class })
//    protected ResponseEntity<Object> handleInternalException(Exception ex, WebRequest request) {
//        logger.error("Unexpected error", ex);
//        DownloaderInternalException e = (DownloaderInternalException) ex;
//
//        String bodyOfResponse = "";
//        switch (e.getType()) {
//            case MORE_THAN_ONE_FILE_FOUND:
//                bodyOfResponse = "More than one file found";
//            case INPUT_TOO_LONG:
//                bodyOfResponse = "Input too long";
//        }
//
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        logger.error("Unexpected error", ex);

        String bodyOfResponse = "Something unexpected happened";

        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    
    @ExceptionHandler(value = { AuthenticationException.class })
    protected ResponseEntity<Object> handleAuthenticationException(RuntimeException ex, WebRequest request) {
        logger.error("Unexpected error", ex);
        String bodyOfResponse = "AuthenticationError: "+ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }
}
