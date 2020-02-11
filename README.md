# ArchMark
An app for Android to help archers keep score during practice sessions or tournaments.

ArchMark's intuitive interface allows for easy and fast, yet precise data input and storage, allowing you to see real time data analysis and insights. 

## Images
![](https://i.imgur.com/XZn7m7R.png?1) ![](https://i.imgur.com/rdbNW4i.png?2)

## Installation/Compilation
### Install
Official releases coming soon!
### Compile and Install
**Compilation requires a copy of the Android SDK (command line tools).**
1. Download the Android command line tools from Android Studio's website (https://developer.android.com/studio#downloads) and extract it.
2. Run `sdkmanager --licenses` in `sdk/bin` to accept licenses easily.
3. Add a file called `local.properties` to the root of ArchMark, and add the line `sdk.dir=/the/location/of/your/sdk`.
4. Use the gradle wrapper included with ArchMark and run `gradlew assemble` to build the app. This will create several build folders.
5. Install the signed apk generated at `app/build/outputs/apk/debug` using adb, or manually.

