package codingrecipe.member.repository;

import codingrecipe.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  MemberRepository extends JpaRepository<MemberEntity, Long> {
} // Entity 클래스의 이름과 PK의 형태를 넘겨줘야 함.
