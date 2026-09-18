# PAi (Personal AI) - Android App Project Definition

## Project Overview

**App Name:** PAi (Personal AI)  
**Platform:** Android (Kotlin)  
**Target SDK:** 34 (Android 14)  
**Minimum SDK:** 24 (Android 7.0)  
**Architecture:** MVVM + Clean Architecture  
**Design:** Futuristic Minimal UI with Material Design 3

## Core Features

### 1. Authentication & Server Configuration
- User login/registration with credential storage
- Server configuration screen:
  - Base URL input
  - API Key input
  - Model selection dropdown (fetched from provider)
  - Test connection button
  - Save/Update configuration
- Logout functionality with confirmation dialog
- Secure credential storage using EncryptedSharedPreferences

### 2. Chat Interface
- Clean, minimal chat screen with futuristic aesthetics
- Message bubbles (user/AI differentiation)
- Typing indicators
- Message timestamps
- Auto-scroll to latest message
- Pull-to-refresh for chat history
- Error handling with retry options
- Message status indicators (sending, sent, failed)

### 3. Attachment System
- Multi-format support:
  - Images (JPG, PNG, WEBP)
  - PDF documents
  - DOC/DOCX files
- File picker integration
- Image preview before sending
- File size validation (max 10MB per file)
- Multiple file selection support
- Thumbnail display in chat
- File upload progress indicator

### 4. Theme System
- Light theme (clean whites, subtle grays)
- Dark theme (deep blacks, electric accents)
- System theme sync option
- Smooth theme transition animations
- Persistent theme preference

### 5. Additional Features
- Chat history persistence (Room Database)
- Network connectivity monitoring
- Offline mode indicators
- Copy message text
- Share messages/conversations
- Clear chat history option
- Settings screen with preferences

## Technical Stack

### Core Technologies
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM + Repository Pattern
- **Dependency Injection:** Hilt
- **Networking:** Retrofit + OkHttp
- **Database:** Room
- **Image Loading:** Coil
- **Security:** EncryptedSharedPreferences
- **File Handling:** DocumentFile API
- **Async:** Kotlin Coroutines + Flow

