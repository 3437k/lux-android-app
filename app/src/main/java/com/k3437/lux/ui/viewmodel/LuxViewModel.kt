package com.k3437.lux.ui.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

/**
 * LuxViewModel은 럭스(조도) 값을 관리하는 ViewModel입니다.
 * 이 ViewModel은 UI 상태를 저장하고, UI 컨트롤러(예: Composable 함수)가
 * 이 상태를 관찰하고 업데이트할 수 있도록 합니다.
 */
class LuxViewModel : ViewModel() {
    // _lx는 내부적으로 관리되는 변경 가능한 상태(MutableState)입니다.
    // 초기값은 0으로 설정됩니다.
    // mutableIntStateOf는 Int 타입에 특화된 MutableState로, 불필요한 오토박싱을 피해 성능상 이점이 있습니다.
    private val _lx = mutableIntStateOf(0)

    // lx는 외부로 노출되는 읽기 전용 상태(State)입니다.
    // UI는 이 상태를 관찰하여 _lx 값이 변경될 때마다 자동으로 업데이트됩니다.
    val lx = _lx

    /**
     * 럭스(lx) 값을 업데이트하는 함수입니다.
     * @param lx 새로운 럭스 값입니다.
     */
    fun setLx(lx: Int) {
        // _lx의 intValue를 새로운 값으로 설정합니다.
        // 이 변경은 lx를 관찰하는 모든 Composable의 재구성을 트리거합니다.
        _lx.intValue = lx
    }
}