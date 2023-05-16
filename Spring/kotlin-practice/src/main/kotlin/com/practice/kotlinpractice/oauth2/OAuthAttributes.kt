package com.practice.kotlinpractice.oauth2

import com.practice.kotlinpractice.domain.Member
import com.practice.kotlinpractice.domain.Role
import com.practice.kotlinpractice.domain.SocialType
import com.practice.kotlinpractice.oauth2.userInfo.GoogleOAuth2UserInfo
import com.practice.kotlinpractice.oauth2.userInfo.KakaoOAuth2UserInfo
import com.practice.kotlinpractice.oauth2.userInfo.NaverOAuth2UserInfo
import com.practice.kotlinpractice.oauth2.userInfo.OAuth2UserInfo
import java.util.*

class OAuthAttributes(
    val nameAttributeKey: String,
    val oAuth2UserInfo: OAuth2UserInfo
) {
    companion object{
        fun of(
            socialType: SocialType,
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return when (socialType) {
                SocialType.KAKAO -> ofKakao(userNameAttributeName, attributes)
                SocialType.GOOGLE -> ofGoogle(userNameAttributeName, attributes)
                SocialType.NAVER -> ofNaver(userNameAttributeName, attributes)
            }
        }

        private fun ofGoogle(
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return OAuthAttributes(
                userNameAttributeName,
                GoogleOAuth2UserInfo(attributes)
            )
        }

        private fun ofNaver(
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return OAuthAttributes(
                userNameAttributeName,
                NaverOAuth2UserInfo(attributes)
            )
        }
        private fun ofKakao(
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return OAuthAttributes(
                userNameAttributeName,
                KakaoOAuth2UserInfo(attributes)
            )
        }
    }

    fun toEntity(
        socialType: SocialType,
        oAuth2UserInfo: OAuth2UserInfo
    ): Member {
        return Member(
            socialType = socialType,
            socialId = oAuth2UserInfo.getId(),
            email = "${UUID.randomUUID()} @socialUser.com",
            nickname = oAuth2UserInfo.getNickname(),
            imageUrl = oAuth2UserInfo.getImageUrl(),
            role = Role.ROLE_GUEST
        )
    }

}