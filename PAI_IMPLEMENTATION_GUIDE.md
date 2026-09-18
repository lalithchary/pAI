# PAi Implementation Guide for AI Development

## Quick Start Instructions for AI

This guide helps AI assistants understand how to implement the PAi Android app step-by-step.

## Implementation Order

### Phase 1: Project Setup (Files 1-10)

1. **Create gradle files:**
   - `settings.gradle.kts`
   - `build.gradle` (project root)
   - `app/build.gradle.kts`
   - `gradle.properties`
   - `proguard-rules.pro`

2. **Create manifest:**
   - `app/src/main/AndroidManifest.xml`

3. **Create resource files:**
   - `app/src/main/res/values/strings.xml`
   - `app/src/main/res/values/colors.xml`
   - `app/src/main/res/values/themes.xml`
   - `app/src/main/res/xml/file_paths.xml` (for file provider)

### Phase 2: Data Layer (Files 11-30)

4. **Create domain models:**
   ```
   app/src/main/java/com/pai/personalai/domain/model/
   - Message.kt (id, content, role, timestamp, attachments, status)
   - Chat.kt (id, title, lastMessage, timestamp)
   - Attachment.kt (id, type, uri, name, size, uploadStatus)
   - ServerConfig.kt (baseUrl, apiKey, selectedModel)
   - AttachmentType.kt (enum: IMAGE, PDF, DOC)
   - MessageStatus.kt (enum: SENDING, SENT, FAILED)
   ```

5. **Create database entities:**
   ```
   app/src/main/java/com/pai/personalai/data/local/entity/
   - MessageEntity.kt (with @Entity annotation)
   - ChatEntity.kt
   - AttachmentEntity.kt
   ```

6. **Create DAOs:**
   ```
   app/src/main/java/com/pai/personalai/data/local/dao/
   - MessageDao.kt (insert, getAll, delete, update)
   - ChatDao.kt
   ```

7. **Create database:**
   ```
   app/src/main/java/com/pai/personalai/data/local/database/
   - AppDatabase.kt (RoomDatabase with entities and DAOs)
   - Converters.kt (for Date, List<Attachment> type conversion)
   ```

8. **Create API DTOs:**
   ```
   app/src/main/java/com/pai/personalai/data/remote/dto/
   - ChatRequest.kt (model, messages array, stream)
   - ChatResponse.kt (id, choices array, usage)
   - MessageDto.kt (role, content)
   - ModelResponse.kt (data array of models)
   - FileUploadResponse.kt (id, filename)
   ```

9. **Create API service:**
   ```
   app/src/main/java/com/pai/personalai/data/remote/api/
   - AIApiService.kt (Retrofit interface)
     @GET("v1/models")
     @POST("v1/chat/completions")
     @Multipart @POST("v1/files")
   ```

10. **Create interceptors:**
    ```
    app/src/main/java/com/pai/personalai/data/remote/interceptor/
    - AuthInterceptor.kt (adds Bearer token)
    - LoggingInterceptor.kt (for debugging)
    ```

11. **Create repositories:**
    ```
    app/src/main/java/com/pai/personalai/data/repository/
    - ChatRepositoryImpl.kt
    - AuthRepositoryImpl.kt
    - ConfigRepositoryImpl.kt
    - FileRepositoryImpl.kt
    
    app/src/main/java/com/pai/personalai/domain/repository/
    - ChatRepository.kt (interface)
    - AuthRepository.kt
    - ConfigRepository.kt
    - FileRepository.kt
    ```

### Phase 3: Dependency Injection (Files 31-35)

12. **Create DI modules:**
    ```
    app/src/main/java/com/pai/personalai/di/
    - AppModule.kt (Application, Context)
    - DatabaseModule.kt (Room, DAOs)
    - NetworkModule.kt (Retrofit, OkHttp, APIService)
    - RepositoryModule.kt (bind repository implementations)
    ```

13. **Create Application class:**
    ```
    app/src/main/java/com/pai/personalai/
    - PAiApplication.kt (@HiltAndroidApp)
    ```

