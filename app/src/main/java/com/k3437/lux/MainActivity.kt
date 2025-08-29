package com.k3437.lux

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.k3437.lux.ui.LuxScreen
import com.k3437.lux.ui.viewmodel.LuxViewModel


class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager // 센서 관리자
    private var lightSensor: Sensor? = null // 조도 센서

    private val viewModel: LuxViewModel = LuxViewModel() // 조도 뷰모델

    override fun onCreate(savedInstanceState: Bundle?) {
        //  SDK 35를 타겟팅하는 앱은 Android 15 이상에서 앱이 올바르게 표시되도록 인셋을 처리
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        // 화면 꺼짐 방지
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 센서 관리자 초기화
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // 조도 센서가 있는지 확인, 있다면 lightSensor 변수에 할당
        // 센서가 없는 기기라면 0으로 항상 표시됨
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        }

        setContent {
            // LuxScreen 컴포저블을 화면에 표시
            LuxScreen(viewModel)
        }
    }

    override fun onResume() {
        super.onResume()

        // 조도 센서 리스너 등록
        lightSensor.also { light ->
            sensorManager.registerListener(
                this, // SensorEventListener
                light, // Sensor 객체
                SensorManager.SENSOR_DELAY_NORMAL,  // 센서 데이터 수신 속도 (일반)
                SensorManager.SENSOR_DELAY_UI // 센서 데이터 수신 속도 (UI 업데이트용)
            )
        }
    }

    override fun onPause() {
        super.onPause()

        // 포그라운드에서 센서 데이터만 수집하도록 리스너 해제
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            // 조도 센서 값이 변경되었을 떄
            Sensor.TYPE_LIGHT -> {
                val light = event.values[0].toInt() // 조도 값을 정수형으로 변환
                viewModel.setLx(light) // 뷰모델에 조도 값 설정
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // 센서 정확도 변경 시 호출 (여기서는 특별한 처리 없음)
    }
}


