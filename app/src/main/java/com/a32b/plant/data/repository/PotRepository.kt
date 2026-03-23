package com.a32b.plant.data.repository

import com.google.firebase.firestore.FirebaseFirestore

data class PotInfo(
    val id: String = "",
    val tag: String = "",
    val name: String = "",
    val level: String = ""
)
class PotRepository {
    private val db = FirebaseFirestore.getInstance()
}