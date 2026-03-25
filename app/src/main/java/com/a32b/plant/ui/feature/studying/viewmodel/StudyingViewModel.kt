package com.a32b.plant.ui.feature.studying.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a32b.plant.data.di.AppContainer
import com.a32b.plant.data.model.StudyingUser
import com.a32b.plant.data.repository.StudyingRepository
import com.a32b.plant.ui.theme.primary
import com.a32b.plant.ui.theme.sub2
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StudyingUiState(
    val tag: String,
    val timer: Long = 0L,
    val isStudying: Boolean = true,
    val buttonText: String = "일시정지",
    val buttonBack: Color = sub2,
    val studyingUsers: List<StudyingUser> = emptyList(),
    val isDialogShown: Boolean = false,
    val studyLog: List<String> = emptyList()
)

sealed class StudyingEvent{
    object NavigateToStudyResult: StudyingEvent()
}
class StudyingViewModel(private val repository: StudyingRepository, private val tag: String) : ViewModel() {

    private val _uiState = MutableStateFlow(StudyingUiState(tag = tag))
    val uiState = _uiState.asStateFlow()
    //
    private val _eventChannel = Channel<StudyingEvent>(Channel.BUFFERED)
    val event = _eventChannel.receiveAsFlow()

    /** db에서 같은 태그로 공부중인 사용자 데이터 가져오기 */
    fun onStudyingUsersChange(){
        viewModelScope.launch {
            _uiState.update { it.copy(studyingUsers = repository.getStudyingUser(tag)) }
        }
    }

    /** 공부중 상태 변경 */
    fun onStudyingStatusChange() {
         _uiState.update { it.copy(isStudying = !it.isStudying) }

        if(_uiState.value.isStudying) startStopwatch()
        else stopStopwatch()
    }
    fun onTimerChange() = _uiState.update { it.copy(timer = it.timer + 1000 ) }
//data store <- sharedPreference 상위호환 느낌
    /** 스톱워치 */
    private var job: Job? = null
    fun startStopwatch(){
        job?.cancel()
        job = viewModelScope.launch {
            while (true){
                delay(1000)
                onTimerChange()
                if(_uiState.value.timer % 600000L == 0L){
                    repository.updateStudyingUser(
                        StudyingUser("zz", "zz", "lv.2", tag, _uiState.value.timer)
                    )
                    onStudyingUsersChange()
                }
            }
        }
    }

    fun stopStopwatch(){
        _uiState.update { it.copy(isStudying = false) }
        job?.cancel()
    }

    init {
        startStopwatch()
    }




    fun showStudyLogEdit(){

    }
}