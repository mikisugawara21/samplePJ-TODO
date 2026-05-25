package sample.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import sample.logic.exception.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException e) {
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnknown(Exception e) {
        log.error("unexpected error", e);
        return "error/500";
    }
}