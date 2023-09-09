package com.spring.butch.api.member.dto;

import com.spring.butch.api.member.entity.MemberEntity;
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
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String phoneNumber;
    private String academyName;
    private Integer numberOfStudents;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setPhoneNumber(memberEntity.getPhoneNumber());
        memberDTO.setAcademyName(memberEntity.getAcademyName());
        memberDTO.setNumberOfStudents(memberEntity.getNumberOfStudents());
        return memberDTO;
    }
}
