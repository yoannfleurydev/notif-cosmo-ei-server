package eu.yoannfleury.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegionNotFoundException extends RuntimeException {
    /**
     * +
     * Throw this exception when the {@link eu.yoannfleury.entity.User} cannot be found in the database.
     *
     * @param userName The user name the application can not find.
     */
    public RegionNotFoundException(String userName) {
        super("Could not find region '" + userName + "'.");
    }
}