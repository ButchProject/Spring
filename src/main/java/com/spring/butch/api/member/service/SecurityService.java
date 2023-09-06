package com.spring.butch.api.member.service;

import com.spring.butch.api.member.dto.MemberDTO;
import com.spring.butch.api.member.exception.InvalidTokenException;
import com.spring.butch.api.member.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class SecurityService {

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    private MemberService memberService;

    public String createToken(String subject, long expTime){
        if(expTime < 0){
            throw new RuntimeException("만료시간이 0보다 커야합니다.");

        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                // 아이디를 subject로 쓰고, 비밀번호를 secretkey를 만드는데 써도 됨.
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public Claims validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            if (now.after(expirationDate)) {
                throw new TokenExpiredException("Token has expired");
            }

            return claims;
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid token");
        }
    }
    // 보통 토큰 검증하는 매서드를 boolean 으로 만들어 사용한다.
    public String getSubject(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
