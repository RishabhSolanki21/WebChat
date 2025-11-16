//package com.example.WebChat.Service;
//
//import com.example.WebChat.Security.CreateJwt;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.function.Function;
//
//@Service
//public class JwtClaims {
//
//    @Autowired
//    private CreateJwt createJwt;
//
//    public Date Expiration(String token) {
//        Date date= getClaim(token, Claims::getExpiration);
//        return date;
//    }
//    public boolean isValid(String token) {
//       return Expiration(token).after(new Date());
//    }
//
//    public String getUsername(String token) {
//        return getClaim(token, Claims::getSubject);
//    }
//
//    public Claims claims(String token){
//       return Jwts.parserBuilder().setSigningKey(createJwt.getJwtSecret()).build().parseClaimsJws(token).getBody();
//    }
//
//    public <T>T getClaim(String token, Function<Claims,T> claimType){
//        Claims claim = claims(token);
//        return claimType.apply(claim);
//    }
//
//}
