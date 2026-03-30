package com.a32b.plant.data.model

import com.a32b.plant.data.di.CurrentUser
import com.google.firebase.Timestamp

data class CommunityActivity(
    val uid: String = CurrentUser.uid,
    val type: String = "",
    val title: String = "",
    val targetId: String = "",
    val comment: String? = null,
    val createAt: Timestamp = Timestamp.now()
)
