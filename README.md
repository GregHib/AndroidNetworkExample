# Albums Json API List

This app uses MVVM with Android Jetpack to display a sorted list of albums retrieved from [an api](https://jsonplaceholder.typicode.com/) which persists for offline viewing.

### Possible Improvements

* Extend functionality to support other resource types: posts, photos, users etc..
* API pagination support
* Photos (with a library such as glide)
* Use data binding with more complex UI
* Update testing libraries (junit 5 is not widely adopted by android community)

### Libraries

* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Retrofit][retrofit] for REST api communication
* [espresso][espresso] for UI tests
* [MockK][mockk] for mocking in tests
* [Retrofit Mock][retrofit-mock] for creating a fake API implementation for tests


Forked from [Android Architecture Components samples](https://github.com/android/architecture-components-samples)