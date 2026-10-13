# Immich TV Legacy

An **unofficial Android 6.0+ compatibility fork** of
[Immich Android TV](https://github.com/giejay/Immich-Android-TV), for older TVs.
This project is not an official Immich client. Upstream attribution and licensing
are preserved; the original project documentation follows below.

## Getting started / 使用指南

**[中文使用指南：获取 APK → 安装 → API Key 登录 → 更新与故障排查](docs/USAGE.zh-CN.md)**

This TV app browses an existing Immich server; it does not host the server or
back up your phone. There are currently **no prebuilt APK releases** in this fork.
Build the Legacy APK using the instructions below, or check the fork's
[Releases page](https://github.com/micate/Immich-TV-Legacy/releases) for future APK
attachments. A source archive is not an installable APK, and upstream APKs are
not Android 6 compatibility builds.

1. Put your TV and Immich server on a trusted network where they can communicate.
2. Build and sideload the APK using your TV's installer or network ADB.
3. Sign into the Immich web app and create a dedicated, limited-permission API key.
4. On TV choose **Sign in by API key**, enter your server's root URL and the key,
   then select **Submit**. Direct username/password login is not available.
5. Use the remote's direction, OK and Back buttons to browse. Keep SSL verification
   enabled; do not publish your API key or expose ADB to the internet.

Updates require the same signing key to preserve the existing installation.
See the Chinese guide for ADB commands, signing conflicts and troubleshooting.

## Legacy compatibility

- Minimum Android version: **6.0 / API 23**.
- Tested on a Xiaomi MiTV4-ANSM0 (Android 6.0.1, 32-bit ARM) with **Immich Server 3.1.0**.
- Verified login, timeline, full-screen PNG viewing, video rendering and memories.
  Not all codecs, audio output, or screensaver features have been verified.
- Uses AndroidX Core 1.17.0 and older Firebase versions compatible with API 23.
- Fixes media-opening crashes caused by `Locale.Category`, absent on Android 6.
- Retains the upstream application ID. Updating an existing installation requires
  the same signing key; differently signed builds cannot replace each other.
- This compatibility build does not imply ongoing security support for Android 6.
  Prefer a trusted LAN and a dedicated, limited-permission Immich API key.

## Building the Legacy debug APK

Use JDK 21 and an Android SDK with API 37 and the required build tools installed.
Set `JAVA_HOME` and `ANDROID_HOME`, then clone this fork and run:

```sh
git clone --branch android6-compat https://github.com/micate/Immich-TV-Legacy.git
cd Immich-TV-Legacy
cp app/google-services.example app/google-services.json
cp app/strings_other.xml.example app/src/main/res/values/strings_other.xml
./gradlew :app:assembleDebug
./gradlew :app:testDebugUnitTest --tests '*AssetDateFormattingTest' --tests '*AssetMetaDataMappingTest'
```

The example configs are placeholders, not production Firebase or demo credentials.
The APK is generated under `app/build/outputs/apk/debug/` and uses the standard
Android debug key by default. To reuse your own debug signing key, set
`DEBUG_KEYSTORE_PATH`, `DEBUG_KEYSTORE_PASSWORD`, `DEBUG_KEY_ALIAS`, and
`DEBUG_KEY_PASSWORD` in your local environment. Never commit keys or credentials.
Release builds require your own release signing configuration.

---

## Original upstream documentation

The following describes upstream features and setup. For this fork, use the
Legacy build instructions and usage guide above; upstream release APKs and
upstream cloning instructions do not include this fork's compatibility fixes.
Upstream feature claims are not a guarantee that every feature has been tested
on Android 6.

Immich is a self hosted backup solution for photos and videos. Current features include:

- Upload and view videos and photos
- Auto backup when the app is opened
- Selective album(s) for backup
- Multi-user support
- Album and Shared albums

More info here: https://github.com/immich-app/immich

## Compatibility

This app is compatible with Immich server **v3.0.0 and newer**. Immich 3.0.0 introduced several
breaking API changes (album asset loading via `search/metadata`, search visibility defaults,
error response format); this app has been adapted accordingly. No additional API key permissions
are required for v3 compared to earlier versions — the existing `asset.read`/`album.read`
permissions listed below already cover the endpoints this app uses.

This Android TV app will allow you to view those uploaded photos and videos. Current features
include:

| Features                                                                       | Status       |
|:-------------------------------------------------------------------------------|--------------|
| Sign in by phone (https://github.com/giejay/Immich-Android-TV-Authentication)  | Done         |
| Sign in by entering API key                                                    | Done         |
| Demo environment                                                               | Done         |
| Album fetching + Lazy loading                                                  | Done         |
| Showing the photos inside an album                                             | Done         |
| Showing people, random, recent or seasonal photos                              | Done         |
| Slideshow of the photos and videos with a configured interval                  | Done         |
| Setting the app as the screensaver                                             | Done         |
| Setting the albums to show in the screensaver                                  | Done         |
| Configure the interval of the screensaver                                      | Done         |
| Add generic sorting of albums and photos                                       | Done         |
| Add sorting for specific album (select last item in row and press right again) | Done         |
| Showing the 4K thumbnail instead of the full image to speed up loading         | Done         |
| Showing the EXIF data and improving the slideshow view                         | Done         |
| Configure whether to play sound with videos                                    | Done         |
| Smarter merging of portrait photos (same people, same date, same city)         | Todo         |
| Add transitions to slideshow                                                   | Done         |
| Add places/tags view                                                           | Todo         |
| Add background media playing info to screensaver                               | Todo         |
| Casting capabilities                                                           | Out of scope |
| Searching in and for albums                                                    | Todo         |
| Dependency injection with Hilt/Dagger                                          | Todo         |

## Required API Permissions

When setting up your API key in Immich, make sure to grant the following permissions for the app to function properly:

- `activity.read` - Read activity data
- `album.download` - Download album content
- `album.read` - Read album information
- `archive.read` - Read archived items
- `asset.download` - Download assets for viewing
- `asset.read` - Read asset metadata
- `asset.view` - View assets (photos/videos)
- `face.read` - Read face detection data
- `folder.read` - Read folder information
- `library.read` - Read library information
- `memory.read` - Read memory/moment data
- `partner.read` - Read partner sharing data
- `person.read` - Read person/people data
- `session.read` - Read session information
- `tag.asset` - Read asset tag associations
- `tag.read` - Read tag information
- `timeline.read` - Read timeline data

## Screenshots

|                                                                                    |                                                                      |                                                                                    |
|:----------------------------------------------------------------------------------:|:--------------------------------------------------------------------:|:----------------------------------------------------------------------------------:|
|        ![Alt text](/screenshots/homescreen-1.png?raw=true "Album overview")        |      ![Alt text](/screenshots/photos.png?raw=true "All photos")      |      ![Alt text](/screenshots/sorting-options.png?raw=true "Sorting options")      |
|         ![Alt text](/screenshots/home-edit.png?raw=true "Edit homescreen")         | ![Alt text](/screenshots/settings-view.png?raw=true "View settings") | ![Alt text](/screenshots/settings-screensaver.png?raw=true "Screensaver settings") |
| ![Alt text](/screenshots/screensaver-portrait.png?raw=true "Screensaver portrait") |        ![Alt text](/screenshots/people.png?raw=true "People")        |             ![Alt text](/screenshots/seasonl.png?raw=true "Seasonal")              |

## Build steps

1. Clone project with `git clone --recurse git@github.com:giejay/Immich-Android-TV.git`
2. Create an account at firebase and create a google-services.json file, or
   `cp app/google-services.example app/google-services.json`
3. copy app/strings_other.xml.example to app/src/main/res/values/strings_other.xml and modify
   the address and API keys for your demo server.
4. Build apk with `./gradlew assembleRelease`

## Support the project

You can support the project in several ways. The first one is by creating nice descriptive bug
reports if you find any: https://github.com/giejay/Immich-Android-TV/issues/new/choose.
<br><br>Even better is creating a PR: https://github.com/giejay/Immich-Android-TV/pulls.
<br><br>
Lastly, if you feel this Android TV app is a useful addition to the already great Immich app, you
might consider buying me a coffee or a beer:

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/giejay)

## FAQ

#### I'n not able to set the app as a screensaver

1. Enable development mode on the device (click the build number or "Android TV OS Build" 7 times in
   the System->About settings).
2. Go to System -> Developer Options and enable USB Debugging.
3. If you don't have ADB installed on your PC, follow these
   instructions: https://www.xda-developers.com/install-adb-windows-macos-linux/
4. After downloading/installing ADB on the PC, connect to the device using it's IP: adb connect
   192.168.xx.xx.
5. If you have an Amazon Fire TV, first execute this command and reboot after: 'adb shell settings put secure amazon_ambient_enabled 0'
6. Execute the following command: 'adb shell settings put secure
   screensaver_components nl.giejay.android.tv.immich/.screensaver.ScreenSaverService'
7. Done!
