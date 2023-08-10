package codingrecipe.member.controller;

import codingrecipe.member.dto.LoginDTO;
import codingrecipe.member.dto.MemberDTO;
import codingrecipe.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor // lombok에서 제공
public class MemberController {



    // 생성자 주입
    private final MemberService memberService;

    // 회원 가입 페이지 출력 요청
    @GetMapping("/register")
    public String saveForm() {
        System.out.println("login form");
        return "save";
    }

    @PostMapping("/api/register")
    public String save(@RequestBody MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);

        return "login";
    }


    @PostMapping("/api/login")
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

    @GetMapping("/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // html로 가져갈 데이터가 있다면 model을 많이 사용함.
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        // 경로상의 값을 가져올 때 @PathVariable을 많이 사용함.
        // id에 해당하는 객체를 DB에서 가져와서 화면에 뿌려야하기 때문에 Model 객체가 필요함.
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
        // redirect 뒤에는 주소가 와야 함(html이름이 오면 안됨)
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        // 세션을 무효화하고 처음으로 돌아감
        // 실제로는 이런 방식의 로그아웃을 사용하지 않음.
        session.invalidate();
        return "index";
    }
}