package com.glodon.jwt.utils;

import com.glodon.jwt.entity.CheckResult;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @Author: yinjw-b
 * @Date: 2021/1/25 15:03
 * @Description:
 */
public class JwtUtils {
    /**
     * 签发Jwt
     * @param id
     * @param subject   可以是JSON数据 尽可能少
     * @param ttlMills
     * @return
     */
    public static String createJWT(String id, String subject, long ttlMills){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        SecretKey secretKey = generalKey();

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)    // 主题
                .setIssuer("user")  // 签发者
                .setIssuedAt(now)   // 签发时间
                .signWith(signatureAlgorithm, secretKey);   // 签名算法及密钥

        if(ttlMills >= 0){
            long expMills = nowMills + ttlMills;
            Date expDate = new Date(expMills);
            builder.setExpiration(expDate);     // 过期时间
        }

        return builder.compact();
    }

    /**
     * 生成密钥
     * @return
     */
    public static SecretKey generalKey(){
        byte[] encodeKey = Base64.decode("JaveeSec");
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        return key;
    }

    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJwt(String jwtStr){
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode("JWT_ERRCODE_EXPIRE");
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode("JWT_ERRCODE_FAIL");
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode("JWT_ERRCODE_FAIL");
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 解析JWT字符串
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt){
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
