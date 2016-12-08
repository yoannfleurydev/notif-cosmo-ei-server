package eu.yoannfleury.controller;

import eu.yoannfleury.exception.UserNotFoundException;
import eu.yoannfleury.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class UserManager {
    private UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final UserLogin login) throws UserNotFoundException {
        if (login.name == null || !this.userRepository.findOneByUserName(login.name).isPresent()) {
            throw new UserNotFoundException(login.name);
        }

        return new LoginResponse(Jwts.builder().setSubject(login.name)
                .claim("roles", this.userRepository.findOneByUserName(login.name).get().getRole())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretKey").compact());
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
}
