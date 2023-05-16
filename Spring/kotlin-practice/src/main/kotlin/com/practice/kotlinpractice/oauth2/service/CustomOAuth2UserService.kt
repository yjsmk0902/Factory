package com.practice.kotlinpractice.oauth2.service

import com.practice.kotlinpractice.domain.Member
import com.practice.kotlinpractice.domain.MemberRepository
import com.practice.kotlinpractice.domain.SocialType
import com.practice.kotlinpractice.oauth2.CustomOAuth2User
import com.practice.kotlinpractice.oauth2.OAuthAttributes
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
@Transactional
class CustomOAuth2UserService(
    val memberRepository: MemberRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    companion object {
        const val NAVER = "naver"
        const val KAKAO = "kakao"
        const val GOOGLE = "google"
    }

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        println("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입")

        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val socialType = getSocialType(registrationId)
        val userNameAttributeName = userRequest.clientRegistration
            .providerDetails.userInfoEndpoint.userNameAttributeName
        val attributes = oAuth2User.attributes

        val extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes)
        val createdMember = getMember(extractAttributes, socialType)

        return CustomOAuth2User(
            setOf(SimpleGrantedAuthority(createdMember.role.name)),
            attributes,
            extractAttributes.nameAttributeKey,
            createdMember.email,
            createdMember.role
        )
    }

    private fun getMember(attributes: OAuthAttributes, socialType: SocialType): Member {
        val findMember = attributes.oAuth2UserInfo.getId()
            ?.let {
                memberRepository.findBySocialTypeAndSocialId(
                    socialType, it
                )
            }
        return findMember ?: saveMember(attributes, socialType)
    }

    private fun saveMember(attributes: OAuthAttributes, socialType: SocialType): Member {
        val createdMember = attributes.toEntity(socialType, attributes.oAuth2UserInfo)
        return memberRepository.save(createdMember)
    }

    private fun getSocialType(registrationId: String): SocialType {
        return when (registrationId) {
            NAVER -> SocialType.NAVER
            GOOGLE -> SocialType.GOOGLE
            KAKAO -> SocialType.KAKAO
            else -> throw IllegalArgumentException("잘못된 registrationId")
        }
    }
}