package com.spring.butch.api.member.controller;

import com.spring.butch.api.member.dto.MemberDTO;
import com.spring.butch.api.member.service.MemberService;
import com.spring.butch.api.member.service.SecurityService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    @GetMapping("/registerInfo/{id}") // 회원 상세 보기
    public ResponseEntity<MemberDTO> findById(HttpServletRequest request, @PathVariable Long id) {
        // 토큰 검증
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        // 뒤에 {id}위치에 쿼리 파라미터로 주면 한 명의 회원 정보만을 받을 수 있음
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/registerInfo/{id}") // 회원 삭제
    public ResponseEntity<Void> deleteById(HttpServletRequest request, @PathVariable Long id) {
        // 토큰 검증
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token);

        memberService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
