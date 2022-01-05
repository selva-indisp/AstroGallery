package com.indisp.astrogallery

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val date = "30-12-2021"
        val formatter1 = SimpleDateFormat("dd-mm-yyyy", Locale.getDefault())
        val dateObject = formatter1.parse(date)
        val nextDay = Calendar.getInstance(Locale.getDefault()).run {
            time = dateObject
            add(Calendar.DATE, 2)
            return@run time
        }
        println("Next - ${formatter1.format(nextDay)}")

        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
        val localDate = LocalDate.parse(date, dateFormatter)
        val nextLocalDay = localDate.plusDays(2)
        val prevLocalDay = nextLocalDay.minusDays(1)
        println("LocalNextDay - ${nextLocalDay.format(dateFormatter)}")
        println("LocalPrevDay - ${prevLocalDay.format(dateFormatter)}")

        val dateFormatterDisplay = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())
        println("Formatted - ${nextLocalDay.format(dateFormatterDisplay)}")
    }
}