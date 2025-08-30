package cat.itacademy.blackjack_api.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public Mono<ResponseEntity<ResponseError>> handleAlreadyExists(AlreadyExistsException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(body));
    }

    @ExceptionHandler(EmptyFieldException.class)
    public Mono<ResponseEntity<ResponseError>> handleEmptyField(EmptyFieldException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(InvalidInputException.class)
    public Mono<ResponseEntity<ResponseError>> handleInvalidInput(InvalidInputException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ResponseError>> handleNotFound(NotFoundException e) {
        ResponseError body = new ResponseError(e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(body));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ValidationResponseError>> handleValidationException(
            WebExchangeBindException e) {
        List<ValidationError> errors =
                e.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList();
        ValidationResponseError body = new ValidationResponseError(errors);
        return Mono.just(ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ResponseError>> handleGeneric(Exception e) {
        ResponseError body = new ResponseError(e.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body));
    }
}
