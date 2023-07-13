package codingrecipe.member.entity;

import codingrecipe.member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "signup")
public class MemberEntity {
    @Id
    @GeneratedValue
    private Long personId;
    @Column
    private String academy;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String phoneNumber;


    // memberDTO객체를 memberEntity로 바꾸는 함수를 만들어야 함
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setAcademy(memberDTO.getAcademy());
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setName(memberDTO.getName());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setPhoneNumber(memberDTO.getPhoneNumber());


        return memberEntity;
    }
}