### Dependencies (build.gradle.kts)
```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
```

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/pai/personalai/
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   ├── MessageDao.kt
│   │   │   │   │   │   └── ChatDao.kt
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── MessageEntity.kt
│   │   │   │   │   │   └── ChatEntity.kt
│   │   │   │   │   └── database/
│   │   │   │   │       └── AppDatabase.kt
│   │   │   │   ├── remote/
│   │   │   │   │   ├── api/
│   │   │   │   │   │   └── AIApiService.kt
│   │   │   │   │   ├── dto/
│   │   │   │   │   │   ├── ChatRequest.kt
│   │   │   │   │   │   ├── ChatResponse.kt
│   │   │   │   │   │   └── ModelResponse.kt
│   │   │   │   │   └── interceptor/
│   │   │   │   │       └── AuthInterceptor.kt
│   │   │   │   └── repository/
│   │   │   │       ├── ChatRepository.kt
│   │   │   │       ├── AuthRepository.kt
│   │   │   │       └── ConfigRepository.kt
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   │   ├── Message.kt
│   │   │   │   │   ├── Chat.kt
│   │   │   │   │   ├── Attachment.kt
│   │   │   │   │   └── ServerConfig.kt
│   │   │   │   └── usecase/
│   │   │   │       ├── SendMessageUseCase.kt
│   │   │   │       ├── GetChatHistoryUseCase.kt
│   │   │   │       └── UploadAttachmentUseCase.kt
│   │   │   ├── presentation/
│   │   │   │   ├── theme/
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   ├── navigation/
│   │   │   │   │   └── NavGraph.kt
│   │   │   │   ├── screens/
│   │   │   │   │   ├── splash/
│   │   │   │   │   │   └── SplashScreen.kt
│   │   │   │   │   ├── login/
│   │   │   │   │   │   ├── LoginScreen.kt
│   │   │   │   │   │   └── LoginViewModel.kt
│   │   │   │   │   ├── chat/
│   │   │   │   │   │   ├── ChatScreen.kt
│   │   │   │   │   │   ├── ChatViewModel.kt
│   │   │   │   │   │   └── components/
│   │   │   │   │   │       ├── MessageBubble.kt
│   │   │   │   │   │       ├── AttachmentPreview.kt
│   │   │   │   │   │       └── ChatInput.kt
│   │   │   │   │   ├── settings/
│   │   │   │   │   │   ├── SettingsScreen.kt
│   │   │   │   │   │   └── SettingsViewModel.kt
│   │   │   │   │   └── config/
│   │   │   │   │       ├── ServerConfigScreen.kt
│   │   │   │   │       └── ServerConfigViewModel.kt
│   │   │   │   └── components/
│   │   │   │       ├── LoadingIndicator.kt
│   │   │   │       └── ErrorDialog.kt
│   │   │   ├── di/
│   │   │   │   ├── AppModule.kt
│   │   │   │   ├── DatabaseModule.kt
│   │   │   │   └── NetworkModule.kt
│   │   │   ├── util/
│   │   │   │   ├── Constants.kt
│   │   │   │   ├── FileUtils.kt
│   │   │   │   └── NetworkUtils.kt
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   ├── mipmap/
│   │   │   └── values/
│   │   │       ├── strings.xml
│   │   │       ├── colors.xml
│   │   │       └── themes.xml
│   │   └── AndroidManifest.xml
│   └── test/
│       └── java/com/pai/personalai/
│           ├── viewmodel/
│           │   └── ChatViewModelTest.kt
│           └── repository/
│               └── ChatRepositoryTest.kt
└── build.gradle.kts
```

## Design Specifications

### Color Scheme

**Light Theme:**
- Primary: #6366F1 (Indigo)
- Secondary: #8B5CF6 (Purple)
- Background: #FFFFFF
- Surface: #F8F9FA
- Text Primary: #1F2937
- Text Secondary: #6B7280
- Error: #EF4444

**Dark Theme:**
- Primary: #818CF8 (Light Indigo)
- Secondary: #A78BFA (Light Purple)
- Background: #0F0F0F
- Surface: #1A1A1A
- Text Primary: #F9FAFB
- Text Secondary: #D1D5DB
- Error: #F87171

### Typography
- Font Family: Inter / Roboto
- Heading 1: 24sp, Bold
- Heading 2: 20sp, SemiBold
- Body: 16sp, Regular
- Caption: 14sp, Regular
- Small: 12sp, Regular

### UI Elements

**Message Bubbles:**
- User messages: Gradient (Primary to Secondary), right-aligned
- AI messages: Surface color, left-aligned
- Border radius: 16dp (top), 4dp (sender side)
- Padding: 12dp horizontal, 8dp vertical
- Max width: 75% screen width

**Input Field:**
- Background: Surface elevation
- Border: 1dp, subtle
- Height: 56dp
- Corner radius: 28dp
- Icons: 24dp

**Buttons:**
- Primary: Gradient background
- Secondary: Outlined
- Corner radius: 12dp
- Height: 48dp

## API Integration Requirements

### Expected API Endpoints

1. **List Models**
   - Endpoint: `GET /v1/models`
   - Response: Array of model objects with `id` and `name`

2. **Send Message**
   - Endpoint: `POST /v1/chat/completions`
   - Request Body:
   ```json
   {
     "model": "model-id",
     "messages": [
       {"role": "user", "content": "message text"}
     ],
     "stream": false
   }
   ```
   - Response: Message completion object

3. **Upload File (Optional)**
   - Endpoint: `POST /v1/files`
   - Content-Type: multipart/form-data
   - Response: File ID for reference

### Authentication
- Authorization header: `Bearer {API_KEY}`
- Stored securely in EncryptedSharedPreferences

## Testing Strategy

### Unit Tests
- ViewModel logic testing
- Repository pattern testing
- Use case testing
- Network response parsing
- Database operations

### UI Tests (Espresso/Compose Testing)
- Login flow
- Chat message sending
- Attachment selection
- Theme switching
- Navigation between screens

### Manual Testing Checklist
- [ ] Login with valid/invalid credentials
- [ ] Configure server with various base URLs
- [ ] Send text messages
- [ ] Attach and send images
- [ ] Attach and send PDF files
- [ ] Attach and send DOC files
- [ ] Switch between light/dark themes
- [ ] Test offline behavior
- [ ] Clear chat history
- [ ] Logout functionality
- [ ] Network error handling
- [ ] File size validation
- [ ] Message retry on failure

### Testing Tools
- **Device Testing:** Android Studio Emulator (Pixel 5, API 34)
- **Real Device:** Physical device with Android 7.0+
- **Network Simulation:** Charles Proxy / Android Network Profiler
- **Test Provider:** Use OpenAI-compatible test server (e.g., LocalAI, Text Generation WebUI)

## Google Play Store Submission Guide

### Pre-Submission Checklist
- [ ] App signed with release keystore
- [ ] ProGuard/R8 enabled for code obfuscation
- [ ] All permissions justified in privacy policy
- [ ] Privacy policy URL hosted and accessible
- [ ] App tested on multiple devices/screen sizes
- [ ] Screenshots prepared (phone + tablet)
- [ ] Feature graphic created (1024x500px)
- [ ] App icon finalized (512x512px)
- [ ] Version code and name set correctly

### Required Assets

**App Icon:**
- 512x512 px, 32-bit PNG with alpha
- No transparency, full bleed

**Feature Graphic:**
- 1024x500 px, JPG or 24-bit PNG
- No transparency

**Screenshots:**
- Minimum 2, maximum 8 per device type
- Phone: 16:9 or 9:16 ratio
- 7-inch tablet: 1024x768px minimum
- 10-inch tablet: 1920x1200px minimum

**Privacy Policy:**
- Required (app collects data)
- Must be hosted on publicly accessible URL
- Must cover: data collection, usage, storage, sharing, security

### Google Play Console - Step-by-Step

#### 1. Create Application
- Log into Google Play Console
- Click "Create app"
- Fill in:
  - **App name:** PAi (Personal AI)
  - **Default language:** English (United States)
  - **App or Game:** App
  - **Free or Paid:** Free
  - **Declarations:** Check all boxes confirming compliance

#### 2. Store Listing
Navigate to "Main store listing" under "Grow":

**App Details:**
- **App name:** PAi (Personal AI)
- **Short description (80 chars):**
  ```
  Connect to any AI provider. Chat with attachments. Minimal, powerful interface.
  ```
- **Full description (4000 chars):**
  ```
  PAi (Personal AI) lets you connect to any OpenAI-compatible AI provider using your own API key and base URL.

  KEY FEATURES:
  • Custom AI Provider Support - Configure your own base URL and API key
  • Rich Chat Interface - Clean, futuristic minimal design
  • Attachment Support - Send images, PDFs, and documents
  • Light & Dark Themes - Beautiful themes that sync with your system
  • Secure & Private - All credentials encrypted locally
  • Offline Mode - Access chat history without connection
  • Flexible Models - Choose from provider's available models

  PERFECT FOR:
  • Users with custom AI deployments
  • Privacy-conscious individuals
  • Developers testing AI integrations
  • Anyone wanting control over their AI experience

  REQUIREMENTS:
  • OpenAI-compatible API endpoint
  • Valid API key from your provider

  Your data never leaves your control. All communication is direct between your device and your configured server.
  ```

**Graphics:**
- Upload feature graphic (1024x500px)
- Upload app icon (512x512px)
- Upload phone screenshots (minimum 2)
- Upload 7" tablet screenshots (minimum 2)
- Upload 10" tablet screenshots (minimum 2)

**Categorization:**
- **App category:** Productivity
- **Tags (up to 5):** AI, Chat, Productivity, Customizable, Privacy

**Contact Details:**
- Email: your-email@example.com
- Phone: (Optional)
- Website: (Optional but recommended)

**Privacy Policy:**
- URL: https://yourwebsite.com/privacy-policy

#### 3. App Content

**Privacy Policy:**
- Paste URL to your privacy policy
- Privacy policy must cover:
  - Server configuration data (base URL, API key)
  - Chat messages and attachments
  - Local storage usage
  - No third-party data sharing statement
  - Data deletion process

**App Access:**
- Select "All functionality is available without restrictions"
- (Or provide test credentials if you have login wall)

**Ads:**
- Select "No, my app does not contain ads"

**Content Ratings:**
- Click "Start questionnaire"
- Select category: "Communication, user-generated content"
- Answer questions honestly:
  - Violence: No
  - Sexuality: No
  - Language: No (unless your AI can generate profanity)
  - Controlled substances: No
  - Discriminatory content: No
  - User interaction: Yes (users can communicate)
  - Shares location: No
  - Shares personal info: No (unless your implementation does)
- Submit for rating

**Target Audience:**
- Select age groups: 13+ (or 18+ if AI content unmoderated)

**News App:**
- Select "No"

**COVID-19 Contact Tracing:**
- Select "No"

**Data Safety:**
Click "Start" and fill out:

**Data Collection:**
- **Account info:** Collected
  - Email address (if using login)
  - Shared: No
  - Optional: Yes
  - Purpose: App functionality

- **App activity:** Collected
  - Other user-generated content (chat messages)
  - Shared: No
  - Optional: No
  - Purpose: App functionality

- **Files and docs:** Collected
  - Photos, videos, docs (attachments)
  - Shared: No
  - Optional: Yes
  - Purpose: App functionality

**Security Practices:**
- Data encrypted in transit: Yes
- Data encrypted at rest: Yes (credentials via EncryptedSharedPreferences)
- Users can request data deletion: Yes
- Committed to Google Play Families Policy: No
- Privacy policy: [Your URL]

#### 4. Release

**Countries/Regions:**
- Select "Add countries/regions"
- Choose "Available in all countries" or select specific ones

**Production Track:**
- Navigate to "Production"
- Click "Create new release"

**App Signing:**
- Enroll in Google Play App Signing (recommended)
- Or upload your own signed APK/AAB

**Release Details:**
- Upload AAB file (recommended over APK)
- **Release name:** 1.0.0 (Initial Release)
- **Release notes:**
  ```
  Welcome to PAi v1.0!
  
  Features:
  • Connect to any OpenAI-compatible AI provider
  • Send messages with image, PDF, and document attachments
  • Beautiful light and dark themes
  • Secure credential storage
  • Offline chat history access
  
  This is the initial release. Feedback welcome!
  ```

**Rollout Percentage:**
- Start with 20% rollout
- Monitor for crashes/issues
- Increase to 100% after 24-48 hours if stable

#### 5. App Review

Before submitting:
- Complete all sections in dashboard (all checkmarks green)
- Review "Release dashboard" for any warnings

**Submit for Review:**
- Click "Send for review"
- Typical review time: 1-7 days
- Monitor email for approval/rejection

**If Rejected:**
- Read rejection reason carefully
- Fix issues (usually privacy policy or permissions)
- Resubmit via "Create new release"

### Post-Launch

**Monitor:**
- Crashes (Play Console > Quality > Android vitals)
- ANRs (Application Not Responding)
- User reviews
- Install/uninstall rates

**Update Strategy:**
- Bug fixes: Increment patch version (1.0.1)
- New features: Increment minor version (1.1.0)
- Major changes: Increment major version (2.0.0)

## Privacy Policy Template

Your privacy policy should include:

```
Privacy Policy for PAi (Personal AI)

