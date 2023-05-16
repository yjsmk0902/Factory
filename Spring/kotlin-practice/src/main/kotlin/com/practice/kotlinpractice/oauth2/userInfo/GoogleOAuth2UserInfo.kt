package com.practice.kotlinpractice.oauth2.userInfo

class GoogleOAuth2UserInfo(
    attributes: Map<String, Any>
) : OAuth2UserInfo(attributes) {
    override fun getId(): String? {
        return attributes["sub"] as String?
    }

    override fun getNickname(): String? {
        return attributes["name"] as String?
    }

    override fun getImageUrl(): String? {
        return attributes["picture"] as String?
    }

}