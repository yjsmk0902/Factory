package practice.javapractice.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"), GUEST("ROLE_GUEST"), USER("ROLE_USER");

    private final String key;

    //OAuth2 로그인 시 첫 로그인을 구분하기 위해 Role 설정
    //key 필드 사용 => Spring Security에서 권한 코드에 항상 "ROLE_" 접두사가 붙어야함
}
