Google+ Android Starter Application
=====================================

== Description ==
This application helps you get started writing a Google+ Android application.

== Getting Started ==

1) If you are brand new to Android development, you can get up to speed by reading the following documents:
  First grab the Android SDK. The following page describes how to install the latest Android SDK and
  set up your development environment for the first time.
    - http://developer.android.com/sdk/installing.html

2) Visit https://code.google.com/apis/console/?api=plus to register your application.
- From the "Project Home" screen, activate access to "Google+ API".
- Enable the Google+ API on your project by toggling the Google+ API switch to ON
- Copy the API Key found at API Access > Simple API Access > API Key

3) Edit src/com/example/android/gocial/PlusWrap.java and replace
  INSERT_YOUR_API_KEY_HERE with the API Key you obtained in step 2.

== Setup Project in Eclipse 3.5 ==

Prerequisites:
  Eclipse 3.5 [http://www.eclipse.org/galileo/]
  ADT Plugin [http://developer.android.com/sdk/eclipse-adt.html#installing]

1) Click Preferences > Android:
  Setup the Android SDK location.

2) Click Window > Android SDK and AVD Manager
  Install the package "Google API's by Google Inc., Android API 10"
  Create a new Android Virtual Device based on the target "Google API's (Google Inc.) - API Level 10"
  (Detailed instructions are available here for this step: http://developer.android.com/sdk/installing.html#AddingComponents)

3) Click "Start.." and wait a while. Follow this step only if you're using the emulator.
  On the home screen in the emulator, click Menu button, Settings
  Click on "Accounts and Sync"
  Click on "Add Account"
  Click "Google" and follow instructions

4) Import the Google+ Android project
  Select the project under google-plus-java-starter/android.
    - File > Import...
    - Select "General > Existing Project into Workspace" and click "Next"
    - Click "Browse" next to "Select root directory", find [someDirectory]/google-plus-java-starter/android
    - Click "Finish"
  Clean Project (if theres a compile error about missing gen directory)
    - Select the PlusAndroidSample project
    - Project > Clean...
    - Select "Clean projects selected below"
    - Click on "OK"
  Run
    - Right-click on the project PlusAndroidSample
    - Run As > Android Application

== TIPS ==

If you would like to debug HTTP requests made to the API, simply add the line:
    java.util.logging.Logger.getLogger("com.google.api.client").setLevel(java.util.logging.Level.ALL);

Then from the command prompt you need to run this:
    adb shell setprop log.tag.HttpTransport DEBUG
