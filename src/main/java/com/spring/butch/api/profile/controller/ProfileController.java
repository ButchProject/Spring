package com.spring.butch.api.profile.controller;

import com.spring.butch.api.member.dto.MemberDTO;
import com.spring.butch.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor // lombok에서 제공
public class ProfileController {

    private final MemberService memberService;

    @GetMapping("/{id}") // 토큰을 받아서 findByEmail하는 로직으로 변경해야 함.
    public ResponseEntity<MemberDTO> findById(@PathVariable Long id) {
        // 뒤에 {id}위치에 쿼리 파라미터로 주면 한 명의 회원 정보만을 받을 수 있음
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping("/{id}/edit") //이것도 id를 받는게 아니라, token을 받아서
    public ResponseEntity<Void> update(@PathVariable Long id, @ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return ResponseEntity.ok().build();
    }
    // 내 게시글 리스트 가져오기



}
