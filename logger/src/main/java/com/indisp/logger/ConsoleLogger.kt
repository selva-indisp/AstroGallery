package com.indisp.logger

import android.util.Log

class ConsoleLogger : Logger{
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun warn(tag: String, throwable: Throwable) {
        Log.w(tag, throwable)
    }

    override fun error(tag: String, throwable: Throwable) {
        Log.e(tag, throwable.message, throwable)
    }
}