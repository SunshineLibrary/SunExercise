#!/bin/bash
adb shell am kill org.sunshinelibrary.exercise
adb shell pm clear org.sunshinelibrary.exercise
adb shell am kill com.ssl.support.daemon
adb shell pm clear com.ssl.support.daemon
