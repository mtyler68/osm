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
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaResource;
import org.mattie.osm.model.NoteCue;
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

        MediaCue surfacePressureCue = mediaCue("Mini-Hop: Surface Pressure",
                resourceSurfacePressure()
                        .setStopAt(Duration.parse("PT1M42.7S")));
        show.add(surfacePressureCue);

        nextIndex();
        MediaCue burnItUpCue = mediaCue("Hip-Hop 1: Burn It Up",
                resourceBurnItUp()
                        .setStopAt(Duration.parse("PT3M28S")));
        show.add(burnItUpCue);

        nextIndex();
        MediaCue wtf = (MediaCue) mediaCue("Hip-Hop 2/3: What U Workin' With", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Trevor\\Hip-Hop 23 - What U Workin' With_.mp3")
                .setStopAt(Duration.parse("PT2M18S"))
                .setName("What U Workin' With"));
        show.add(wtf);

        nextIndex();
        MediaCue heyGirlCue = mediaCue("Jazz 2/3: Hey Girl",
                resourceHeyGirl()
                        .setStopAt(Duration.parse("PT3M2.7S")));
        show.add(heyGirlCue);

        nextIndex();
        MediaCue trustInMeCue = mediaCue("Contemporary: Trust In Me",
                resourceTrustInMe()
                        .setStopAt(Duration.parse("PT2M18S"))
                        .setFadeOut(Duration.ofSeconds(5)));
        show.add(trustInMeCue);

        return show;
    }

    public static Show dfBbTtKcPc() {
        Show show = new Show().setName("Spring Recital 2022 - DF BB TT KC PC");

        MediaCue dfMoVid = mediaCue("DF Mo (vid): The Tiki, Tiki, Tiki Room", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\DF Mo.mp4")
                .setName("DF Mon Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(dfMoVid);

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

        NoteCue beforeCurtainCue = noteCue("Before Curtain", "Cue music before curtain opens")
                .setClearNotes(true);
        show.add(beforeCurtainCue);

        nextIndex();
        NoteCue visualCue1 = noteCue("Visual Cue", "Fade 01 Cavern when Moana rows across stage")
                .setClearNotes(true);
        show.add(visualCue1);

        nextIndex();
        MediaCue cavernCue = (MediaCue) mediaCue("01 Cavern", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\01 Cavern.mp3")
                .setName("Cavern"))
                .setTrigger(TriggerType.AUTO_START);
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
            MediaCue track = (MediaCue) mediaCue(String.format("Cinderella: Track 0%d", ndx), new MediaResource()
                    .setFile(String.format("C:\\Users\\Matt\\Music\\2022 Recital\\Cinderella\\0%1$d Track 0%1$d.mp3", ndx))
                    .setName(String.format("Track 0%d", ndx)))
                    .setTrigger(TriggerType.AUTO_START);
            show.add(track);

            nextIndex();
        }

        MediaCue track09 = (MediaCue) mediaCue("Cinderella: Track 09", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Cinderella\\09 Track 09.mp3")
                .setName("Track 09"));
        show.add(track09);
        return show;
    }
}
