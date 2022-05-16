package com.kuang.config.jwtUtils;




import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {

    private  static final String CLAIM_KEY_USERNAME = "sub";   //用户名
    private  static final String CLAIM_KEY_CREATED = "created";   //创建时间

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
    public String getUsernameFromToken(String token){
        String username;

        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

       return username;
    }
    private Claims getClaimsFromToken(String token){
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(sercret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
    //token是否过期
    //username和token中的用户名是否一致
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        if(username.equals(userDetails.getUsername())&&!isExpiration(token)){
            return  true;
        }else{
            return false;
        }
    }

    //token是否需要刷新
     public  boolean canFlush(String token){
        return !isExpiration(token);
     }

     public String refreshToken(String token){
         Claims claims = getClaimsFromToken(token);
         claims.put(CLAIM_KEY_CREATED,new Date());
         return generateToken(claims);
     }


    // token是否失效
    public boolean isExpiration(String token){
        Date date = getExpirationDate(token);
        return date.before(new Date());
    }

    public Date getExpirationDate(String token){
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration;
    }






}
