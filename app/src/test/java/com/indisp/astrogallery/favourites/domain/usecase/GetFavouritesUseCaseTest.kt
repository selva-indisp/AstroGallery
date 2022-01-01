package com.indisp.astrogallery.favourites.domain.usecase

import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


class GetFavouritesUseCaseTest {

    private lateinit var sut: GetFavouritesUseCase

    @Before
    fun setUp() {
        sut = GetFavouritesUseCase()
    }

    @Test
    fun `test1`() = runBlockingTest{
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        println(dateFormat.format(Date()))
    }
}