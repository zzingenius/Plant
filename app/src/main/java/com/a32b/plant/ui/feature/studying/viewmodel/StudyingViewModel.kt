package com.a32b.plant.ui.feature.studying.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a32b.plant.data.di.AppContainer
import com.a32b.plant.data.model.StudyingUser
import com.a32b.plant.ui.theme.primary
import com.a32b.plant.ui.theme.sub1
import com.a32b.plant.ui.theme.sub2
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StudyingViewModel: ViewModel() {
    private val repository = AppContainer.studyingRepository
    private var isStudying by mutableStateOf(true)

    /** db에서 같은 태그로 공부중인 사용자 데이터 가져오기 */
    var studyingUsers by mutableStateOf<List<StudyingUser>>(emptyList())
        private set
    fun fetchStudyingUsers(tag: String){
        viewModelScope.launch {
            studyingUsers = repository.getStudyingUser(tag)
            Log.d("뷰모델 유저", "공부중 : $studyingUsers")
        }
    }

    /** 버튼 클릭 시 상태 및 색상 변경 */
    val buttonText: String
        get() = if(isStudying) "일시정지" else "학습하기"
    val buttonBack: Color
        get() = if(isStudying) sub2 else primary
    fun toggleStudyStatus(){
        isStudying = !isStudying

        if(isStudying) startStopwatch()
        else stopStopwatch()
    }
//data store <- sharedPreference 상위호환 느낌
    /** 스톱워치 */
    var timeMillis : Long by mutableStateOf(0L)
    private var job: Job? = null
    fun startStopwatch(){
        job?.cancel()
        job = viewModelScope.launch {
            while (true){
                delay(1000)
                timeMillis +=1000
            }
        }
    }

    fun stopStopwatch(){
        isStudying = false
        job?.cancel()
    }

    init {
        startStopwatch()
    }




    fun showStudyLogEdit(){

    }
}