package eu.yoannfleury.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    /**+
     *
     * @param userName The user name the application can not find.
     */
    public UserNotFoundException(String userName) {
        super("Could not find user '" + userName + "'.");
    }
}