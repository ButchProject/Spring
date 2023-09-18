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
    private final SecurityService securityService;


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

    // 내 게시글 수정하기
    // 토큰 email이랑 db에 저장되어있는 이메일이랑 비교해서 되면 그 email에 해당되는 글 전부
    // postentity 가져오기
    //

    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudentToBoard(HttpServletRequest request) {
        String token = securityService.resolveToken(request);
        Claims claims = securityService.validateToken(token); // 토큰 검사

        return ResponseEntity.ok("ok");
    }


}