### Phase 4: Use Cases (Files 36-42)

14. **Create use cases:**
    ```
    app/src/main/java/com/pai/personalai/domain/usecase/
    - SendMessageUseCase.kt
    - GetChatHistoryUseCase.kt
    - UploadAttachmentUseCase.kt
    - SaveConfigUseCase.kt
    - TestConnectionUseCase.kt
    - GetModelsUseCase.kt
    - DeleteChatHistoryUseCase.kt
    ```

### Phase 5: UI Theme (Files 43-46)

15. **Create theme files:**
    ```
    app/src/main/java/com/pai/personalai/presentation/theme/
    - Color.kt (light/dark color definitions)
    - Type.kt (typography scale)
    - Theme.kt (PAiTheme composable)
    - Shape.kt (corner shapes)
    ```

### Phase 6: Utilities (Files 47-52)

16. **Create utility classes:**
    ```
    app/src/main/java/com/pai/personalai/util/
    - Constants.kt (API endpoints, limits, keys)
    - FileUtils.kt (getMimeType, getFileSize, copyToCache)
    - NetworkUtils.kt (isNetworkAvailable)
    - DateUtils.kt (formatTimestamp)
    - SecurePreferences.kt (EncryptedSharedPreferences wrapper)
    - Resource.kt (sealed class for Success/Error/Loading)
    ```

### Phase 7: ViewModels & UI State (Files 53-58)

17. **Create UI state classes:**
    ```
    app/src/main/java/com/pai/personalai/presentation/screens/
    - chat/ChatUiState.kt
    - login/LoginUiState.kt
    - settings/SettingsUiState.kt
    - config/ConfigUiState.kt
    ```

18. **Create ViewModels:**
    ```
    - chat/ChatViewModel.kt (@HiltViewModel)
    - login/LoginViewModel.kt
    - settings/SettingsViewModel.kt
    - config/ServerConfigViewModel.kt
    ```

### Phase 8: UI Components (Files 59-70)

19. **Create reusable components:**
    ```
    app/src/main/java/com/pai/personalai/presentation/components/
    - LoadingIndicator.kt
    - ErrorDialog.kt
    - PrimaryButton.kt
    - SecondaryButton.kt
    - CustomTextField.kt
    - ThemeToggle.kt
    - EmptyState.kt
    ```

20. **Create chat-specific components:**
    ```
    app/src/main/java/com/pai/personalai/presentation/screens/chat/components/
    - MessageBubble.kt (user vs AI styling)
    - ChatInput.kt (text field + attachment button)
    - AttachmentPreview.kt (thumbnail with remove button)
    - TypingIndicator.kt (animated dots)
    - AttachmentButton.kt (bottom sheet trigger)
    - MessageStatusIcon.kt (sending/sent/failed indicator)
    ```

### Phase 9: Screens (Files 71-78)

21. **Create screens:**
    ```
    - splash/SplashScreen.kt (check if configured)
    - login/LoginScreen.kt (email/password fields)
    - config/ServerConfigScreen.kt (base URL, API key, model selector)
    - chat/ChatScreen.kt (messages, input, attachment picker)
    - settings/SettingsScreen.kt (theme, logout, clear history, server config)
    ```

### Phase 10: Navigation (Files 79-80)

22. **Create navigation:**
    ```
    app/src/main/java/com/pai/personalai/presentation/navigation/
    - NavGraph.kt (NavHost with all routes)
    - Screen.kt (sealed class with route constants)
    ```

### Phase 11: MainActivity (File 81)

23. **Create MainActivity:**
    ```
    app/src/main/java/com/pai/personalai/
    - MainActivity.kt (setContent with PAiTheme and NavGraph)
    ```

### Phase 12: Testing (Files 82-90)

24. **Create tests:**
    ```
    app/src/test/java/com/pai/personalai/
    - viewmodel/ChatViewModelTest.kt
    - viewmodel/ConfigViewModelTest.kt
    - repository/ChatRepositoryTest.kt
    - usecase/SendMessageUseCaseTest.kt
    
    app/src/androidTest/java/com/pai/personalai/
    - ui/ChatScreenTest.kt
    - ui/LoginFlowTest.kt
    - database/MessageDaoTest.kt
    ```

