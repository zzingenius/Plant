package com.a32b.plant.data.model


import com.google.firebase.firestore.PropertyName

/**
 * 커뮤니티 게시글 데이터 모델
 * 파이어베이스(Firestore)와 연동하기 위해 모든 변수에 초기값(=)을 줍니다.
 */
data class Post(
    val id: String = "",              // 게시글 고유 ID
    val nickName: String = "",        // 작성자 닉네임 (성호님 팀 약속!)
    val content: String = "",         // 게시글 내용
    val commentCount: Int = 0,        // 댓글 수
    val likeCount: Int = 0,           // 좋아요 수
    val isLiked: Boolean = false,     // 내가 좋아요를 눌렀는지 여부
    val createdAt: String = ""        // 작성 시간 (예: 2026-03-26)
) {
    // 파이어베이스가 데이터를 읽을 때 필요한 빈 생성자 역할을
    // 위에서 준 초기값(= "", = 0 등)들이 대신 해줍니다.
}