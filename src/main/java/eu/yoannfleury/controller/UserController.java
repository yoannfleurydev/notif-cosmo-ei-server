package eu.yoannfleury.controller;

import eu.yoannfleury.entity.User;
import eu.yoannfleury.exception.UnprocessableEntityException;
import eu.yoannfleury.exception.UserNotFoundException;
import eu.yoannfleury.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping
    public List<User> users() {
        return this.userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User add(@RequestBody User user) {
        validate(user);

        this.userRepository.save(user);

        return this.userRepository.findOneByUserName(user.getUserName())
                .orElseThrow(() -> new UserNotFoundException(
                        user.getUserName())
                );
    }

    private void validate(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.size() > 0) {
            String message = "";
            for (ConstraintViolation<User> constraintViolation:
                    constraintViolations){
                message += ", " + constraintViolation.getRootBeanClass().getSimpleName() + " "
                        + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage();
            }

            throw new UnprocessableEntityException(message);
        }
    }
}
