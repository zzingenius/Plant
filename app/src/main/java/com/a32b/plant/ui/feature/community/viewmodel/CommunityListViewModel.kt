package com.a32b.plant.ui.feature.community.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a32b.plant.data.model.Post
import com.a32b.plant.data.repository.PostRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CommunityListViewModel(private val repository: PostRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // 1️⃣ 화면 이동을 위한 '이벤트' 채널 (일회성 신호)
    private val _navigateToCommunity = MutableSharedFlow<Unit>()
    val navigateToCommunity = _navigateToCommunity.asSharedFlow()

    val uiState: StateFlow<List<Post>> = combine(
        repository.getPosts(),
        _searchQuery
    ) { posts, query ->
        // 2️⃣ 로직 추가: 게시글이 하나라도 있으면 화면 이동 신호를 보냄
        if (posts.isNotEmpty()) {
            _navigateToCommunity.emit(Unit)
        }

        if (query.isBlank()) posts
        else posts.filter { it.content.contains(query, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}