## Key Implementation Details

### 1. Server Configuration Storage

```kotlin
class SecurePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        "pai_secure_prefs",
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    fun saveConfig(config: ServerConfig) {
        encryptedPrefs.edit()
            .putString("base_url", config.baseUrl)
            .putString("api_key", config.apiKey)
            .putString("model", config.selectedModel)
            .apply()
    }
}
```

### 2. Dynamic Retrofit Instance

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(securePreferences: SecurePreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val config = securePreferences.getConfig()
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${config?.apiKey}")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        securePreferences: SecurePreferences
    ): Retrofit {
        val baseUrl = securePreferences.getConfig()?.baseUrl ?: "https://api.openai.com/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
```

### 3. File Attachment Handling

```kotlin
object FileUtils {
    fun getFileFromUri(context: Context, uri: Uri): File? {
        val contentResolver = context.contentResolver
        val mimeType = contentResolver.getType(uri)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        
        val cacheFile = File(context.cacheDir, "upload_${System.currentTimeMillis()}.$extension")
        
        contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(cacheFile).use { output ->
                input.copyTo(output)
            }
        }
        
        return cacheFile
    }
    
    fun validateFileSize(context: Context, uri: Uri): Boolean {
        val size = context.contentResolver.openFileDescriptor(uri, "r")?.statSize ?: 0
        return size <= 10 * 1024 * 1024 // 10MB
    }
}
```

### 4. Message Bubble Composable

```kotlin
@Composable
fun MessageBubble(
    message: Message,
    isFromUser: Boolean
) {
    val backgroundColor = if (isFromUser) {
        Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary))
    } else {
        Brush.linearGradient(listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.surface))
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = if (isFromUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(backgroundColor, RoundedCornerShape(16.dp, 16.dp, 
                    if (isFromUser) 4.dp else 16.dp, 
                    if (isFromUser) 16.dp else 4.dp))
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = message.content,
                    color = if (isFromUser) Color.White else MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
                
                message.attachments.forEach { attachment ->
                    AttachmentPreview(attachment)
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = DateUtils.formatTime(message.timestamp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isFromUser) Color.White.copy(alpha = 0.7f) 
                               else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    
                    if (isFromUser) {
                        Spacer(modifier = Modifier.width(4.dp))
                        MessageStatusIcon(message.status)
                    }
                }
            }
        }
    }
}
```

### 5. Chat Screen with LazyColumn

```kotlin
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val messages = uiState.messages
    val listState = rememberLazyListState()
    
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PAi") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                state = listState
            ) {
                items(messages) { message ->
                    MessageBubble(
                        message = message,
                        isFromUser = message.role == "user"
                    )
                }
                
                if (uiState.isTyping) {
                    item { TypingIndicator() }
                }
            }
            
            ChatInput(
                onSendMessage = { text, attachments ->
                    viewModel.sendMessage(text, attachments)
                },
                enabled = !uiState.isLoading
            )
        }
    }
}
```

### 6. Theme Implementation

```kotlin
@Composable
fun PAiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF818CF8),
            secondary = Color(0xFFA78BFA),
            background = Color(0xFF0F0F0F),
            surface = Color(0xFF1A1A1A),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFFF9FAFB),
            onSurface = Color(0xFFF9FAFB),
            error = Color(0xFFF87171)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF6366F1),
            secondary = Color(0xFF8B5CF6),
            background = Color.White,
            surface = Color(0xFFF8F9FA),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1F2937),
            onSurface = Color(0xFF1F2937),
            error = Color(0xFFEF4444)
        )
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
```

## Testing Examples

### Unit Test Example

```kotlin
@ExperimentalCoroutinesTest
class ChatViewModelTest {
    
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private lateinit var viewModel: ChatViewModel
    private lateinit var sendMessageUseCase: SendMessageUseCase
    private lateinit var getChatHistoryUseCase: GetChatHistoryUseCase
    
