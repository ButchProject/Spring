package com.spring.butch.member.controller;

import com.spring.butch.member.dto.MemberDTO;
import com.spring.butch.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor // lombok에서 제공
public class ProfileController {

    private final MemberService memberService;
    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable Long id) {
        // 뒤에 {id}위치에 쿼리 파라미터로 주면 한 명의 회원 정보만을 받을 수 있음
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping("/{id}/edit")
    public ResponseEntity<Void> update(@PathVariable Long id, @ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return ResponseEntity.ok().build();
    }

}
