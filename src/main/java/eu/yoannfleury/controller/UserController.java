package eu.yoannfleury.controller;

import eu.yoannfleury.entity.User;
import eu.yoannfleury.exception.UnprocessableEntityException;
import eu.yoannfleury.exception.UserAlreadyExistsException;
import eu.yoannfleury.exception.UserNotFoundException;
import eu.yoannfleury.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

/**
 * User controller. This controller is made for a simple CRUD on the users.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * {@link User} repository to interact with database.
     */
    private final UserRepository userRepository;

    /**
     * Controller constructor.
     * @param userRepository The user repository to interact with data.
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * <pre>
     *     <code>GET /users</code>
     * </pre>
     *
     * Use this route to get all the users stored in the database.
     *
     * @return All the users stored in the database.
     */
    @RequestMapping
    public List<User> users() {
        return this.userRepository.findAll();
    }

    /**
     * <pre>
     *     <code>GET /users/{id}</code>
     * </pre>
     *
     * Use this route to get the user with the id.
     *
     * @param id Long to identify the user you want to get.
     * @return The user identified by the parameter id.
     */
    @RequestMapping("/{id}")
    public User user(@PathVariable long id) {
        if (this.userRepository.findOne(id) == null) {
            throw new UserNotFoundException(Long.toString(id));
        }

        return this.userRepository.findOne(id);
    }

    /**
     * <pre>
     *     <code>POST /users</code>
     * </pre>
     *
     * Use this route to create a new user.
     *
     * @param user The user to create.
     * @return The newly created {@link User}.
     */
    @RequestMapping(method = RequestMethod.POST)
    public User add(@RequestBody User user) {
        validate(user);
        exists(user);

        this.userRepository.save(user);

        return this.userRepository.findOneByUserName(user.getUserName())
                .orElseThrow(() -> new UserNotFoundException(
                        user.getUserName())
                );
    }

    /**
     * This will validate the {@link User}.
     * @param user The user to create.
     */
    private void validate(User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.size() > 0) {
            String message = "";
            for (ConstraintViolation<User> constraintViolation:
                    constraintViolations){
                message += ", " + constraintViolation.getRootBeanClass().getSimpleName() + "."
                        + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage();
            }

            throw new UnprocessableEntityException(message);
        }
    }

    /**
     * This will check if {@link User} is unique.
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