Last updated: [Date]

1. Information We Collect
PAi stores the following data locally on your device:
- Server configuration (base URL, API key)
- Chat history and messages
- File attachments you choose to send
- App preferences (theme, settings)

2. How We Use Information
All data is stored locally on your device and used solely for app functionality.
Your API credentials are encrypted using Android's secure storage.

3. Data Sharing
PAi does NOT share your data with third parties.
All communication occurs directly between your device and your configured AI provider.
We do not collect, transmit, or have access to your data.

4. Data Security
- API keys stored using EncryptedSharedPreferences
- All network communication uses HTTPS
- No cloud backup of sensitive data

5. Data Retention
Data is retained until you:
- Clear chat history within the app
- Uninstall the application
- Manually delete stored messages

6. Your Rights
You can delete your data at any time by:
- Using "Clear chat history" in Settings
- Uninstalling the app

7. Third-Party Services
Your configured AI provider may have its own privacy policy governing the data you send to their API.
Review your provider's terms of service.

8. Children's Privacy
This app is not intended for children under 13.

9. Changes to This Policy
We will notify users of privacy policy changes via app updates.

10. Contact Us
Email: [your-email@example.com]
```

## Development Timeline Estimate

**Week 1-2: Foundation**
- Project setup with dependencies
- Architecture skeleton
- Database schema
- Network layer with Retrofit
- DI setup with Hilt

**Week 3-4: Core Features**
- Authentication screens
- Server configuration screen
- Basic chat UI
- Message sending/receiving
- Theme implementation

**Week 5-6: Advanced Features**
- Attachment system
- File picker integration
- Image/PDF/DOC handling
- Upload with progress
- Chat history persistence

**Week 7-8: Polish & Testing**
- UI refinements
- Error handling
- Unit tests
- UI tests
- Performance optimization
- Bug fixes

**Week 9: Preparation**
- Create Play Store assets
- Write privacy policy
- Test on multiple devices
- Generate signed release build

**Week 10: Launch**
- Submit to Google Play
- Monitor review process
- Prepare for feedback

## Key Permissions Required (AndroidManifest.xml)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" 
    android:maxSdkVersion="32" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />

<queries>
    <intent>
        <action android:name="android.intent.action.GET_CONTENT" />
        <data android:mimeType="*/*" />
    </intent>
</queries>
```

