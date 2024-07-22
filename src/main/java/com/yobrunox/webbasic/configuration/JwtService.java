package com.yobrunox.webbasic.configuration;

import com.yobrunox.webbasic.exception.BusinessException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {

    private static final String SECRET_KEY = "EFDSA45F6D4S5FGDSGFbjDKJNOIGFHDF5F56F4DS56FGGFD544f";
    public String getToken(UserDetails user){
        return getToken(new HashMap<>(),user);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*2))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        //try {
            return getClaim(token, Claims::getSubject);
        //} catch (JwtException | IllegalArgumentException e) {
            // Log the exception or handle it appropriately
            //System.err.println("Token inválido: " + e.getMessage());
        //    throw new BusinessException("403", HttpStatus.FORBIDDEN,"Token inválido o clave incorrecta en fromtoken");

        //}
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username= getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    public Claims getAllClaims(String token)
    {
        /*
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();*/
        //try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
       //} catch (JwtException | IllegalArgumentException e) {
            // Maneja la excepción (por ejemplo, registrar el error o lanzar una excepción personalizada)
        //    throw new BusinessException("403", HttpStatus.FORBIDDEN,"Token inválido o clave incorrecta");
        //}
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }


}