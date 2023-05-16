package practice.javapractice.domain.userinfo;

import java.util.Map;
import java.util.Objects;

public abstract class OAuth2UserInfo {

    //해당 클래스를 상속 받는 클래스에서만 이용가능하도록 protected 설정
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();             //소셜 식별 값: Google -> "sub" / Kakao -> "id" / Naver -> "id"

    public abstract String getNickname();

    public abstract String getImageUrl();
}
