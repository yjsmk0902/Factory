package com.practice.kotlinpractice.oauth2.userInfo

abstract class OAuth2UserInfo(
    val attributes: Map<String, Any>
) {

    abstract fun getId(): String?
    abstract fun getNickname(): String?
    abstract fun getImageUrl(): String?

}