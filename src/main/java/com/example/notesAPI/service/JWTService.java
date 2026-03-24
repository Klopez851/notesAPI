package com.example.notesAPI.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

//    @Value("${JWT_KEY}")
    private String secretKey;

    public JWTService(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");//the key needs to be a certain size for this algo
            SecretKey sk = keyGenerator.generateKey();
            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded()); //key is first generated and encoded in base64

        }catch (Exception e){
            System.out.println(e);
        }

    }

    public String generateToken( String username){
        //String = key, object = value bc it will be extracted from different objects depending on the user
        Map<String,Object> claims = new HashMap<>();//used to add custom claims to the jwt payload

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (5*60*1000)))//first into seconds, then into minute
                .and()
                .signWith(getKey())
                .compact();//generates the token

    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); //key is then decoded and used where needed
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return "";
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return true;
    }
}
