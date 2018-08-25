package com.baomidou.springboot.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
/**
* @Description:    jwt使用类
* @Author:         LiHaitao
* @CreateDate:     2018/8/25 21:47
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public class JwtHelper {
	
	public static SecretKey generalKey(){
        byte[] encodedKey = Base64.decodeBase64("qinsent1q2we3");
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
	
	public static Claims parseJWT(String jsonWebToken) {
		try {
			Claims claims = Jwts.parser().setSigningKey(generalKey())
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}

	public static String createJWT(String name, String userId,
			long TTLMillis) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		// 添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
				.claim("name", name)
				.claim("id", userId)
				.setIssuer("qinsent")
	            .setAudience("client")
				.signWith(signatureAlgorithm, generalKey());
		// 添加Token过期时间
		if (TTLMillis >= 0) {
			long expMillis = nowMillis + TTLMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp).setNotBefore(now);
		}

		// 生成JWT
		return builder.compact();
	}
}
