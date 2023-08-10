package com.spring.butch.member.controller;

import com.spring.butch.member.dto.MemberDTO;
import com.spring.butch.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor // lombok에서 제공
public class ProfileController {

    private final MemberService memberService;
    @PostMapping("/edit")
    public ResponseEntity<Void> update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return ResponseEntity.ok().build();
    }

}
