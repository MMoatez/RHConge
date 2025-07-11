package tn.star.rhconge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Services.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // 🔐 Vérifier que l'e-mail est fourni
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email obligatoire"));
        }

        // ✅ Vérifier que l'e-mail se termine par @star.com.tn
        if (!email.toLowerCase().endsWith("@star.com.tn")) {
            return ResponseEntity.status(403).body(Map.of("error", "Veuillez vous connecter avec un Compte Gmail STAR."));
        }

        // 🔐 Vérifier que le mot de passe est fourni
        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Mot de passe obligatoire"));
        }

        try {
            String token = authService.login(email, password);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

}
