# Android JSON API Example

This app uses MVVM with Android Jetpack to display a sorted list of albums retrieved from [an api](https://jsonplaceholder.typicode.com/) which persists for offline viewing.

### Possible Improvements

* Extend functionality to support other resource types: posts, photos, users etc..
* API pagination support
* Photos (with a library such as [glide](https://github.com/bumptech/glide))
* Use data binding with more complex UI
* Update testing libraries (junit 5 is not widely adopted by the android community)

### Libraries

* [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html)
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
* [Retrofit](https://square.github.io/retrofit/) for REST api communication
* [espresso](https://developer.android.com/training/testing/espresso) for UI tests
* [MockK](https://mockk.io/) for mocking in tests
* [Retrofit Mock](https://github.com/square/retrofit/tree/master/retrofit-mock) for creating a fake API implementation for tests


Forked from [Android Architecture Components samples](https://github.com/android/architecture-components-samples)