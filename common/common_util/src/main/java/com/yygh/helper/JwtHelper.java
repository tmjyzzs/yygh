package com.yygh.helper;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {

    //token过期时间
    private static long tokenExpiration = 24*60*60*1000;
    //token签名秘钥
    private static String tokenSignKey = "123456";


    /**
     * 根据参数生成token
     * @param userId 用户id
     * @param userName  用户名
     * @return 返回一个token字符串
     */
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("YYGH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }


    /**
     * 根据token字符串得到用户id
     * @param token 前端的token
     * @return 得到用户的id
     */
    public static Long getUserId(String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer)claims.get("userId");
        return userId.longValue();
    }


    /**
     * 根据token字符串得到用户名称
     * @param token 前端token
     * @return 用户名称
     */
    public static String getUserName(String token) {
        if(StringUtils.isEmpty(token)){
            return "";
        }

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("userName");
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "lucy");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}

