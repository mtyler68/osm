package org.mattie.osm.demo;

import java.time.Duration;
import static org.mattie.osm.demo.Constants.resourceBurnItUp;
import static org.mattie.osm.demo.Constants.resourceHeyGirl;
import static org.mattie.osm.demo.Constants.resourceSurfacePressure;
import static org.mattie.osm.demo.Constants.resourceTrustInMe;
import static org.mattie.osm.demo.Cues.mediaCue;
import static org.mattie.osm.demo.Cues.newSubIndex;
import static org.mattie.osm.demo.Cues.nextIndex;
import static org.mattie.osm.demo.Cues.noteCue;
import static org.mattie.osm.demo.Cues.popSubIndex;
import static org.mattie.osm.demo.Cues.richTextCue;
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaResource;
import org.mattie.osm.model.NoteCue;
import org.mattie.osm.model.RichTextCue;
import org.mattie.osm.model.SequentialCue;
import org.mattie.osm.model.Show;
import org.mattie.osm.model.TriggerType;

/**
 *
 * @author Matt
 */
public class SpringRecital22 {

    public static Show springRecital2022() {
        Show show = new Show().setName("2022 Spring Recital");

        addOpenHouseCues(show);
        nextIndex();
        addMoanaAct1Cues(show);

        return show;
    }

