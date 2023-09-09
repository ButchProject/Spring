package com.spring.butch.api.member.controller;

import com.spring.butch.api.member.dto.MemberDTO;
import com.spring.butch.api.member.exception.InvalidTokenException;
import com.spring.butch.api.member.exception.TokenExpiredException;
import com.spring.butch.api.member.service.MemberService;
import com.spring.butch.api.member.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor // lombok에서 제공
public class AdminController {
    // 생성자 주입
    private final MemberService memberService;
    private final SecurityService securityService;

    @GetMapping("/RegisterInfo")
    public ResponseEntity<List<MemberDTO>> findAll(HttpServletRequest request) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);
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
