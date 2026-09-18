# Implementation Plan - App Refactoring and pAI Branding

This plan addresses the requested UI/UX improvements, fixes bugs in the settings screen, removes the unnecessary login flow, and rebrands the application as "pAI" with the new logo.

## User Review Required

> [!NOTE]
> The login screen will be completely bypassed. The app will navigate from the Splash screen directly to the Chat screen if the server is configured, or to the Server Configuration screen otherwise.
>
> Renaming the actual project directory on disk (from `omniroute android` to `pAI`) must be done manually by the user while the IDE is closed to avoid breaking the active workspace. However, the internal project name and app display name will be updated to `pAI`.

## Proposed Changes

### [Branding and Logo]

#### [MODIFY] [strings.xml](file:///D:/kiro/omniroute%20android/app/src/main/res/values/strings.xml)
- Rename `app_name` to "pAI".
- Rename `app_full_name` to "pAI (Personal AI)".

#### [MODIFY] [settings.gradle.kts](file:///D:/kiro/omniroute%20android/settings.gradle.kts)
- Update `rootProject.name` to "pAI".

#### [MODIFY] [AndroidManifest.xml](file:///D:/kiro/omniroute%20android/app/src/main/AndroidManifest.xml)
- Update `android:icon` and `android:roundIcon` to use `@drawable/logo_pai`.

### [Navigation and Server Configuration]

#### [NEW] [SplashViewModel.kt](file:///D:/kiro/omniroute%20android/app/src/main/java/com/pai/personalai/presentation/screens/splash/SplashViewModel.kt)
- Create a new ViewModel to determine the next screen based on whether the server is already configured.

#### [MODIFY] [PAiNavGraph.kt](file:///D:/kiro/omniroute%20android/app/src/main/java/com/pai/personalai/presentation/navigation/PAiNavGraph.kt)
- Bypass the `Login` screen entirely in the splash flow.
- Use `SplashViewModel` to decide the start destination after splash (Chat or ServerConfig).
- Pass an `onEditServerConfig` lambda to `SettingsScreen` to allow navigation to the configuration editor.

#### [MODIFY] [ServerConfigViewModel.kt](file:///D:/kiro/omniroute%20android/app/src/main/java/com/pai/personalai/presentation/screens/config/ServerConfigViewModel.kt)
- Inject `ConfigRepository` and load existing server configuration in the `init` block to pre-fill the input fields.

### [UI Fixes]

#### [MODIFY] [ChatScreen.kt](file:///D:/kiro/omniroute%20android/app/src/main/java/com/pai/personalai/presentation/screens/chat/ChatScreen.kt)
- **Fix Overlap:** Remove the `FloatingActionButton` (which was blocking the Send button).
- **Relocate New Chat:** Move the "New Chat" icon button to the `TopAppBar` actions.

#### [MODIFY] [SettingsScreen.kt](file:///D:/kiro/omniroute%20android/app/src/main/java/com/pai/personalai/presentation/screens/settings/SettingsScreen.kt)
- **Fix Dialogs:** Change `?.let { ... }` on Boolean flags to `if (flag) { ... }` so dialogs can be dismissed and don't show up automatically.
- **Edit Config:** Add a "Server Configuration" clickable item to allow users to edit their server details.

#### [MODIFY] [SplashScreen.kt](file:///D:/kiro/omniroute%20android/app/src/main/java/com/pai/personalai/presentation/screens/splash/SplashScreen.kt)
- Replace the placeholder icon with the new `logo_pai` image.

## Verification Plan

### Automated Tests
- Run `:app:assembleDebug` to verify the project builds and all references are correct.
- Run existing unit tests: `./gradlew test`

### Manual Verification
1. Launch the app: Verify the Splash screen shows the new logo.
2. Verify Navigation: After splash, it should go directly to Chat (if configured) or Server Config (if fresh install).
3. Test Server Config: Verify text fields are pre-filled when editing.
4. Test Chat UI: Verify the Send button is no longer blocked and the "New Chat" button is in the top bar.
5. Test Settings: Verify the Logout dialog dismisses correctly and the "Server Configuration" option works.
6. Verify Branding: Check that the app name appears as "pAI" in the launcher and UI.
