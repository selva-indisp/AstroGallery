package com.indisp.astrogallery.core.domain.usecase

import com.indisp.shared.domain.Result
import com.indisp.astrogallery.details.domain.model.GetApodDetailsError
import com.indisp.astrogallery.core.domain.model.Apod
import com.indisp.astrogallery.core.domain.repository.ApodRepository
import kotlinx.coroutines.delay
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GetApodDetailsUseCase(private val apodRepository: ApodRepository) {
    suspend operator fun invoke(date: LocalDate): Result<Apod, GetApodDetailsError> {
        delay(2000)
        return apodRepository.getDetailsFor(date)
        val incomingDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        return  Result.Success(
            Apod("The Comet and the Galaxy",
            "The Moon almost ruined this photograph.  During late March and early April 1997, Comet Hale-Bopp passed nearly in front of the Andromeda Galaxy. Here the Great Comet of 1997 and the Great Galaxy in Andromeda were  photographed together on 1997 March 24th.  The problem was the brightness of the Moon. The Moon was full that night and so bright that long exposures meant to capture the tails of Hale-Bopp and the disk of M31 would capture instead only moonlight reflected off the Earth's atmosphere.  By the time the Moon would set, this opportunity would be gone.  That's why this picture was taken during a total lunar eclipse.",
            "J. C. Casado", URL("https://apod.nasa.gov/apod/image/0608/hbm31_jcc_big.jpg"), LocalDate.parse("2006-08-13", incomingDateFormat))
        )
    }
}