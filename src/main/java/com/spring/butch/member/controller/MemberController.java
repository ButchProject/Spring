package com.spring.butch.member.controller;

import com.spring.butch.member.dto.LoginDTO;
import com.spring.butch.member.service.MemberService;
import com.spring.butch.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor // lombok에서 제공
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> save(@RequestBody MemberDTO memberDTO) {
        // 이메일 중복 예외처리 해야 함.
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDTO> login(@RequestBody LoginDTO loginDTO) {
        MemberDTO loginResult = memberService.login(loginDTO);
        if (loginResult != null) {
            // login 성공

            // 성공 시 세션 처리 or 쿠키 처리 기능 추가 필요함.
            return ResponseEntity.ok(loginResult);
        } else {
            // login 실패
            // 예외처리 해야 함.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    /*@GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }*/




    /*@GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션을 무효화하고 처음으로 돌아감
        // 실제로는 이런 방식의 로그아웃을 사용하지 않음.
        session.invalidate();
        return "index";
    }*/
}