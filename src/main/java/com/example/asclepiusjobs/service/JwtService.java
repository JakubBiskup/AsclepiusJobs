package com.example.asclepiusjobs.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    // this should be stored somewhere safe, change it later
    String secretString="not a well kept secret! But it is quite a long string, and that's because the key needs to be equal or larger than 256 bits.";

    private Key key= Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    //  private Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createToken(UserDetails userDetails){
        String jws= Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .claim("active", userDetails.isEnabled())
                .signWith(key)
                .compact();
        return jws;
    }

    private Claims getAllClaims(String jws){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody();
    }

    public String extractUsername(String jws){
        return getAllClaims(jws).getSubject();
    }

    public boolean validateToken(String jws, UserDetails userDetails){
        String usernameFromToken= extractUsername(jws);
        String usernameFromUserDetails=userDetails.getUsername();
        boolean isValid=false;
        if(usernameFromToken.equals(usernameFromUserDetails) && new Date(System.currentTimeMillis()).before(getAllClaims(jws).getExpiration())){
            isValid=true;
        }
        return isValid;
    }



}
