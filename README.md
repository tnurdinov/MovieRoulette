# Movie Roulette 📽 (work-in-progress 👨‍💻👩‍💻)

Movie Roulette is a **work-in-progress** movie suggestion Android app which connects to themoviedb.org.
 It just passed first release state. Currently, only MVP functionality implemented.
 
## Android development

Movie Roulette is an app which attempts to use the latest cutting edge libraries and tools. As a summary:

 * Entirely written in [Kotlin](https://kotlinlang.org/)
 * Uses [Kotlin coroutines](https://github.com/Kotlin/kotlinx.coroutines)
 * Uses part of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/): LiveData and Lifecycle-components
 * Uses [Glide v4](https://github.com/bumptech/glide)

## Build Status

[![Build Status](https://app.bitrise.io/app/9c64947803dc134c/status.svg?token=t-Upyml36NELxtWra5vOhg&branch=develop)](https://app.bitrise.io/app/9c64947803dc134c)

## Try it on Play Store

<a href="https://play.google.com/store/apps/details?id=com.tnurdinov.movieroulette">
    <img alt="Get it on Google Play"
        height="80"
        src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" />
</a>
</div>
</br></br>

## Screenshots

<div style="display:flex;" >
<img  src="https://i.imgur.com/ySDQOZn.png" width="19%" >
<img style="margin-left:10px;" src="https://i.imgur.com/iT2Q5xS.png" width="19%" >

</div>

## Development setup

First off, you require the latest Android Studio 3.2 (or newer) to be able to build the app.

### API keys

You need to supply API / client key for
[TMDb](https://developers.themoviedb.org/4/getting-started)

Add it to `local.properties` file like below
```
tmdb_api_key=YOUR_API_KEY
```

## Contributions

If you've found an error in this sample, please file an issue.

Patches are encouraged and may be submitted by forking this project and
submitting a pull request. Since this project is still in its very early stages,
if your change is substantial, please raise an issue first to discuss it.

## License

```
Copyright (c) 2018 Temirlan Nurdinov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
