package codingrecipe.member.service;

import codingrecipe.member.dto.MemberDTO;
import codingrecipe.member.entity.MemberEntity;
import codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); // 상속받는 JpaRepository에 save가 구현되어 있음
        //repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단

         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다.(해당 이메일을 가진 회원 정보가 있다.)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // 비밀번호가 일치하는 경우
                // Entity의 password: 데이터베이스의 비밀번호
                // DTO의 password: 입력받은 비밀번호
                // entity -> DTO 변환후 리턴
                MemberDTO memberDTO1 = MemberDTO.toMemberDTO(memberEntity);
                return memberDTO1;
            }
            else {
                // 비밀번호 불일치
                return null;
            }
        }
        else {
            // 조회 결과가 없다.(해당 이메일을 가진 회원이 없다.)
            return null;
        }
    }

    public List<MemberDTO> findAll(){
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        // Repository와 관련된 것은 Entity 객체로 넘어온다.
        // 우리는 Entity 객체를 DTO 객체로 넘겨줘야 함.
        List<MemberDTO> memberDTOList = new ArrayList<>();

        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }
        return memberDTOList;


    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
            // get 함수를 쓰면 optional로 감싸진 껍데기를 벗길 수 있다.
        }
        else {
            return null;
        }

    }
    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

}
