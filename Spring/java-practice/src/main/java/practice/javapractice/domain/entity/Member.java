package practice.javapractice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Table(name="USERS")
//  user 키워드가 예약어로 지정되어 있는 경우가 있음 (엔티티 이름을 user로 설정할 경우)
//  엔티티 매핑시 오류가 날 수도 있어서 걍 맘편하게 Member 사용
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String email;
    private String password;
    private String username;
    private String name;
    private String imageProfile;

    //추가 정보
    //  자체 로그인시 해당 정보들을 입력받지만 OAuth2 로그인은 해당 정보를
    //  따로 받지 않으므로 이후에 추가 정보 입력 폼을 구현해야 함
    private int age;
    private String city;

    //Role => GUEST, USER 구분
    //  자체 로그인 시 => 상관없이 모두 USER
    //  OAuth2 로그인 시 => 첫 로그인 시에 GUEST로 설정, 추후에 정보 입력 시 User로 업데이트
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;      //KAKAO, NAVER, GOOGLE

    private String socialId; //로그인한 소셜 타입의 식별자 값 (일반 로그인의 경우 null)

    private String refreshToken; //리프레시 토큰

    //유저 권한 설정
    public void authorizeUser() {
        this.role = Role.USER;
    }

    //비밀번호 암호화
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    //Refresh Token 재발급
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }


}
