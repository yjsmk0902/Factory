package practice.javapractice.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberLoginDto {
    private String email;
    private String password;

    //로그인 API에 RequestBody로 사용할 MemberLoginDto 생성
    //근데 일단 CustomJsonUsernamePasswordAuthenticationFilter에 위임함
    //LoginSuccessHandler에서 처리되어서 사용할 필요 없을듯
}