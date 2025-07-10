package tn.star.rhconge.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private static final String SECRET_KEY = "uExLkA2I4frMJoi+xF4wD5q9J2VG5ZVaAZZ5Lk08r7E=";
    private static final long EXPIRATION_TIME = 86400000; // 24h

  /* public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().getNom()) // optionnel
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }*/
  public String generateToken(User user) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("matricule", user.getMatricule());
      claims.put("nom", user.getNom());
      claims.put("prenom", user.getPrenom());
      claims.put("email", user.getEmail());
      claims.put("role", user.getRole() != null ? user.getRole().getNom() : null);
      claims.put("congesRestants", user.getCongesRestants());
      claims.put("congesPris", user.getCongesPris());
      claims.put("congesAnnuelsRestants", user.getCongesAnnuelsRestants());

      if (user.getManager() != null) {
          claims.put("manager", Map.of(
                  "matricule", user.getManager().getMatricule(),
                  "nom", user.getManager().getNom(),
                  "prenom", user.getManager().getPrenom()
          ));
      }

      return Jwts.builder()
              .setClaims(claims)
              .setSubject(user.getEmail()) // or user.getMatricule() if you prefer
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
              .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
              .compact();
  }

}
