package com.a32b.plant.ui.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.a32b.plant.data.di.AppContainer
import com.a32b.plant.data.di.CurrentUser
import com.a32b.plant.data.di.CurrentUser.nickname
import com.a32b.plant.data.model.UserProfile
import com.a32b.plant.data.repository.PotRepository
import com.a32b.plant.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


//CurrentUser.uid = user.uid
//CurrentUser.nickname = user.nickname
//CurrentUser.profileImg = user.profileImg
// 완성되면 CurrentUser 갖다가 사용할 것

class MyPageViewModel(
    private val userRepository: UserRepository,
    private val potRepository: PotRepository
) : ViewModel() {
//    private val userRepository = AppContainer.userRepository

    /** 데이터베이스에서 값을 받아와야 하는 경우
    _변수명 : 외부에서 값을 못 건들이게 하기 위해 private으로 선언
    변수명 : 외부에서 읽는 데이터.
    _변수명이 바뀌면 자동으로 값이 업데이트가 되게 하기 위해 .asStaeFlow() 붙이기
     */
    private val _potId = MutableStateFlow<String>("")
    val potId = _potId.asStateFlow()

    private val _userData = MutableStateFlow<UserProfile?>(null)
    val userData = _userData.asStateFlow()
    private val _userName = MutableStateFlow<String>("사용자")
    val userName = _userName.asStateFlow()
   private val _profileImg = MutableStateFlow<String>("")
    val profileImg = _profileImg.asStateFlow()

    // update 결과 확인용 update 완료 후 true => 다이얼로그 창 닫기
    private val _isUpdateSuccess = MutableStateFlow(false)
    val isUpdateSuccess = _isUpdateSuccess.asStateFlow()

    // 프로필 편집 다이얼로그 창에 쓰이는 레벨 리스트
    private val _levelList = MutableStateFlow<List<String>>(emptyList())
    val levelList = _levelList.asStateFlow()

    init {
        viewModelScope.launch {
            _potId.value = "WfFW9NVdg8NDXZPGNas4"

        }
    }

    // 보유한 레벨 중복 제거 레벨 리스트 가져오기
    fun getImageLevelList() {
        viewModelScope.launch {
            val result = potRepository.getDuplicationLevelList(_potId.value)
            _levelList.value = result
        }
    }

    fun updateProfile(nickname: String, imageLevel: String) {
        if (nickname.length <= 2) {
            _isUpdateSuccess.value = false
            return
        } else {
            viewModelScope.launch {
                try {
                    userRepository.updateNicknameAndImage(
                        "WfFW9NVdg8NDXZPGNas4",
                        nickname,
                        imageLevel
                    )
                    CurrentUser.set(CurrentUser.uid, nickname, imageLevel)
                    _isUpdateSuccess.value = true
                    _userName.value = nickname
                } catch (e: Exception) {
                    Log.e("error", e.message.toString())
                    _isUpdateSuccess.value = false
                }
            }
        }
    }


    fun resetIsUpdateSuccess() {
        _isUpdateSuccess.value = false
    }


    //데이터베이스에서 값을 안 가져와도 되는 경우
    fun getTag() = "자격증"
}

//Log.d("mypage", "MyPageViewModel - ")
