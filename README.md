# 조도계 - 오늘의 햇빛

- Android 내장 조도 센서를 활용하여 실시간 lux 값을 표시하는 비교용 유틸리티 앱.
- Compose + MVVM 기반으로 구현.

## 기능

- **실시간 조도 값(lx) 표시**: 기기의 조도 센서를 이용한 정밀한 실시간 측정.
- **측정 고정(Hold) 기능**: 화면을 누르고 있으면 측정이 일시 정지되어 값을 편하게 확인 가능.
- **다크 모드 지원**: 사용자 설정 및 시스템 설정에 따른 테마 전환 기능.
- **다국어 지원**: 한국어 및 영어 지원.

## 개발정보

- 개인 프로젝트, 제작 기간: 2023.12.1 ~ 2023.12.18 (기본 구현)
- 기능 업데이트 및 최적화: 2025.03
- 배포: Google Play

## 적용 기술

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM
- **Jetpack**: ViewModel, Lifecycle, Compose Animation, Baseline Profiles

## 개발 포인트

- **성능 최적화 (Startup Time)**:
    - Baseline Profiles 적용으로 초기 실행 속도 향상.
    - 불필요한 라이브러리(Material Icons Extended)를 제거하고 필수 리소스만 사용하여 앱 용량 및 로딩 속도 최적화.
    - SharedPreferences 읽기 시점을 ViewModel 초기화 단계로 이동하여 UI 스레드 병목 제거.
- **사용자 경험(UX) 개선**:
    - **Hold 상태 시각화**: 측정이 멈췄을 때 찐한 노란색(Amber)으로 수치를 표시하여 직관적인 피드백 제공.
    - **접근성**: 거친 환경에서도 조작이 쉽도록 테마 전환 버튼과 아이콘 크기 확대.
- **코드 품질**:
    - `by viewModels()` 위임과 `viewModel()` Composable 주입을 통한 표준적인 의존성 관리.
    - 가독성 높은 Compose 애니메이션 구현.

## 기술적 제약 및 해결

- **제약사항:**
  - 하드웨어 편차: OEM별 센서 스펙 차이로 측정값 차이 발생.
  - 환경 변수: 측정 각도, 반사광, 투과광에 따라 값 변동.
  - 적외선(IR) 간섭: 실제 가시광선 외 IR 성분이 lux 값에 영향을 줄 수 있으며, 표준 Android SDK API에서는 IR 필터링 수준 확인 불가.

- **해결 방안:**
  - 절대값 제공보다는 동일 조건에서의 상대적 비교 중심으로 제품 컨셉 정의.
  - 사용자에게는 간단한 비교용 측정 도구로 안내.

## 화면 구성

![lux](https://github.com/3437k/lux-android-app/blob/main/resource/lux_02.png?raw=true)
