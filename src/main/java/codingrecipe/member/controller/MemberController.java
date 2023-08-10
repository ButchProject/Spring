package codingrecipe.member.controller;

import codingrecipe.member.dto.LoginDTO;
import codingrecipe.member.dto.MemberDTO;
import codingrecipe.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor // lombok에서 제공
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity<MemberDTO> save(@RequestBody MemberDTO memberDTO) {
        // 이메일 중복 예외처리 해야 함.
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberDTO> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(loginDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            // 세션 기록
            return ResponseEntity.ok(loginResult);
        } else {
            // login 실패
            // 예외처리 해야 함.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> memberDTOList = memberService.findAll();
        return ResponseEntity.ok(memberDTOList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
        // redirect 뒤에는 주소가 와야 함(html이름이 오면 안됨)
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 세션을 무효화하고 처음으로 돌아감
        // 실제로는 이런 방식의 로그아웃을 사용하지 않음.
        session.invalidate();
        return "index";
    }
}