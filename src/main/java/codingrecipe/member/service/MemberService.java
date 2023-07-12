package codingrecipe.member.service;

import codingrecipe.member.dto.MemberDTO;
import codingrecipe.member.entity.MemberEntity;
import codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        //repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)
    }
}
