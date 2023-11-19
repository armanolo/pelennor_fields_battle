package es.mmm.managerartillery.infrastructure.configuration;

import es.mmm.managerartillery.domain.exception.CannonNotAvailableException;
import es.mmm.managerartillery.domain.exception.InvalidCannonResponseException;
import es.mmm.managerartillery.domain.exception.InvalidProtocolTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidProtocolTypeException.class, InvalidCannonResponseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseBody handleConflict(RuntimeException ex) {

        return new ErrorResponseBody(ex.getMessage());

    }

    @ExceptionHandler(value= { CannonNotAvailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected ErrorResponseBody handleNotFoundConflict(RuntimeException ex) {

        return new ErrorResponseBody(ex.getMessage());

    }
}