    public static Show trevor2022() {

        Show show = new Show().setName("Spring Recital 2022 - Trevor");

        // Mini-Hop
        MediaCue miniVid = mediaCue("Mini-Hop (video): Surface Pressure", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\mini-hop.mp4")
                .setName("Mini-Hop Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(miniVid);

        nextIndex();
        MediaCue miniMusic = mediaCue("Mini-Hop (music): Surface Pressure",
                resourceSurfacePressure()
                        .setStopAt(Duration.parse("PT1M42.7S")));
        show.add(miniMusic);

        // Hip-Hop 1
        nextIndex();
        MediaCue hop1Vid = mediaCue("Hip-Hop 1 (video): Burn It Up", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\hip-hop 1.mp4")
                .setName("Hip-Hop 1 Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(hop1Vid);

        nextIndex();
        MediaCue burnItUpCue = mediaCue("Hip-Hop 1 (music): Burn It Up",
                resourceBurnItUp()
                        .setStopAt(Duration.parse("PT3M28S")));
        show.add(burnItUpCue);

        // Hip-Hop 2/3
        nextIndex();
        MediaCue hop23Vid = mediaCue("Hip-Hop 2/3 (video): What U Workin' With", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\hip-hop 23.mp4")
                .setName("Hip-Hop 23 Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(hop23Vid);

        nextIndex();
        MediaCue wtf = (MediaCue) mediaCue("Hip-Hop 2/3 (music): What U Workin' With", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Trevor\\Hip-Hop 23 - What U Workin' With_.mp3")
                .setStopAt(Duration.parse("PT1M44.9S"))
                .setName("What U Workin' With"));
        show.add(wtf);

        // Jazz 2/3
        nextIndex();
        MediaCue jazz23Vid = mediaCue("Jazz 2/3 (video): Hey Girl", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\jazz 23.mp4")
                .setName("Jazz 23 Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(jazz23Vid);

        nextIndex();
        MediaCue heyGirlCue = mediaCue("Jazz 2/3 (music): Hey Girl",
                resourceHeyGirl()
                        .setStopAt(Duration.parse("PT3M2.7S")));
        show.add(heyGirlCue);

        // Contemporary
        nextIndex();
        MediaCue contempVid = mediaCue("Contemporary (video): Trust In Me", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\Contemporary.mp4")
                .setName("Contemporary Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(contempVid);

        nextIndex();
        MediaCue trustInMeCue = mediaCue("Contemporary (music): Trust In Me",
                resourceTrustInMe()
                        .setStopAt(Duration.parse("PT2M18S"))
                        .setFadeOut(Duration.ofSeconds(5)));
        show.add(trustInMeCue);

        return show;
    }

    public static Show danceFunBoogieBabies() {
        Show show = new Show().setName("Spring Recital 2022 - DanceFUN and Boogie Babies");

        MediaCue dfMoVid = mediaCue("DF Mo (video): The Tiki, Tiki, Tiki Room", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\DF Mo.mp4")
                .setName("DF Mon Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(dfMoVid);

        nextIndex();
        MediaCue dfMoMusic = (MediaCue) mediaCue("DF Mo (music): The Tiki, Tiki, Tiki Room", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\DF Mo - The Tiki, Tiki, Tiki Room.mp3")
                .setName("The Tiki, Tiki, Tiki Room")
                .setStopAt(Duration.parse("PT1M41S"))
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(dfMoMusic);

        nextIndex();
        MediaCue dfTuVid = mediaCue("DF Tu (video): I Like To Move It", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\DF Tu.mp4")
                .setName("DF Tue Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(dfTuVid);

        nextIndex();
        MediaCue dfTuMusic = (MediaCue) mediaCue("DF Tu (music): I Like To Move It", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\DF Tu - I Like To Move It.mp3")
                .setName("I Like To Move It")
                .setStopAt(Duration.parse("PT1M22S"))
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(dfTuMusic);

        nextIndex();
        MediaCue dfFrVid = mediaCue("DF Fr (video): Ballet Dancing Bears", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\DF Fr.mp4")
                .setName("DF Fri Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(dfFrVid);

        nextIndex();
        MediaCue dfFrMusic = (MediaCue) mediaCue("DF Fr (music): Ballet Dancing Bears", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\DF Fr - Ballet Dancing Bears.mp3")
                .setName("Ballet Dancing Bears"));
        show.add(dfFrMusic);

        nextIndex();
        MediaCue bbMoVid = mediaCue("BB Mo (video): Take You To Rio", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\BB Mo.mp4")
                .setName("BB Mon Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(bbMoVid);

        nextIndex();
        MediaCue bbMoMusic = (MediaCue) mediaCue("BB Mo (music): Take You To Rio", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\BB Mo - Take You To Rio.mp3")
                .setName("Take You To Rio").setStopAt(Duration.parse("PT1M59S"))
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(bbMoMusic);

        nextIndex();
        MediaCue bbTuVid = mediaCue("BB Tu (video): Monkey See, Monkey Do", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\BB Tu.mp4")
                .setName("BB Tue Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(bbTuVid);

        nextIndex();
        MediaCue bbTuMusic = (MediaCue) mediaCue("BB Tu (music): Monkey See, Monkey Do", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\BB Tu - Monkey See, Monkey Do.mp3")
                .setName("Monkey See, Monkey Do").setStopAt(Duration.parse("PT1M35S"))
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(bbTuMusic);

        // BB Fr
        nextIndex();
        MediaCue bbFrVid = mediaCue("BB Fr (video): You'll Be In My Heart", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\BB Fr.mp4")
                .setName("BB Fri Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(bbFrVid);

        nextIndex();
        MediaCue bbFrMusic = (MediaCue) mediaCue("BB Fr (music): You'll Be In My Heart", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\BB Fr - Youll Be In My Heart.mp3")
                .setName("You'll Be In My Heart"));
        show.add(bbFrMusic);

        // Private Ballet
        nextIndex();
        MediaCue pbgVid = mediaCue("PBG (video): Bear Necessities", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\PBG Fr.mp4")
                .setName("PBG Fri Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(pbgVid);

        nextIndex();
        MediaCue pbgMusic = (MediaCue) mediaCue("PBG (music): Bear Necessities", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\Private Ballet Fr - Bear Necessities.mp3")
                .setName("Bear Necessities"));
        show.add(pbgMusic);

        // BB Fr PM
        nextIndex();
        MediaCue bbFrPmVid = mediaCue("BB Fr PM (video): Love Will Find a Way", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\BB Fr PM.mp4")
                .setName("BB Fri PM Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(bbFrPmVid);

        nextIndex();
        MediaCue bbFrPmMusic = (MediaCue) mediaCue("BB Fr PM (music): Love Will Find a Way", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\BB Fr PM - Love Will Find a Way.mp3")
                .setName("Love Will Find a Way")
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(bbFrPmMusic);

        return show;
    }

    public static Show ttKcPc() {
        Show show = new Show().setName("Spring Recital 2022 - TT, KC, and PC");

        // TT Mo
        MediaCue ttMoVid = mediaCue("TT Mo (video): Trashin' The Camp", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\TT Mo.mp4")
                .setName("TT Mon Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(ttMoVid);

        nextIndex();
        MediaCue ttMoMusic = (MediaCue) mediaCue("TT Mo (music): Trashin' The Camp", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\TT Mo - Trashin' the Camp.mp3"));
        show.add(ttMoMusic);

        // TT Mo
        nextIndex();
        MediaCue ttFrVid = mediaCue("TT Fr (video): Can You Feel the Love Tonight", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\TT Fr.mp4")
                .setName("TT Fri Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(ttFrVid);

        nextIndex();
        MediaCue ttFrMusic = (MediaCue) mediaCue("TT Fr (music): Can You Feel the Love Tonight", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\TT Fr - Can You Feel the Love Tonight.mp3")
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(ttFrMusic);

        return show;
    }

    public static Show dfBbTtKcPc() {
        Show show = new Show().setName("Spring Recital 2022 - DF BB TT KC PC");

        nextIndex();
        MediaCue ttWe = (MediaCue) mediaCue("TT We: Hakuna Matata", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Leasa\\Twinkle Toes We - Hakuna Matata.mp3")
                .setName("Hakuna Matata"));
        show.add(ttWe);

        nextIndex();
        MediaCue kcWe = (MediaCue) mediaCue("KC We: Jungle Book", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Leasa\\Kinder Combo We - Jungle Book.mp3")
                .setStartAt(Duration.parse("PT54.6s"))
                .setName("Jungle Book"));
        show.add(kcWe);

        nextIndex();
        MediaCue pcWe = (MediaCue) mediaCue("PC We: Monkeys Uncle", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Leasa\\Primary Combo We - Monkeys Uncle.mp3")
                .setName("Monkeys Uncle"));
        show.add(pcWe);

        nextIndex();
        MediaCue ttFr = (MediaCue) mediaCue("TT Fr: Can You Feel the Love Tonight", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\TT Fr - Can You Feel the Love Tonight.mp3")
                .setFadeOut(Duration.ofSeconds(5))
                .setName("Can You Feel the Love Tonight"));
        show.add(ttFr);

        nextIndex();
        MediaCue pbFr = (MediaCue) mediaCue("Private Ballet Fr: Bear Necessities", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\Private Ballet Fr - Bear Necessities.mp3")
                .setName("Bear Necessities"));
        show.add(pbFr);

        nextIndex();
        MediaCue bbFrPm = (MediaCue) mediaCue("BB Fr PM: Love Will Find a Way", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Hannah\\BB Fr PM - Love Will Find a Way.mp3")
                .setFadeOut(Duration.ofSeconds(5))
                .setName("Love Will Find a Way"));
        show.add(bbFrPm);

        return show;
    }

    private static void addOpenHouseCues(Show show) {
//        ParallelCue openHouseCue = parallelCue("OPEN HOUSE");
//
//        newSubIndex();
//        Cue cue = mediaPlaylistCue("OPEN HOUSE MUSIC", Duration.ZERO,
//                Constants.resourceBrazilSamba(),
//                Constants.resourceRunThroughTheJungle(),
//                Constants.resourceStrandedInTheJungle(),
//                Constants.resourceWelcomeToTheJungle());
//        openHouseCue.add(cue);
//
//        nextIndex();
        Cue cue = noteCue("OPEN HOUSE BILLBOARDS", "Create billboard cue")
                .setDelay(Duration.ofSeconds(10));
        show.add(cue);
//        openHouseCue.add(cue);
//        popSubIndex();
    }

    private static void addMoanaAct1Cues(Show show) {

        // Act Notes
        SequentialCue actNotesSeq = Cues.sequentialCue("Act Notes");
        newSubIndex();
        NoteCue setupNote = noteCue("Stage: Sets", "Setup stage for Moana Act 1")
                .setClearNotes(true);
        nextIndex();
        NoteCue micsNote = noteCue("Audio: Wireless Mics", "Pot up wireless mics for Moana Act 1");
        popSubIndex();
        actNotesSeq.add(setupNote, micsNote);
        show.add(actNotesSeq);
    }

    public static Show moanaScene1() {
        Show show = new Show()
                .setName("Spring Recital 2022 - Moana Sc. 1");

        // Pre scene notes
        NoteCue visualCue1 = noteCue("Visual Cue", "Fade '01 Cavern' when Moana rows across stage")
                .setClearNotes(true);
        show.add(visualCue1);

        nextIndex();
        NoteCue beforeCurtainCue = (NoteCue) noteCue("Before Curtain", "Cue '01 Cavern' music before curtain opens")
                .setTrigger(TriggerType.AUTO_START);
        show.add(beforeCurtainCue);

        // Script
        nextIndex();
        RichTextCue script = (RichTextCue) richTextCue("Moana Scene 1 Script",
                Constants.MOANA_SC1_PG1,
                Constants.MOANA_SC1_PG2,
                Constants.MOANA_SC1_PG3)
                .setClearPages(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(script);

        nextIndex();
        MediaCue cavernCue = (MediaCue) mediaCue("01 Cavern", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\01 Cavern.mp3")
                .setName("Cavern"));
        show.add(cavernCue);

        nextIndex();
        MediaCue whereURCue = (MediaCue) mediaCue("02 Where You Are", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\02 Where You Are (Instrumental).mp3")
                .setName("Where You Are (Instrumental)"));
        show.add(whereURCue);

        nextIndex();
        MediaCue howFarCue = (MediaCue) mediaCue("03 How Far I'll Go", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\03 How Far I'll Go (Instrumental).mp3")
                .setName("How Far I'll Go (Instrumental)"));
        show.add(howFarCue);

        nextIndex();
        MediaCue whereURCue2 = (MediaCue) mediaCue("02 Where You Are", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\02 Where You Are (Instrumental).mp3")
                .setName("Where You Are (Instrumental)")
                .setStartAt(Duration.ofSeconds(15))
                .setFadeIn(Duration.ofSeconds(5))
                .setStopAt(Duration.ofSeconds(35))
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(whereURCue2);

        return show;
    }

    public static Show cinderellaSc1() {
        Show show = new Show().setName("Spring Recital 2022 - Cinderella Sc. 1");

        MediaCue track01 = (MediaCue) mediaCue("Cinderella: Track 01", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Cinderella\\01 Track 01.mp3")
                .setName("Track 01"));
        show.add(track01);

        nextIndex();
        MediaCue track02 = (MediaCue) mediaCue("Cinderella: Track 02", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Cinderella\\02 Track 02.mp3")
                .setName("Track 02"))
                .setTrigger(TriggerType.AUTO_START);
        show.add(track02);

        return show;
    }

    public static Show cinderellaSc2() {
        Show show = new Show().setName("Spring Recital 2022 - Cinderella Sc. 2");

        for (int ndx = 3; ndx < 9; ndx++) {
            MediaResource resource = new MediaResource()
                    .setFile(String.format("C:\\Users\\Matt\\Music\\2022 Recital\\Cinderella\\0%1$d Track 0%1$d.mp3", ndx))
                    .setName(String.format("Track 0%d", ndx));
            if (ndx == 3) {
                resource.setStopAt(Duration.parse("PT2M57.5S"));
            }

            MediaCue track = (MediaCue) mediaCue(String.format("Cinderella: Track 0%d", ndx), resource)
                    .setTrigger(TriggerType.AUTO_START);
            show.add(track);

            nextIndex();
        }

        NoteCue noteCue = (NoteCue) noteCue("Curtain Call", "Play Track 09 (next cue) for curtain call")
                .setClearNotes(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(noteCue);

        nextIndex();
        MediaCue track09 = (MediaCue) mediaCue("Cinderella: Track 09", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Cinderella\\09 Track 09.mp3")
                .setName("Track 09"));
        show.add(track09);
        return show;
    }
}
