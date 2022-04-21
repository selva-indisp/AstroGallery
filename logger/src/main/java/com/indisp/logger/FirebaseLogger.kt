package com.indisp.logger

import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseLogger(
    private val crashlytics: FirebaseCrashlytics,
    private val consoleLogger: ConsoleLogger
) : Logger {

    override fun debug(tag: String, message: String) {
        consoleLogger.debug(tag, message)
    }

    override fun warn(tag: String, throwable: Throwable) {
        consoleLogger.warn(tag, throwable)
        crashlytics.recordException(throwable)
    }

    override fun error(tag: String, throwable: Throwable) {
        consoleLogger.error(tag, throwable)
    }
}