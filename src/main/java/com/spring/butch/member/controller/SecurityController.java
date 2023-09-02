package com.spring.butch.member.controller;

import com.spring.butch.member.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    private SecurityService securityService;

    @GetMapping("/get/subject")
    public Map<String, Object> getSubject(@RequestParam(value = "token") String token){
        String subject = securityService.getSubject(token); // 2분으로 설정
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", subject);
        return map;
    }
    @PostMapping("/verifyToken")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");

        try {
            // JWT 라이브러리를 사용하여 토큰 파싱 및 검증
            String memberEmail = securityService.getSubject(token);
//            Claims claims = Jwts.parser().setSigningKey("kajjknkjqwerbasdflkqljwrjasdkfashkjdgfhgdslkaglefwauigvlbscjb").parseClaimsJws(token).getBody();
            // 추가적인 클레임 정보 확인 (옵션)
//            String memberEmail = claims.get("memberEmail", String.class);

            // 토큰이 유효한 경우
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("valid", true);
            response.put("memberEmail", memberEmail); // 필요한 경우 클라이언트에게 추가 정보를 전달할 수 있음

            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            // 토큰이 유효하지 않은 경우
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("valid", false);

            return ResponseEntity.ok(response);
        }
    }
}