## Configuration Files

### build.gradle (Project level)
```kotlin
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}
```

### build.gradle.kts (App level)
```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.pai.personalai"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pai.personalai"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
```

### proguard-rules.pro
```
# Keep Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep Gson
-keep class com.google.gson.** { *; }
-keep class com.pai.personalai.data.remote.dto.** { *; }

# Keep Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Keep Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
```

## Success Criteria

The project is complete when:
- [ ] App builds without errors
- [ ] All features functional as described
- [ ] UI matches design specifications
- [ ] Unit tests pass (>70% coverage)
- [ ] UI tests pass for critical flows
- [ ] Manual testing checklist completed
- [ ] App tested on min SDK and target SDK devices
- [ ] No memory leaks detected
- [ ] Network calls handle errors gracefully
- [ ] Privacy policy published
- [ ] Play Store assets created
- [ ] Release build signed and tested
- [ ] App submitted to Google Play

## Maintenance & Updates Plan

**Regular Updates:**
- Monitor crash reports weekly
- Address critical bugs within 48 hours
- Feature updates quarterly
- Security patches as needed
- Dependency updates monthly

**User Feedback:**
- Monitor Play Store reviews
- Implement highly-requested features
- Maintain changelog in app and store listing

## Additional Resources

**Design Tools:**
- Figma for UI mockups
- Material Design 3 guidelines
- Android Design guidelines

**Testing Services:**
- Firebase Test Lab for device testing
- BrowserStack for broader device coverage

**Analytics (Optional):**
- Firebase Analytics (if user consents)
- Google Play Console metrics

**Crash Reporting:**
- Firebase Crashlytics (recommended)

## Notes for AI Development

When implementing this project:
1. Start with the data layer (database + network)
2. Build repository implementations
3. Create ViewModels with state management
4. Implement Compose UI screens
5. Add navigation
6. Integrate file handling
7. Implement theme system
8. Add error handling
9. Write tests
10. Optimize and polish

Focus on clean architecture principles, separation of concerns, and testability. Use Kotlin coroutines for async operations and Compose state management for UI reactivity.
