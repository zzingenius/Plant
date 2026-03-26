package com.a32b.plant.ui.feature.community.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a32b.plant.data.model.Post
import com.a32b.plant.data.repository.PostRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommunityPostViewModel(private val repository: PostRepository) : ViewModel() {

    // ✅ 게시글 저장 함수
    fun savePost(title: String, content: String, tag: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val newPost = Post(
                    id = "",
                    nickName = "성호", // 👈 팀 약속대로 nickName 추가!
                    content = "[$tag] $title\n$content",
                    commentCount = 0,
                    likeCount = 0,
                    isLiked = false,
                    createdAt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // ✅ 오늘 날짜 자동 입력
                )

                repository.uploadPost(newPost)
                onComplete(true)
            } catch (e: Exception) {
                onComplete(false)
            }
        }
    }
}