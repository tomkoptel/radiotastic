## CI status
[![Build Status](https://travis-ci.org/tomkoptel/radiotastic.svg?branch=master)](https://travis-ci.org/tomkoptel/radiotastic)
## Description
Internet Radio client which was designed all around [Dirble API](https://dirble.com/developer/api).

User can easily navigate between main radio station categories grouped by music genre. 

For specific station user can start playback and continue listening to it while app in background.

User also has an access to additional meta data like station bitrate and song history.

## Song history
At the moment app doesn't provide any real time updates of song history. It simply consumes items from API 
and display them as it is.

## Tribute 

Dependencies list and reason for usages.

1. Sub project pojo. Consumes **jsonschema2json gradle plugin**. Used for generating java classes on the basis of json. This classes lately mapped to json incoming from API. Library uses gson for deserializing json data to java objects.
  * https://github.com/joelittlejohn/jsonschema2pojo
  * https://code.google.com/p/google-gson/

2. **android-contentprovider-generator** project used to generate content provider and associated model objects from json mapping. Generated classes lately used to provide convenient way of accessing data from cursor objects.
  * https://github.com/BoD/android-contentprovider-generator

3. **appcompat-v7** provides backward compatibility of application to older versions of app. Also provides theme app consumes in purpose of implementing material design theme.
  * https://github.com/android/platform_frameworks_support/tree/master/v7/appcompat

4. **pallete-v7** app uses during calculating swatches when station provides placeholder for station it extracts light color and applies it to the ImageView background.
  * https://github.com/android/platform_frameworks_support/tree/master/v7/palette

5. **cardview-v7** custom view used to wrap detail station page.
  * https://github.com/android/platform_frameworks_support/tree/master/v7/cardview

6. **recyclerview-v7** used instead of ListView. Provides efficient and convenient way to display big data sets.
  * https://github.com/android/platform_frameworks_support/tree/master/v7/recyclerview

7. **dagger 2** dependency injection framework used to provide module system.
  * http://google.github.io/dagger/

8. **retrofit** squareup (https://squareup.com/) network library used to wrap Dirble API.
  * http://square.github.io/retrofit/

9. **okhttp-urlconnection** squareup (https://squareup.com/) network library provides better control for http connection objects. With it app controls time out properties of network connections.
  * https://github.com/square/okhttp/tree/master/okhttp-urlconnection

10. **picasso** squareup (https://squareup.com/) image network library. Provides convenient way to upload and preview of images from network.
  * https://github.com/square/picasso

11. **timber** log wrapper library from JakeWharton (http://jakewharton.com/) provides convenient way to control logging for debug and release build types. For instance app doesn`t provides log when app build as release one.
  * https://github.com/JakeWharton/timber

12. **logger** another log wrapper library. Provides pretty logs when called during runtime.
  * https://github.com/orhanobut/logger

13. **android-observablescrollview** Android library to observe scroll events on scrollable views. It's easy to interact with the Toolbar introduced in Android 5.0 Lollipop and may be helpful to implement look and feel of Material Design apps.
  * https://github.com/ksoichiro/Android-ObservableScrollView

14. **floatingactionbutton** port of floating button from material design.
  * https://github.com/makovkastar/FloatingActionButton

15. **nineoldandroids** Android library for using the Honeycomb (Android 3.0) animation API on all versions of the platform back to 1.0!
  * https://github.com/JakeWharton/NineOldAndroids/

16. **snackbar** port of snack bar view from material design.
  * https://github.com/Kennyc1012/SnackBar

17. **recyclerview-flexibledivider**  library providing simple way to control divider items of RecyclerView
  * https://github.com/yqritc/RecyclerView-FlexibleDivider

18. **textdrawable** provides custom drawable with letter/text like in Gmail app
  * https://github.com/amulyakhare/TextDrawable

19. **androidannotations** helps to reduce android boilerplate code through java annotations.
  * http://androidannotations.org/

20. **dexmaker + mockito** provides a convenient way to mock out object for test environment.
  * http://mockito.org/
  * https://code.google.com/p/dexmaker/

21. **mockwebserver** helps to run server as localhost inside emulator environment.
  * https://github.com/square/okhttp/tree/master/mockwebserver

22. **espresso** used for functional testing. Though app doesn`t have one it. Still it consumes hamcrest dependency as transitive library for tests which comes pre packaged together with espresso.
  * https://code.google.com/p/android-test-kit/wiki/Espresso

23. **commons io** copied source files for loading purpose of resource files during test environment setup.
  * http://commons.apache.org/proper/commons-io/ 

During development of app I used following articles and repositories:

1. https://github.com/cmc00022/easyrecycleradapters copied adapter into the app.

2. https://gist.github.com/skyfishjy/443b7448f59be978bc59 copied source to use in conjunction with cursor object.

3. https://github.com/ismoli/DynamicRecyclerView I`ve borrowed idea for single choice item port for recyclerview.

4. https://github.com/ksoichiro/Android-ObservableScrollView/tree/master/samples copied sample source for paralax effect on station details page.

5. https://github.com/google/iosched I`ve borrowed idea for sync algorithm used to compare cached data(which resides in database) with cloud one.

6. http://jakewharton.com/coercing-picasso-to-play-with-palette/ used code to properly combine picasso with pallete library.

7. https://developer.android.com/training/managing-audio/index.html used tips from training while developing playback service for streaming url.

8. http://materialdesignicons.com/ copied some icons.

9. http://romannurik.github.io/AndroidAssetStudio/ used for project launcher icon.

10. http://www.materialpalette.com/  used colors from this resource as the base for material design.

11. http://www.google.com/design/spec/material-design/introduction.html was one of the guides I`ve used while developing design.

12. http://stackoverflow.com/questions/27661305/material-design-suggestions-for-lists-with-avatar-with-text-and-icon was referencing to this answer while developing design for list items.

13. While exploring main concepts behind dependency injection framework I was refering to this list of resources 
  * https://github.com/RaphaelBrugier/dagger2-sample/
  * https://github.com/gk5885/dagger-android-sample
  * https://github.com/mgrzechocinski/dagger2-example
  * https://github.com/cgruber/u2020/tree/dagger2
  * https://github.com/google/dagger/tree/master/examples
  * http://engineering.circle.com/instrumentation-testing-with-dagger-mockito-and-espresso/
  * http://ics.upjs.sk/~novotnyr/blog/1160/one-does-not-simply-test-the-intentservices

14. https://github.com/udacity/Sunshine borrowed solution for separating UI implementation for both tablet and handset. Took idea of integration tests. Used as reference while implementing sync task.

15. http://rkistner.github.io/android/2013/02/05/android-builds-on-travis-ci/ referred to this article when was setting up Travi CI environment. 

16. Default violet art which is used as thumbnail in app was copied from this project   
  * http://forum.xda-developers.com/android/apps-games/material-dashboard-template-t3056580 

