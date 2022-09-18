package pl.marketapi.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import pl.marketapi.domain.entity.User;

import java.util.Date;

@Service
public class JwtProvider {


    public String generateJWT(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, System.getenv("SECRET"))
                .compact();
    }

    //TODO verify signature before parsing
    public Jws<Claims> parseJWT(String jwt) throws SignatureException {
        return Jwts.parser()
                .setSigningKey(System.getenv("SECRET"))
                .parseClaimsJws(jwt);
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(System.getenv("SECRET"))
                    .parseClaimsJws(jwt);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
