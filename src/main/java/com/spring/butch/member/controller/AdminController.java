package com.spring.butch.member.controller;

import com.spring.butch.member.service.MemberService;
import com.spring.butch.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor // lombok에서 제공
public class AdminController {
    // 생성자 주입
    private final MemberService memberService;
    @GetMapping("/RegisterInfo")
    public ResponseEntity<List<MemberDTO>> findAll() {
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
        List<MemberDTO> memberDTOList = memberService.findAll();
        return ResponseEntity.ok(memberDTOList);
    }


    @GetMapping("/RegisterInfo/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable Long id) {
        // 뒤에 {id}위치에 쿼리 파라미터로 주면 한 명의 회원 정보만을 받을 수 있음
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/RegisterInfo/{id}")
    public void deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
    }
}
