package practice.javapractice.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import practice.javapractice.domain.dto.OAuthAttributes;
import practice.javapractice.domain.entity.Member;
import practice.javapractice.domain.entity.SocialType;
import practice.javapractice.domain.repository.MemberRepository;
import practice.javapractice.web.oauth2.CustomOAuth2User;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        //DefaultOAuth2UserService 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        //DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내
        //유저 정보를 얻고, DefaultOAuth2User 객체 생성 후 반환
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        //OAuth2User => OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저

        //userRequest에서 registrationId 추출 후 regeistrationId로 SocialType 저장
        //http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId임
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);

        //userNameAttributeName => 후에 nameAttributeKey로 설정됨
        //OAuth2 로그인 시 Key(PK)가 되는 값
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //소셜 로그인에서 API가 제공하는 userInfo의 JSON 값 (유저 정보들)
        Map<String, Object> attributes = oAuth2User.getAttributes();

        //SocialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        //getMember 메서드로 member 생성 후 반환
        Member createdMember = getMember(extractAttributes, socialType);

        //DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdMember.getRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdMember.getEmail(),
                createdMember.getRole()
        );
    }

    //SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드
    //찾은 회원이 있다면 그대로 반환하고, 없다면 saveMember()를 호출하여 회원을 저장
    private Member getMember(OAuthAttributes attributes, SocialType socialType) {

        log.info("getMember() 함수 진입");
        Member findMember = memberRepository.findBySocialTypeAndSocialId(socialType, attributes.getOAuth2UserInfo().getId())
                .orElse(null);

        if (findMember == null) {
            return saveMember(attributes, socialType);
        }
        return findMember;
    }

    //OAuthAttributes의 toEntity() 메소드를 통해 builder로 Member 객체 생성 후 반환
    //생성된 Member 객체를 DB에 저장 : socialType, socialId, email, role 값만 있는 상태
    private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
        Member createdMember = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());
        return memberRepository.save(createdMember);
    }

    private SocialType getSocialType(String registrationId) {
        if (NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if (KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }
}
