package com.example.WebChat.Security;

import com.example.WebChat.Entity.Users;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CreateJwt {

    protected final static String JWT_SECRET = "mysupersecretkeywithwhatthefuckf3jcogrvftfgrgfddsertfftjvmiui";

    public String createJwt(String jwtCredentials){
        Map<String,Object> credentials=new HashMap<>();
        credentials.put("username",jwtCredentials);

        return Jwts.builder().addClaims(credentials).setSubject(jwtCredentials)
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+60*60*1000))
                .signWith(getJwtSecret()).compact();
    }
    public SecretKey getJwtSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }
}
