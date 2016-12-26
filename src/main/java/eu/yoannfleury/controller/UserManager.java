package eu.yoannfleury.controller;

import eu.yoannfleury.entity.User;
import eu.yoannfleury.exception.UnprocessableEntityException;
import eu.yoannfleury.exception.UserAlreadyExistsException;
import eu.yoannfleury.exception.UserNotFoundException;
import eu.yoannfleury.exception.WrongPasswordException;
import eu.yoannfleury.repository.UserRepository;
import eu.yoannfleury.security.IJwtUser;
import eu.yoannfleury.security.Password;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.Principal;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping(value = "/user")
public class UserManager {
    public static final String SECRET_KEY = "notif-cosmo-ei";

    private UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final UserLogin login) throws UserNotFoundException {
        if (login.name == null || !this.userRepository.findOneByUserName(login.name).isPresent()) {
            throw new UserNotFoundException(login.name);
        }
        if (!Password.checkPassword(login.password,
                this.userRepository.findOneByUserName(login.name).get().getPassword())) {
            throw new WrongPasswordException();
        }

        return new LoginResponse(Jwts.builder().setSubject(login.name)
                .claim("role", this.userRepository.findOneByUserName(login.name).get().getRole())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact());
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public LoginResponse signup(@RequestBody final User user) {
        validate(user);
        exists(user);

        user.setPassword(Password.hashPassword(user.getPassword()));

        this.userRepository.save(user);

        return new LoginResponse(Jwts.builder().setSubject(user.getUserName())
                .claim("role", this.userRepository.findOneByUserName(user.getUserName()).get().getRole())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact());
    }

    public static class UserLogin {
        public String name;
        public String password;
    }

    public static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }

    /**
     * This will validate the {@link User}.
     *
     * @param user The user to create.
     */
    private void validate(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.size() > 0) {
            String message = "";
            for (ConstraintViolation<User> constraintViolation :
                    constraintViolations) {
                message += ", " + constraintViolation.getRootBeanClass().getSimpleName() + "."
                        + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage();
            }

            throw new UnprocessableEntityException(message);
        }
    }

    /**
     * This will check if {@link User} is unique.
     *
     * @param user The user to create.
     */
    private void exists(User user) {
        if (this.userRepository.findOneByUserName(user.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException(user.getUserName());
        }
        if (this.userRepository.findOneByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }
}
