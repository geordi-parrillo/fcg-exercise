package it.geordi.fcg_exercise.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "user not found")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }
}
