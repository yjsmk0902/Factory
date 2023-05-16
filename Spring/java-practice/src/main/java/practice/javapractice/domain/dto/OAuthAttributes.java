package practice.javapractice.domain.dto;

import lombok.Builder;
import lombok.Data;
import practice.javapractice.domain.entity.Member;
import practice.javapractice.domain.entity.Role;
import practice.javapractice.domain.entity.SocialType;
import practice.javapractice.domain.userinfo.GoogleOAuth2UserInfo;
import practice.javapractice.domain.userinfo.KakaoOAuth2UserInfo;
import practice.javapractice.domain.userinfo.NaverOAuth2UserInfo;
import practice.javapractice.domain.userinfo.OAuth2UserInfo;

import java.util.Map;
import java.util.UUID;

@Data
//소셜에서 받아오는 데이터가 각각 다르므로, 소셜 별로 데이터 분기 처리를 하는 DTO 클래스
public class OAuthAttributes {

    private String nameAttributeKey;    //OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oAuth2UserInfo;  //소셜 타입별 로그인 유저 정보 (닉네임, 이메일, 프로필 사진 등등)

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        //들어온 socialType을 분기별로 처리
        switch (socialType) {
            case NAVER : return ofNaver(userNameAttributeName, attributes);
            case KAKAO : return ofKakao(userNameAttributeName, attributes);
            case GOOGLE : return ofGoogle(userNameAttributeName, attributes);
            default: return null;
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    //of 메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
    public Member toEntity(SocialType socialType, OAuth2UserInfo oAuth2UserInfo) {
        return Member.builder()
                .socialType(socialType)

                //OAuth2UserInfo에서 해당 값들을 가져와서 빌드
                .socialId(oAuth2UserInfo.getId())
                //email에는 UUID로 중복 없는 랜덤 값 생성 (JWT를 발급하기 위한 용도)
                .email(UUID.randomUUID() + "@socialUser.com")
                .username(oAuth2UserInfo.getNickname())
                .imageProfile(oAuth2UserInfo.getImageUrl())

                //role은 GUEST
                .role(Role.GUEST)
                .build();
    }
}
