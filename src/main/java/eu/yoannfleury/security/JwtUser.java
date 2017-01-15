package eu.yoannfleury.security;

import eu.yoannfleury.controller.UserManager;
import eu.yoannfleury.entity.User;
import eu.yoannfleury.exception.UserNotFoundException;
import eu.yoannfleury.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@Service
public class JwtUser implements IJwtUser {
    private UserRepository userRepository;

    @Autowired
    public JwtUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(HttpServletRequest request) throws UserNotFoundException {
        final String authHeader = request.getHeader("Authorization");
        final String token = authHeader.substring(7); // The part after "Bearer "

        try {
            final Claims claims = Jwts.parser().setSigningKey(UserManager.SECRET_KEY)
                    .parseClaimsJws(token).getBody();
            Optional<User> userOptional = this.userRepository.findOneByUserName(claims.getSubject());

            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new UserNotFoundException(claims.getSubject());
            }
        } catch (final SignatureException e) {
            throw new RuntimeException("Invalid token.");
        }
    }
}
