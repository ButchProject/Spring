package codingrecipe.member.dto;

import codingrecipe.member.entity.MemberEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
    // 회원 정보에 필요한 것들을 필드로 정리하고 필드를 간접적으로 접근하도록 함.
    // lombok의 기능
    private Long personId;
    private String academy;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;


    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setAcademy(memberEntity.getAcademy());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setPassword(memberEntity.getPassword());
        memberDTO.setPhoneNumber(memberEntity.getPhoneNumber());

        return memberDTO;
    }
}
