package com.spring.butch.member.controller;

import com.spring.butch.member.dto.LoginDTO;
import com.spring.butch.member.service.MemberService;
import com.spring.butch.member.dto.MemberDTO;
import com.spring.butch.member.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor // lombok에서 제공
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;
    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> save(@RequestBody MemberDTO memberDTO) {
        // 이메일 중복 예외처리 해야 함.
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        MemberDTO loginResult = memberService.login(loginDTO);
        if (loginResult != null) {
            // login 성공
            String subject = loginDTO.getMemberEmail();
            String token = securityService.createToken(subject, (5*1000*60)); // 5분으로 설정

            Map<String, Object> claims = new HashMap<>();
            claims.put("memberEmail", subject);


            Map<String, Object> map = new LinkedHashMap<>();
            map.put("token", token);
            // 성공 시 세션 처리 or 쿠키 처리 기능 추가 필요함.
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            // login 실패
            // 예외처리 해야 함.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }





    /*@GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션을 무효화하고 처음으로 돌아감
        // 실제로는 이런 방식의 로그아웃을 사용하지 않음.
        session.invalidate();
        return "index";
    }*/
}