package com.spring.butch.member.controller;

import com.spring.butch.member.exception.InvalidTokenException;
import com.spring.butch.member.exception.TokenExpiredException;
import com.spring.butch.member.service.MemberService;
import com.spring.butch.member.dto.MemberDTO;
import com.spring.butch.member.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor // lombok에서 제공
public class AdminController {
    // 생성자 주입
    private final MemberService memberService;
    private final SecurityService securityService;
    @GetMapping("/RegisterInfo")
    public ResponseEntity<List<MemberDTO>> findAll(@RequestHeader(value = "Authorization") String token) {
        /**
         * /admin/registerInfo로 요청시 회원들의 데이터를 JSON방식으로 보냄
         * ex)
         *      {
         *         "id": 3,
         *         "memberEmail": "kkm32543@khu.ac.kr",
         *         "memberPassword": "1234",
         *         "memberName": "김근민",
         *         "phoneNumber": "01067295114",
         *         "academyName": "김근민수학학원"
         *     },
         */
        // "Bearer " 제거
        String jwtToken = token.replace("Bearer ", "");

        // 토큰 유효성 검사 및 처리 로직
        try {
            // 1. JWT 라이브러리를 활용하여 토큰 파싱
            Claims claims = Jwts.parser().setSigningKey("kajjknkjqwerbasdflkqljwrjasdkfashkjdgfhgdslkaglefwauigvlbscjb").parseClaimsJws(jwtToken).getBody();

            // 2. 서명 검증 (옵션)
            // ...

            // 3. 만료 시간 확인
            Date expirationDate = claims.getExpiration();
            Date now = new Date();
            if (now.after(expirationDate)) {
                throw new TokenExpiredException("Token has expired");
            }

        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid token");
        }
        List<MemberDTO> memberDTOList = memberService.findAll();
        return ResponseEntity.ok(memberDTOList);
    }


    @GetMapping("/registerInfo/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable Long id) {
        // 뒤에 {id}위치에 쿼리 파라미터로 주면 한 명의 회원 정보만을 받을 수 있음
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/registerInfo/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
