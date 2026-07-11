package com.k3437.lux.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.k3437.lux.ui.theme.ThemePreferences

/**
 * LuxViewModel은 럭스(조도) 값을 관리하는 ViewModel입니다.
 */
class LuxViewModel(application: Application) : AndroidViewModel(application) {
    // _lx는 내부적으로 관리되는 변경 가능한 상태(MutableState)입니다.
    private val _lx = mutableIntStateOf(0)

    // lx는 외부로 노출되는 읽기 전용 상태(State)입니다.
    val lx = _lx

    // 측정 고정(Hold) 상태를 관리하는 변수입니다.
    private val _isHeld = mutableStateOf(false)
    val isHeld = _isHeld

    // 사용자 테마 설정을 관리하는 상태입니다.
    // ViewModel 생성 시 SharedPreferences에서 값을 미리 로드합니다.
    private val _userForcedDarkMode = mutableStateOf(ThemePreferences.getUserForcedDarkMode(application))
    val userForcedDarkMode = _userForcedDarkMode

    /**
     * 럭스(lx) 값을 업데이트하는 함수입니다.
     * @param lx 새로운 럭스 값입니다.
     */
    fun setLx(lx: Int) {
        // Hold 상태가 아닐 때만 값을 업데이트합니다.
        if (!_isHeld.value) {
            _lx.intValue = lx
        }
    }

    /**
     * Hold 상태를 설정하는 함수입니다.
     * @param hold true면 측정을 멈추고 현재 값을 유지합니다.
     */
    fun setHold(hold: Boolean) {
        _isHeld.value = hold
    }

    /**
     * 다크 모드 강제 설정 여부를 업데이트하고 저장합니다.
     */
    fun setUserForcedDarkMode(isForced: Boolean?) {
        _userForcedDarkMode.value = isForced
        ThemePreferences.setUserForcedDarkMode(getApplication(), isForced)
    }
}