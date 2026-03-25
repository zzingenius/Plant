package com.a32b.plant.data.model

//화분 정보 모음
data class PotInfo(
    val id: String = "",
    val tag: String = "",
    val name: String = "",
    private val _level: String = "0",
    val imageUrl: String = "",
    val todayStudyingTime: Long = 0L,
    val pottotalStudyingTime: Long = 0L
){
    //레벨 계산
    val level: String get(){
        val hours = pottotalStudyingTime / 3600
        val calculatedlevel =  when {
            hours >= 77 -> 5
            hours >= 50 -> 4
            hours >= 30 -> 3
            hours >= 10 -> 2
            hours >= 3 -> 1
            else -> 0
        }
        return calculatedlevel.toString()
    }

}