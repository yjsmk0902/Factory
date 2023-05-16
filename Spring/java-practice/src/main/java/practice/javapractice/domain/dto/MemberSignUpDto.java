package practice.javapractice.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberSignUpDto {
    private String email;
    private String password;
    private String username;
    private String name;

    //추가 정보
    private int age;
    private String city;

    //자체 회원가입 API에 RequestBody로 사용할 UserSignUpDto를 생성
    //추가 정보인 age와 city도 같이 요청
}
