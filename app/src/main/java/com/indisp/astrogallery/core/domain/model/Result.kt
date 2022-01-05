package com.indisp.astrogallery.core.domain.model

sealed class Result<out S, out F> {
    data class Success<S>(val data: S): Result<S, Nothing>()
    data class Failed<F>(val error: F): Result<Nothing, F>()
}
