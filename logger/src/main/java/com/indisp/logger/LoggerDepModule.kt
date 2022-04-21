package com.indisp.logger

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class LoggerDepModule {
    val consoleLogger = ConsoleLogger()
    val firebaseLogger = FirebaseLogger(Firebase.crashlytics, consoleLogger)
}