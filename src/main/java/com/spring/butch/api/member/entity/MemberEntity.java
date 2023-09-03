package com.spring.butch.api.member.entity;

import com.spring.butch.api.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(unique = true, name = "member_email") // unique 제약조건 추가
    private String memberEmail;

    @Column(name = "member_password")
    private String memberPassword;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "academy_name")
    private String academyName;

    @Column(name = "phone_number")
    private String phoneNumber;

    // memberDTO객체를 memberEntity로 바꾸는 함수를 만들어야 함
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setPhoneNumber(memberDTO.getPhoneNumber());
        memberEntity.setAcademyName(memberDTO.getAcademyName());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setPhoneNumber(memberDTO.getPhoneNumber());
        memberEntity.setAcademyName(memberDTO.getAcademyName());
        return memberEntity;
    }
}
