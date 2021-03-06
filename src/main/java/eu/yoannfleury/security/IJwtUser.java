package eu.yoannfleury.security;

import eu.yoannfleury.entity.User;
import eu.yoannfleury.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface IJwtUser {
    User getUser(HttpServletRequest request) throws UserNotFoundException;
}
