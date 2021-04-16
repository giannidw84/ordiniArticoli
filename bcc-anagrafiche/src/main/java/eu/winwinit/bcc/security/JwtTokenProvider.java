package eu.winwinit.bcc.security;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import eu.winwinit.bcc.constants.AuthorityRolesConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    public static final long EXIPIRATION_TIME = 43_200_000; //12 ore

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXIPIRATION_TIME))
                .claim("roles", authentication.getAuthorities())
                .claim("attribute1", "ciao")  //TODO: da leggere da tabella se necessario
                .signWith(SignatureAlgorithm.HS512, AuthorityRolesConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(AuthorityRolesConstants.SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature");
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.");
        }

        return false;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(AuthorityRolesConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Set<GrantedAuthority> getRolesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(AuthorityRolesConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();

        List<LinkedHashMap<String, String>> roles = (List<LinkedHashMap<String, String>>) claims.get("roles");

        Set<GrantedAuthority> newList = new HashSet<>();

        for (LinkedHashMap<String, String> grantedAuthority : roles) {

            newList.add(new SimpleGrantedAuthority(grantedAuthority.get("authority")));

        }

        return newList;
    }
}
