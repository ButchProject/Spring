package com.spring.butch.api.profile.controller;

import com.spring.butch.api.member.dto.MemberDTO;
import com.spring.butch.api.member.service.MemberService;
import com.spring.butch.api.member.service.SecurityService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor // lombok에서 제공
public class ProfileController {

    @Autowired
    private final MemberService memberService;

    @Autowired
    private SecurityService securityService;


    @GetMapping("/profile")
    public ResponseEntity<MemberDTO> findById(HttpServletRequest request) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);
        String Email = claims.getSubject();
        MemberDTO memberDTO = memberService.findByEmail(Email);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping("/profile/edit") //이것도 id를 받는게 아니라, token을 받아서
    public ResponseEntity<Void> update(HttpServletRequest request, @RequestBody MemberDTO memberDTO) {
        // "Bearer " 제거
        String token = securityService.resolveToken(request);
        // 토큰 유효성 검사 및 처리 로직
        Claims claims = securityService.validateToken(token);
        memberService.update(memberDTO);
        return ResponseEntity.ok().build();
    }

}
