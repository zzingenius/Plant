package com.a32b.plant.data.model


import com.google.firebase.firestore.PropertyName

/**
 * 커뮤니티 게시글 데이터 모델
 * 파이어베이스(Firestore)와 연동하기 위해 모든 변수에 초기값(=)을 줍니다.
 */

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Post(
    val id: String = "",
    val title: String = "",
    val nickName: String = "",
    val content: String = "",
    val commentCount: Int = 0,
    val likeCount: Int = 0,
    val isLiked: Boolean = false,
    val createdAt: String = ""
)