package com.indisp.logger

interface Logger {
    fun debug(tag: String, message: String)
    fun warn(tag: String, throwable: Throwable)
    fun error(tag: String, throwable: Throwable)
}