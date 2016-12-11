package eu.yoannfleury.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EffectNotFoundException extends RuntimeException {
    public EffectNotFoundException(String message) {
        super(message);
    }

    public EffectNotFoundException(long id) {
        super(Long.toString(id));
    }
}
