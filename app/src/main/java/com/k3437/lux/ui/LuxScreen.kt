package com.k3437.lux.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.k3437.lux.R
import com.k3437.lux.ui.theme.LuxTheme
import com.k3437.lux.ui.theme.ThemePreferences
import com.k3437.lux.ui.viewmodel.LuxViewModel


// 애니메이션 지속 시간 (밀리초 단위)
private const val THEME_ANIMATION_DURATION = 500 // 0.5초

@Composable
fun LuxScreen(viewModel: LuxViewModel) {
    val context = LocalContext.current

    // SharedPreferences에서 사용자 설정 로드, remember를 사용하여 초기 한 번만 로드
    var userForcedDarkMode by remember {
        mutableStateOf(ThemePreferences.getUserForcedDarkMode(context))
    }

    val systemIsDark = isSystemInDarkTheme()

    // 최종 다크 모드 결정: 저장된 사용자 설정이 있으면 그것을 따르고, 없으면 시스템 설정을 따름
    val currentDarkTheme = userForcedDarkMode ?: systemIsDark

    LuxTheme(darkTheme = currentDarkTheme) {
        // 현재 테마의 배경색을 가져옵니다.
        val targetBackgroundColor = MaterialTheme.colorScheme.background

        // 배경색 변경에 애니메이션을 적용합니다.
        val animatedBackgroundColor by animateColorAsState(
            targetValue = targetBackgroundColor,
            animationSpec = tween(durationMillis = THEME_ANIMATION_DURATION),
            label = "BackgroundColorAnimation"
        )

        // 현재 테마의 텍스트 색상을 가져옵니다.
        val targetTextColor = MaterialTheme.colorScheme.onBackground

        // 텍스트 색상 변경에 애니메이션을 적용합니다.
        val animatedTextColor by animateColorAsState(
            targetValue = targetTextColor,
            animationSpec = tween(durationMillis = THEME_ANIMATION_DURATION),
            label = "TextColorAnimation"
        )

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // 현재 테마 상태의 반대로 사용자 설정을 결정
                        val newForcedState = !currentDarkTheme

                        // UI 상태 즉시 업데이트
                        userForcedDarkMode = newForcedState

                        // SharedPreferences에 저장
                        ThemePreferences.setUserForcedDarkMode(context, newForcedState)
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp, 60.dp)
                        .size(60.dp),
                    // FAB 색상도 테마에 맞게 조정하거나, 애니메이션을 적용할 수 있습니다.
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ) {
                    Icon(
                        imageVector = if (currentDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = if (currentDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.EndOverlay
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = animatedBackgroundColor // 애니메이션된 배경색 사용
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
                        color = animatedTextColor, // 애니메이션된 텍스트 색상 사용
                        style = MaterialTheme.typography.displayLarge,
                    )
                }
            }
        }
    }
}