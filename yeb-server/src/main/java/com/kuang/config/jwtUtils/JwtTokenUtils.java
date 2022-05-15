package com.kuang.config.jwtUtils;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {

    private  static final String CLAIM_KEY_USERNAME = "sub";   //用户名的key
    private  static final String CLAIM_KEY_CREATED = "created";   //jwt的创建时间

    @Value("${jwt.sercret}")
    private  String sercret;

    @Value("${jwt.expiration}")
    private  Long expiration;


    //根据用户信息生成token
    public String generateToken(UserDetails userDetails){

        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());

        claims.put(CLAIM_KEY_CREATED,new Date());

        return generateToken(claims);

    }

    //根据负载生成token
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256,sercret)
                .compact();
    }


    //失效时间
    private Date generateExpirationDate(){
        //注意是毫秒
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    //生成token，从token中拿到用户名
    public String getUsernameFromToken(String Token){
        String username;

        try {
            Claims claims = getClaimsFromToken(Token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

       return username;
    }
    public Claims getClaimsFromToken(String Token){
        return Jwts.parser()
                .setSigningKey(sercret)
                .parseClaimsJws(Token)
                .getBody();
    }

    //判断token是否过期

    //token是否需要刷新






}
