# 조도계 - 오늘의 햇빛

- Android 내장 조도 센서를 활용하여 실시간 lux 값을 표시하는 비교용 유틸리티 앱.
- Compose + MVVM 기반으로 구현.

## 기능

- 실시간 조도 값(lx) 표시
- 다크 모드 지원

## 개발정보

- 개인 프로젝트, 제작 기간: 2023.12.1 ~ 2023.12.18 (약 3주)
- 배포: Google Play

## 적용 기술

- Language: Kotlin
- UI: Jetpack Compose
- Architecture: MVVM

## 개발 포인트

- SensorManager + SensorEventListener를 활용하여 실시간 센서 값 반영
- ViewModel과 Compose State를 활용한 UI 상태 관리
- 불필요한 기능 제외 → 빠른 실행 속도 및 직관적 사용성 확보
- Compose 애니메이션과 Scaffold/FAB 사용으로 직관적이고 반응 빠른 UI 구현

## 기술적 제약 및 해결

- **제약사항:**

  - 하드웨어 편차: OEM별 센서 스펙 차이로 측정값 차이 발생
  - 환경 변수: 측정 각도, 반사광, 투과광에 따라 값 변동
  - 적외선(IR) 간섭: 실제 가시광선 외 IR 성분이 lux 값에 영향을 줄 수 있으며, 표준 Android SDK API에서는 IR 필터링 수준 확인 불가

- **해결 방안:**
  - 절대값 제공보다는 동일 조건에서의 상대적 비교 중심으로 제품 컨셉 정의.
  - 사용자에게는 간단한 비교용 측정 도구로 안내.

## 화면 구성

![image](resource/s2.png)
