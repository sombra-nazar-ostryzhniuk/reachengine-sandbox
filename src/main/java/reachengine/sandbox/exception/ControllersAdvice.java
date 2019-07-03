package reachengine.sandbox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllersAdvice {

    @ExceptionHandler(value = NotFound404Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFound(final NotFound404Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = BadRequest400Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequest(final BadRequest400Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(value = Conflict409Exception.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleConflict(final Conflict409Exception e) {
        return new ErrorResponse(e.getMessage());
    }

}
