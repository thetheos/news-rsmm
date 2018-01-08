# news-rsmm
Android application for Résidence Marcel Marlier

Application that auto update itself. Need a rooted device to work properly.

It need a config.json file in wich contains the versionCode of the application, the url's of the PDF to display, the url of the updated apk, ...

This .json is located on a distant server.

The update processus work with the versionCode of the application defined in the build.gradle file. The versionCode also needs to be written in the config.json file. If the versionCode parsed in the json is greater than the versionCode of the application the apk file will be downloaded and installed.

There are Log.d debbuging messages. with different tag per class.