    @Before
    fun setup() {
        sendMessageUseCase = mockk()
        getChatHistoryUseCase = mockk()
        viewModel = ChatViewModel(sendMessageUseCase, getChatHistoryUseCase)
    }
    
    @Test
    fun `sendMessage updates UI state correctly`() = runTest {
        val message = "Hello AI"
        coEvery { sendMessageUseCase(any()) } returns flowOf(Resource.Success(mockMessage))
        
        viewModel.sendMessage(message, emptyList())
        
        advanceUntilIdle()
        
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.messages.isNotEmpty())
    }
}
```

### UI Test Example

```kotlin
@HiltAndroidTest
class ChatScreenTest {
    
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()
    
    @Test
    fun sendMessage_displaysInChatList() {
        composeRule.setContent {
            PAiTheme {
                ChatScreen()
            }
        }
        
        composeRule.onNodeWithTag("chat_input").performTextInput("Test message")
        composeRule.onNodeWithTag("send_button").performClick()
        
        composeRule.onNodeWithText("Test message").assertIsDisplayed()
    }
}
```

## Build Commands

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release AAB
./gradlew bundleRelease

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Generate test coverage report
./gradlew jacocoTestReport

# Lint check
./gradlew lint
```

## Signing Configuration

Add to `app/build.gradle.kts`:

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("../keystore/pai-release.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

## Implementation Checklist for AI

When implementing, follow this order:

**Phase 1: Foundation**
- [ ] Create all Gradle files
- [ ] Set up AndroidManifest.xml
- [ ] Create resource files (strings, colors, themes)
- [ ] Create Application class with Hilt

**Phase 2: Data**
- [ ] Define domain models
- [ ] Create database entities and DAOs
- [ ] Set up Room database
- [ ] Create API DTOs
- [ ] Implement Retrofit service
- [ ] Create repositories

**Phase 3: DI**
- [ ] Set up all Hilt modules
- [ ] Configure dependency injection

**Phase 4: Business Logic**
- [ ] Implement use cases
- [ ] Create utility classes

**Phase 5: UI Foundation**
- [ ] Create theme files
- [ ] Build reusable components
- [ ] Set up navigation

**Phase 6: Features**
- [ ] Implement ViewModels
- [ ] Create all screens
- [ ] Connect UI to ViewModels

**Phase 7: Polish**
- [ ] Add error handling
- [ ] Implement loading states
- [ ] Add animations
- [ ] Optimize performance

**Phase 8: Testing**
- [ ] Write unit tests
- [ ] Write integration tests
- [ ] Manual testing

**Phase 9: Release**
- [ ] Configure ProGuard
- [ ] Set up signing
- [ ] Build release AAB
- [ ] Test release build

## Common Pitfalls to Avoid

1. **Don't hardcode base URL** - Must be configurable
2. **Don't store API keys in plain text** - Use EncryptedSharedPreferences
3. **Don't forget file provider** - Required for file sharing
4. **Don't skip error handling** - Network can fail
5. **Don't forget permissions** - Storage and internet
6. **Don't use flow operators incorrectly** - Collect in ViewModel
7. **Don't forget to handle configuration changes** - Use ViewModel
8. **Don't skip ProGuard rules** - Keep Retrofit/Gson classes
9. **Don't forget content URIs** - Use proper file handling
10. **Don't skip null checks** - Kotlin nullable types

## File Count Summary

Total files needed: ~90
- Gradle/Config: 5
- Manifest/Resources: 5
- Domain: 8
- Data: 25
- DI: 5
- Use Cases: 7
- Theme: 4
- Utils: 6
- ViewModels: 8
- Components: 12
- Screens: 5
- Navigation: 2
- Tests: 8

## Estimated Lines of Code

- Gradle files: ~400 lines
- Data layer: ~2,500 lines
- Domain layer: ~600 lines
- DI: ~300 lines
- UI: ~3,000 lines
- Tests: ~1,200 lines
- **Total: ~8,000 lines**

This is a medium-sized Android project, suitable for 1-2 developers working 6-8 weeks.
