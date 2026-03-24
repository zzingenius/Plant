package com.a32b.plant.data.model

//화분 정보 모음
data class PotInfo(
    val id: String = "",
    val tag: String = "",
    val name: String = "",
    val level: String = "",
    // [추가] 이미지 URL과 오늘 공부 시간 필드가 반드시 있어야 합니다.
    val imageUrl: String = "",
    val todayStudyingTime: Long = 0L
)