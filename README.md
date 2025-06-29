A sample app demonstrating Kotlin Multiplatform functionality.  
Targeting Android, iOS, Web, Desktop.
Uses Koin as a DI framework.
Supports light and dar mode.

Known issue: when screens are being transitioned in when animation, button clicks are not available
until transition is complete. So if you click a button too fast after navigation, a button press wont do anything.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.