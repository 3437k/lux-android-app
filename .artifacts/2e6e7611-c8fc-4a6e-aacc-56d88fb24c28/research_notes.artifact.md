# Android 16 (API 36) Targeting Investigation

This document summarizes the potential impacts of setting `targetSdk = 36` (Android 16 "Baklava") for the Lux app.

## Project Current State
- **compileSdk**: 36
- **targetSdk**: 36
- **AGP**: 9.3.1
- **Kotlin**: 2.2.10

The app is already configured to target API 36. Below is an analysis of compatibility based on Android 16 behavior changes.

## Compatibility Analysis

### 1. Edge-to-Edge Enforcement
- **Change**: Android 16 removes the ability to opt-out of edge-to-edge enforcement.
- **App Status**: [MainActivity.kt](file:///E:/fork/_androids/lux-android-app/app/src/main/java/com/k3437/lux/MainActivity.kt) already calls `enableEdgeToEdge()`.
- **Impact**: **None**. The app is already compliant.

### 2. Predictive Back Gesture
- **Change**: Enabled by default for apps targeting API 36. `onBackPressed` is ignored.
- **App Status**: The app does not use custom back handling or `BackHandler` in Compose.
- **Impact**: **None**. The app will use default system behavior.

### 3. Adaptive Layouts & Aspect Ratio
- **Change**: On displays with `sw600dp` or higher, orientation, resizability, and aspect ratio restrictions (like `android:max_aspect`) are ignored.
- **App Status**: [AndroidManifest.xml](file:///E:/fork/_androids/lux-android-app/app/src/main/AndroidManifest.xml) defines `<meta-data android:name="android.max_aspect" android:value="2.4" />`.
- **Impact**: **Minor**. This metadata will be ignored on large screens/tablets, and the app will fill the screen. Since the app is built with Compose and uses `fillMaxSize()`, this should result in a better experience on large screens rather than a breakage.

### 4. Permissions (Health & Local Network)
- **Change**: Android 16 introduces granular health permissions and a new local network permission.
- **App Status**: The app only uses the Light Sensor (`Sensor.TYPE_LIGHT`), which is not part of the health permission changes. It does not use any networking APIs.
- **Impact**: **None**.

### 5. Intent Security (Safer Intents)
- **Change**: Stricter intent matching for external intents.
- **App Status**: The app only has one activity and doesn't seem to receive complex external intents.
- **Impact**: **None**.

### 6. JobScheduler Quota
- **Change**: New quota optimizations for background jobs.
- **App Status**: The app does not use `JobScheduler` or `WorkManager`.
- **Impact**: **None**.

## Conclusion
Setting `targetSdk = 36` for the Lux app is **safe**. The app is simple and already follows the key requirements (like edge-to-edge).

> [!TIP]
> While not strictly necessary, you could remove the `android:max_aspect` meta-data from the manifest as it's becoming obsolete for large screen devices in Android 16.

## Verification Suggestions
- Test the app on an Android 16 emulator/device, especially on a tablet or foldable emulator to see the adaptive layout behavior.
- Verify that the status bar and navigation bar insets are handled correctly (the app already uses `enableEdgeToEdge()`).
