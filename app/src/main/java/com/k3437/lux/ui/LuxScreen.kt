package com.k3437.lux.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode // 다크 모드 아이콘
import androidx.compose.material.icons.filled.LightMode // 라이트 모드 아이콘
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.k3437.lux.R
import com.k3437.lux.ui.theme.LuxTheme
import com.k3437.lux.ui.viewmodel.LuxViewModel


// 애니메이션 지속 시간 (밀리초 단위)
private const val THEME_ANIMATION_DURATION = 500 // 0.5초

@Composable
fun LuxScreen(viewModel: LuxViewModel = viewModel()) {
    // 최종 다크 모드 결정: 저장된 사용자 설정이 있으면 그것을 따르고, 없으면 시스템 설정을 따름
    val currentDarkTheme = viewModel.userForcedDarkMode.value ?: isSystemInDarkTheme()

    LuxTheme(darkTheme = currentDarkTheme) {
        val targetBackgroundColor = MaterialTheme.colorScheme.background
        val animatedBackgroundColor by animateColorAsState(
            targetValue = targetBackgroundColor,
            animationSpec = tween(durationMillis = THEME_ANIMATION_DURATION),
            label = "BackgroundColorAnimation"
        )

        val targetTextColor = if (viewModel.isHeld.value) {
            androidx.compose.ui.graphics.Color(0xFFFFD700) // 찐한 노란색 (Gold/Amber)
        } else {
            MaterialTheme.colorScheme.onBackground
        }
        val animatedTextColor by animateColorAsState(
            targetValue = targetTextColor,
            animationSpec = tween(durationMillis = THEME_ANIMATION_DURATION),
            label = "TextColorAnimation"
        )

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // 현재 테마 상태의 반대로 사용자 설정을 결정하여 ViewModel에 저장
                        viewModel.setUserForcedDarkMode(!currentDarkTheme)
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp, 60.dp)
                        .size(80.dp), // 버튼 크기를 60dp에서 80dp로 확대
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ) {
                    Icon(
                        imageVector = if (currentDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                        contentDescription = if (currentDarkTheme) stringResource(R.string.switch_light_mode) else stringResource(R.string.switch_dark_mode),
                        modifier = Modifier.size(40.dp) // 아이콘 크기도 함께 확대 (기본 약 24dp -> 40dp)
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.EndOverlay
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                try {
                                    viewModel.setHold(true)
                                    awaitRelease()
                                } finally {
                                    viewModel.setHold(false)
                                }
                            }
                        )
                    },
                color = animatedBackgroundColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.lx, viewModel.lx.intValue),
                        color = animatedTextColor,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = if (viewModel.isHeld.value) FontWeight.Bold else FontWeight.Normal
                    )

                    if (viewModel.isHeld.value) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.hold_label),
                            color = animatedTextColor,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
