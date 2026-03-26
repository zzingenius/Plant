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


    private val _navigateToCommunity = MutableSharedFlow<Unit>()
    val navigateToCommunity = _navigateToCommunity.asSharedFlow()


    val uiState: StateFlow<List<Post>> = combine(
        repository.getPosts(),
        _searchQuery
    ) { posts, query ->


        val filteredPosts = if (query.isBlank()) {
            posts
        } else {
            posts.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.content.contains(query, ignoreCase = true)
            }
        }


        val sortedPosts = filteredPosts.sortedByDescending { it.createdAt }


        if (sortedPosts.isNotEmpty()) {
            _navigateToCommunity.emit(Unit)
        }

        sortedPosts

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}