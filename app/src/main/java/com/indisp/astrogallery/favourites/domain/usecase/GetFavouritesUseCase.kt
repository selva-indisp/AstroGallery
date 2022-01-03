package com.indisp.astrogallery.favourites.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.indisp.astrogallery.favourites.domain.model.Apod
import kotlinx.coroutines.delay
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class GetFavouritesUseCase() {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(): List<Apod> {
        delay(1000)
        val incomingDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
        return listOf(
            Apod("The Comet and the Galaxy",
                "The Moon almost ruined this photograph.  During late March and early April 1997, Comet Hale-Bopp passed nearly in front of the Andromeda Galaxy. Here the Great Comet of 1997 and the Great Galaxy in Andromeda were  photographed together on 1997 March 24th.  The problem was the brightness of the Moon. The Moon was full that night and so bright that long exposures meant to capture the tails of Hale-Bopp and the disk of M31 would capture instead only moonlight reflected off the Earth's atmosphere.  By the time the Moon would set, this opportunity would be gone.  That's why this picture was taken during a total lunar eclipse.",
                "J. C. Casado", URL("https://apod.nasa.gov/apod/image/0608/hbm31_jcc_big.jpg"), incomingDateFormat.parse("2006-08-13")),
            Apod("MACS 1206: A Galaxy Cluster Gravitational Lens",
                "It is difficult to hide a galaxy behind a cluster of galaxies. The closer cluster's gravity will act like a huge lens, pulling images of the distant galaxy around the sides and greatly distorting them. This is just the case observed in the above recently released image from the CLASH survey with the Hubble Space Telescope. The cluster MACS J1206.2-0847 is composed of many galaxies and is lensing the image of a yellow-red background galaxy into the huge arc on the right. Careful inspection of the image will reveal at least several other lensed background galaxies -- many appearing as elongated wisps. The foreground cluster can only create such smooth arcs if most of its mass is smoothly distributed dark matter -- and therefore not concentrated in the cluster galaxies visible. Analyzing the positions of these gravitational arcs also gives astronomers a method to estimate the dark matter distribution in galaxy clusters, and infer from that when these huge conglomerations of galaxies began to form.   APOD Retrospective: The best of Clusters of Galaxies",
                "Mohan", URL("https://apod.nasa.gov/apod/image/1110/macs1206_hst_973.jpg"),incomingDateFormat.parse("2011-10-17")),
            Apod("Cape York Annular Eclipse",
                "This week the shadow of the New Moon fell on planet Earth, crossing Queensland's Cape York in northern Australia ... for the second time in six months. On the morning of May 10, the Moon's apparent size was too small to completely cover the Sun though, revealing a \\\"ring of fire\\\" along the central path of the annular solar eclipse. Near mid-eclipse from Coen, Australia, a webcast team captured this telescopic snapshot of the annular phase. Taken with a hydrogen-alpha filter, the dramatic image finds the Moon's silhouette just within the solar disk, and the limb of the active Sun spiked with solar prominences. Still, after hosting back-to-back solar eclipses, northern Australia will miss the next and final solar eclipse of 2013. This November, a rare hybrid eclipse will track across the North Atlantic and equatorial Africa.",
                "MWV Observatory", URL("https://apod.nasa.gov/apod/image/1305/ASE_McCarty3-3.jpg"),incomingDateFormat.parse("2006-08-13")),
            Apod("The Elephant's Trunk in IC 1396",
                "Like an illustration in a galactic Just So Story, the Elephant's Trunk Nebula winds through the emission nebula and young star cluster complex IC 1396, in the high and far off constellation of Cepheus. Of course, this cosmic elephant's trunk is over 20 light-years long. The false-color view was recorded through narrow band filters that transmit the light from hydrogen (in green), sulfur (in red), and oxygen (in blue) atoms in the region. The resulting composite highlights the bright swept-back ridges that outline pockets of cool interstellar dust and gas. Such embedded, dark, tendril-shaped clouds contain the raw material for star formation and hide protostars within the obscuring cosmic dust. Nearly 3,000 light-years distant, the relatively faint IC 1396 complex covers a large region on the sky, spanning about 5 degrees. This dramatic close-up covers a 1/2 degree wide field, about the size of the Full Moon.",
                "Brian Lula", URL("https://apod.nasa.gov/apod/image/0710/vdB142_lula.jpg"),incomingDateFormat.parse("2006-08-13")),
            Apod("Dragon Aurora over Iceland",
                "Have you ever seen a dragon in the sky? Although real flying dragons don't exist, a huge dragon-shaped aurora developed in the sky over Iceland earlier this month.  The aurora was caused by a hole in the Sun's corona that expelled charged particles into a solar wind that followed a changing interplanetary magnetic field to Earth's magnetosphere.  As some of those particles then struck Earth's atmosphere, they excited atoms which subsequently emitted light: aurora. This iconic display was so enthralling that the photographer's mother ran out to see it and was captured in the foreground.  No sunspots have appeared on the Sun so far in February, making the multiple days of picturesque auroral activity this month somewhat surprising.",
                "Jingyi Zhang", URL("https://apod.nasa.gov/apod/image/1902/DragonAurora_Zhang_2241.jpg"),incomingDateFormat.parse("2006-08-13")),
            Apod("Nearby Spiral Galaxy NGC 4945",
                "Large spiral galaxy NGC 4945 is seen edge-on near the center of this cosmic galaxy portrait. In fact, NGC 4945 is almost the size of our own Milky Way Galaxy. Its own dusty disk, young blue star clusters, and pink star forming regions standout in the sharp, colorful telescopic image. About 13 million light-years distant toward the expansive southern constellation Centaurus, NGC 4945 is only about six times farther away than Andromeda, the nearest large spiral galaxy to the Milky Way. Though the galaxy's central region is largely hidden from view for optical telescopes, X-ray and infrared observations indicate significant high energy emission and star formation in the core of NGC 4945. Its obscured but active nucleus qualifies the gorgeous island universe as a Seyfert galaxy and likely home to a central supermassive black hole.",
                "SSRO", URL("https://apod.nasa.gov/apod/image/1301/NGC4945_Master23.jpg"),incomingDateFormat.parse("2006-08-13")),
        )
    }
}
