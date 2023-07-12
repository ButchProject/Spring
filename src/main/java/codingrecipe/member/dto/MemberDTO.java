package codingrecipe.member.dto;

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
}
