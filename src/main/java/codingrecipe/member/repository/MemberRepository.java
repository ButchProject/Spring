package codingrecipe.member.repository;

import codingrecipe.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  MemberRepository extends JpaRepository<MemberEntity, Long> {
    //이메일로 회원 정보 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    // optional은 null방지 목적으로 사용

} // Entity 클래스의 이름과 PK의 형태를 넘겨줘야 함.
