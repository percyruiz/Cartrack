# Cartrack Mobile Application Challenge

This application displays a list of users obtained from https://jsonplaceholder.typicode.com/users and shows detail views for each users.

It has a simple login screen with a pair of username password initialized upon install.

## Installation
Clone this repository and import into **Android Studio**
```bash
https://github.com/percyruiz/Cartrack.git
```
Login using the ff:
```
username = username
password = password
```

## Screenshots
<p align="center">
  <img src="https://user-images.githubusercontent.com/16864893/129060842-25c10e75-29e2-4225-87b2-fe0d4976bfbe.png" width="25%" height="25%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://user-images.githubusercontent.com/16864893/129060973-d2211a53-8bf0-47ff-8914-4333f88db2d9.png" width="25%" height="25%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://user-images.githubusercontent.com/16864893/129061097-ae60bdbd-3c41-442a-989a-c1f25309bc82.png" width="25%" height="25%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://user-images.githubusercontent.com/16864893/129061501-f99affed-7836-4be7-9308-4c138864e235.png" width="25%" height="25%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>

Tablet Master-Detail Screen
<p align="center">
  <img src="https://user-images.githubusercontent.com/16864893/129061752-8c16c473-1a19-40b2-8084-3b395e08a3fe.png" width="25%" height="25%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://user-images.githubusercontent.com/16864893/129062018-40c48db6-d6e8-4ca4-bb31-efc60c9a4992.png" width="25%" height="25%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>

## Architecture
The app uses MVVM architecture which takes advantage of Android Jetpack's Android Architecture Components https://developer.android.com/topic/libraries/architecture.
<p align="center">
  <img src="https://user-images.githubusercontent.com/16864893/126056079-2c0e8155-2201-45e6-bf3f-514eda1c39ff.png" width="50%" height="50%"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>

## Dependencies
- Dependency injection (with [Koin](https://www.koin.com/))
- Reactive programming (with [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html))
- Http client (with [Retrofit](https://square.github.io/retrofit/))
- Paging implementation which handles loading of items and caching to db (with [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview))
- Google [Material Design](https://material.io/blog/android-material-theme-color) library
- Android architecture components

## Maintainers
This project is mantained by:
* [Percival Ruiz](https://github.com/percyruiz)
