package tn.star.rhconge.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.User;
import tn.star.rhconge.Repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return jwtService.generateToken(user);
            }
        }

        throw new RuntimeException("Email ou mot de passe incorrect");
    }
}
