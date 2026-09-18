# pAI - Your Personal AI Companion

[![F-Droid](https://img.shields.io/badge/F--Droid-Not%20listed-blue.svg?logo=F-Droid)](https://f-droid.org)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg?logo=GNU)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg?logo=Android)](https://www.android.com)
[![API](https://img.shields.io/badge/API-26%2B-blue.svg)](https://developer.android.com/about/versions/14)

**pAI** is a free, open-source Android app that brings the power of AI directly to your pocket. Connect to your favorite AI backend, chat intelligently, manage configurations, and experience a beautifully crafted Material Design 3 interface — all without ads, tracking, or proprietary dependencies.

---

## ✨ Features

- 🤖 **AI Chat Interface** — Seamless conversation with AI models via configurable backend endpoints
- ⚙️ **Server Configuration** — Easily switch between different AI providers and API keys
- 💾 **Local DataStorage** — Your chats and settings stay on your device using Room Database
- 🔒 **Security First** — API keys stored securely using Android Keystore
- 🎨 **Material You** — Dynamic theming with Material Design 3
- 🌓 **Dark Mode** — Full support for system dark/light themes
- 📱 **Adaptive Icon** — Crisp vector-based launcher icon on all densities
- 🚫 **No Ads, No Tracking** — 100% free and open source

---

## 📸 Screenshots

| Chat Screen | Server Config | Settings | Light mode | Chat history |
|-------------|---------------|----------|------------|--------------|
| ![chat screen](https://github.com/lalithchary/pAI/blob/main/screenshots/ss1.png) | ![server config](https://github.com/lalithchary/pAI/blob/main/screenshots/ss3.png) | ![Settings](https://github.com/lalithchary/pAI/blob/main/screenshots/ss2.png) |![lightmode](https://github.com/lalithchary/pAI/blob/main/screenshots/ss4.png) | ![chat history](https://github.com/lalithchary/pAI/blob/main/screenshots/ss5.png) |

---

## 📥 Download

### F-Droid (Recommended)
pAI is submitted to F-Droid and will be available soon.  
Once approved, you can install it directly from the F-Droid app.

### GitHub Releases
Download the latest APK from the [![Latest Release](https://img.shields.io/github/release/lalithchary/pAI.svg)](https://github.com/lalithchary/pAI/releases) page.

### Alternative Stores
You can also sideload the APK from trusted alternative app stores.

---

## 🔨 Build from Source

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK API 34

### Steps
1. **Clone the repository**
   ```bash
   git clone https://github.com/lalithchary/pAI.git
   cd pAI
   ```

2. **Open in Android Studio**
   - Open the project folder in Android Studio
   - Wait for Gradle sync to complete

3. **Build the app**
   ```bash
   ./gradlew assembleRelease
   ```
   The release APK will be generated at:
   ```
   app/build/outputs/apk/release/app-release.apk
   ```

4. **Generate signed bundle (for distribution)**
   ```bash
   ./gradlew bundleRelease
   ```
   The signed AAB will be generated at:
   ```
   app/build/outputs/bundle/release/app-release.aab
   ```

---

## 📋 Project Structure

```
pAI/
├── app/
│   ├── src/main/
│   │   ├── java/com/pai/personalai/   # Kotlin source code
│   │   └── res/                        # Android resources
│   └── build.gradle.kts                # App-level build config
├── logo/                               # SVG source icon
├── build.gradle.kts                    # Project-level build config
├── settings.gradle.kts                 # Gradle settings
├── LICENSE                             # GPLv3 License
└── README.md                           # This file
```

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the GNU General Public License v3.0.  
See the [LICENSE](LICENSE) file for details.

---

## 🙋 Support

If you encounter any issues or have questions:
- Open an [Issue](https://github.com/lalithchary/pAI/issues)
- Check the [Discussions](https://github.com/lalithchary/pAI/discussions) tab

---

## 🗺️ Roadmap

- [ ] Multi-model support (Claude, Gemini, etc.)
- [ ] Conversation export/import
- [ ] Markdown rendering
- [ ] Voice input
- [ ] Custom themes

---

## ⭐ Star History

If you find this project useful, please consider giving it a star!

---

<p align="center">
  Made with ❤️ by lalithchary
</p>
