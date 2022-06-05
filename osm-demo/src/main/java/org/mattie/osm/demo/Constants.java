package org.mattie.osm.demo;

import java.time.Duration;
import org.mattie.osm.model.MediaResource;

/**
 *
 * @author Matt
 */
public class Constants {

    public static final String MOANA_SC1_PG1 = "<html>\n"
            + "<head>\n"
            + "	<title>Moana SC1 PG1</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "<h3 style=\"text-align: center;\"><u>Scene 1-Village</u></h3>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">Gramma Tala:</span></strong></u>&nbsp; In the beginning, there was only ocean. Until &ldquo;Mother Island&rdquo; emerged.</p>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">All:</span></strong></u> TeFiti</p>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">Gramma Tala:</span></strong></u> Her heart had the greatest power ever known.&nbsp;&nbsp;And TeFiti shared it with the world.&nbsp;&nbsp;But in time, some began to seek TeFiti&rsquo;s heart.&nbsp;The most daring was Maui- the Demi god of wind and sea.&nbsp; He was a warrior... A trickster. A shape shifter who could change form with the power of his magical fish hook. He took the heart of TeFiti, and without her heart she began to crumble.&nbsp;&nbsp;&nbsp;Maui was confronted by TeKa-&nbsp; the demon of earth and fire.&nbsp;&nbsp;</p>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">Kids:</span></strong></u>&nbsp; Nooooooo! Fire monster! Fire monster noooo!</p>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">Grmma Tala:</span></strong></u>&nbsp; Maui was struck down, never to be seen again. His magical fish hook,&nbsp; and the heart of TeFiti were lost to the sea.&nbsp; One day, her heart will be found by someone who&nbsp; is brave and would journey beyond the reef, across the great ocean to find Maui and restore the heart of TeFiti.</p>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">All:</span></strong></u> And save us all.&nbsp;</p>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">Moana:</span></strong></u> Wow! Great story Gramma! I want to cross the ocean.....</p>\n"
            + "\n"
            + "<p><u><strong><span style=\"background-color:#00FF00;\">Kids:</span></strong></u> Noooot. Monster. Monster there. Nooooooo</p>\n"
            + "</body>\n"
            + "</html>";

    public static final String MOANA_SC1_PG2 = "";

    public static MediaResource resourceCircusHorn() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\mixkit-clown-horn-at-circus-715.wav")
                .setName("Circus Horn")
                .setStopAt(Duration.parse("PT2.9S"));
    }

    public static MediaResource resourceBrazilSamba() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\bensound-brazilsamba.mp3")
                .setName("Brazil Samba")
                .setStopAt(Duration.parse("PT4M1S"));
    }

    public static MediaResource resourceSurfacePressure() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Trevor\\Mini-Hop - Surface Pressure.mp3")
                .setName("Surface Pressure");
    }

    public static MediaResource resourceBurnItUp() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Trevor\\Hip-Hop 1 - BURNITUP! (feat. Missy Elliott).mp3")
                .setName("Burn It Up!");
    }

    public static MediaResource resourceHeyGirl() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Trevor\\Jazz 23 - Hey Girl.mp3")
                .setName("Hey Girl");
    }

    public static MediaResource resourceTrustInMe() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Trevor\\Contemporary - Trust in Me.mp3")
                .setName("Trust in Me");
    }
}
