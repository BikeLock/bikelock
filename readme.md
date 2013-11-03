# Android Maven setup

## Setup

Assuming that the android SDK has been downloaded and unzipped in $HOME/bin

Set the ANDROID_HOME pointing to the Android SDK folder. OSX:

    export ANDROID_HOME=/Users/a.fiore/bin/android-sdk-macosx/

Note: Maven seems to fail at expanding ~/ into $HOME.

...or Windows:

    set ANDROID_HOME=C:\<installation location>\android-sdk-windows

## Code generation

Run he mvn command using the `android-with-test` archetype.

    mvn archetype:generate \
      -DarchetypeArtifactId=android-with-test \
      -DarchetypeGroupId=de.akquinet.android.archetypes \
      -DarchetypeVersion=1.0.10 \
      -DgroupId=com.understandinggeek \
      -DartifactId=bikelocker \
      -Dpackage=com.understandinggeek.bikelocker \
      -Dplatform=16

## Running the integration test

First, fire up the Android avd tool and start an emulator.

    android avd

    ANDROID_HOME=/Users/a.fiore/bin/android-sdk-macosx mvn -Dandroid.enableIntegrationTest=true -Dandroid.device=emulator clean integration-test

Deploy app on device

    ANDROID_HOME=/Users/a.fiore/bin/android-sdk-macosx  mvn -Dandroid.enableIntegrationTest=true -Dandroid.device=emulator clean install android:deploy android:run
