package com.practice.kotlinpractice.oauth2.userInfo

class KakaoOAuth2UserInfo(
    attributes: Map<String, Any>
) : OAuth2UserInfo(attributes) {
    override fun getId(): String? {
        return attributes["id"] as String?
    }

    override fun getNickname(): String? {
        val account = attributes["kakao_account"] as Map<*, *>?
        val profile = attributes["profile"] as Map<*, *>?
        if (account == null || profile == null) {
            return null
        }
        return profile["nickname"] as String
    }

    override fun getImageUrl(): String? {
        val account = attributes["kakao_account"] as Map<*, *>?
        val profile = attributes["profile"] as Map<*, *>?
        if (account == null || profile == null) {
            return null
        }
        return profile["thumbnail_image_url"] as String
    }
}