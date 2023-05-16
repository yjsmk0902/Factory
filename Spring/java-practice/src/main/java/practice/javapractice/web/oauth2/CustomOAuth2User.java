package practice.javapractice.web.oauth2;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import practice.javapractice.domain.entity.Role;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    //Resource Server에서 제공하지 않는 추가 정보(여기에서는 emil)들을
    //내 서비스에 가지고 있기 위해서 DefaultOAuth2User를 직접 사용하지 않고 커스텀함

    private String email;
    private Role role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey,
                            String email, Role role) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
    }

}
