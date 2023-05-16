package com.practice.kotlinpractice.oauth2.userInfo

class NaverOAuth2UserInfo(
    attributes: Map<String, Any>
) : OAuth2UserInfo(attributes) {

    override fun getId(): String? {
        val response = attributes["response"] as Map<*, *>?
        return response
            ?.let{
                response["id"] as String
            }
    }

    override fun getNickname(): String? {
        val response = attributes["response"] as Map<*, *>?
        return response
            ?.let{
                response["nickname"] as String
            }
    }

    override fun getImageUrl(): String? {
        val response = attributes["response"] as Map<*, *>?
        return response
            ?.let{
                response["profile_image"] as String
            }
    }
}