package com.example.asclepiusjobs.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    private String secretString=getSecretStringFromTextFile();
    private Key key= Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

    public JwtService() throws Exception {
    }

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

    private String getSecretStringFromTextFile() throws Exception {
        try{
            Path path=Path.of("../JWTsecret.txt");
            return Files.readString(path);
        }catch (Exception e){
            System.out.println("Problem reading secret string");
            e.printStackTrace();
            throw new Exception("Cant read secret JWT string");
        }
    }


}
