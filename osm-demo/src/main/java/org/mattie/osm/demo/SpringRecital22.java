package org.mattie.osm.demo;

import java.time.Duration;
import static org.mattie.osm.demo.Constants.resourceBurnItUp;
import static org.mattie.osm.demo.Constants.resourceHeyGirl;
import static org.mattie.osm.demo.Constants.resourceSurfacePressure;
import static org.mattie.osm.demo.Constants.resourceTrustInMe;
import static org.mattie.osm.demo.Cues.mediaCue;
import static org.mattie.osm.demo.Cues.nextIndex;
import static org.mattie.osm.demo.Cues.noteCue;
import static org.mattie.osm.demo.Cues.richTextCue;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaResource;
import org.mattie.osm.model.NoteCue;
import org.mattie.osm.model.RichTextCue;
import org.mattie.osm.model.Show;
import org.mattie.osm.model.TriggerType;

/**
 *
 * @author Matt
 */
public class SpringRecital22 {

//    public static Show springRecital2022() {
//        Show show = new Show().setName("2022 Spring Recital");
//
//        addOpenHouseCues(show);
//        nextIndex();
//        addMoanaAct1Cues(show);
//
//        return show;
//    }
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
        MediaCue bbTuVid = mediaCue("BB Tu (video): 5 Positions All in a Row", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\BB Tu.mp4")
                .setName("BB Tue Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(bbTuVid);

        nextIndex();
        MediaCue bbTuMusic = (MediaCue) mediaCue("BB Tu (music): 5 Positions All in a Row", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\BB Tu - 5 Positions All in a Row (Instrumental).mp3")
                .setName("5 Positions All in a Row")
                .setStopAt(Duration.parse("PT1M46S"))
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

        // TT We
        nextIndex();
        MediaCue ttWeVid = mediaCue("TT We (video): Hakuna Matata", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\TT We.mp4")
                .setName("TT Wed Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(ttWeVid);

        nextIndex();
        MediaCue ttWe = (MediaCue) mediaCue("TT We: Hakuna Matata", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Leasa\\Twinkle Toes We - Hakuna Matata.mp3")
                .setName("Hakuna Matata"));
        show.add(ttWe);

        // TT Fr
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

        // KC Mo
        nextIndex();
        MediaCue kcMoVid = mediaCue("KC Mo (video): Upside Down", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\KC Mo.mp4")
                .setName("KC Mon Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(kcMoVid);

        nextIndex();
        MediaCue kcMoMusic = (MediaCue) mediaCue("KC Mo (music): Upside Down", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\KC Mo - Upside Down.mp3")
                .setStopAt(Duration.parse("PT1M40S"))
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(kcMoMusic);

        // KC We
        nextIndex();
        MediaCue kcWeVid = mediaCue("KC We (video): Jungle Book", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\KC We.mp4")
                .setName("KC Wed Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(kcWeVid);

        nextIndex();
        MediaCue kcWe = (MediaCue) mediaCue("KC We: Jungle Book", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Leasa\\Kinder Combo We - Jungle Book.mp3")
                .setStartAt(Duration.parse("PT54.6s"))
                .setName("Jungle Book"));
        show.add(kcWe);

        // PC Mo
        nextIndex();
        MediaCue pcMoVid = mediaCue("PC Mo (video): Funky Monkey", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\PC Mo.mp4")
                .setName("PC Mon Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(pcMoVid);

        nextIndex();
        MediaCue pcMoMusic = (MediaCue) mediaCue("PC Mo (music): Funky Monkey", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Lily\\PC Mo - Funky Monkey.mp3"));
        show.add(pcMoMusic);

        // PC We
        nextIndex();
        MediaCue pcWeVid = mediaCue("PC We (video): Monkey's Uncle", new MediaResource()
                .setFile("C:\\Users\\Matt\\Videos\\2022 Recital\\PC We.mp4")
                .setName("PC Wed Vid")
                .setType(MediaResource.MediaType.AUDIO_VIDEO));
        show.add(pcWeVid);

        nextIndex();
        MediaCue pcWe = (MediaCue) mediaCue("PC We: Monkeys Uncle", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Leasa\\Primary Combo We - Monkeys Uncle.mp3")
                .setName("Monkeys Uncle"));
        show.add(pcWe);

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

    public static MediaCue createPlaylistMedia(String name, String source) {
        return (MediaCue) mediaCue(name, new MediaResource().setName(name).setFile(source))
                .setTrigger(TriggerType.AUTO_START)
                .setDelay(Duration.ofMillis(2000));
    }

    public static Show openHouseCues() {
        Show show = new Show().setName("Spring Reecital 2022 - Open House");

        show.add(createPlaylistMedia("Brazil Samba", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\bensound-brazilsamba.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Adventrue", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\adventure.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Stranded In the Jungle", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\01 Stranded In the Jungle.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Ukulele", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\Ukulele.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("New Dawn", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\newdawn.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Dreams", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\dreams.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("The Lion Sleeps Tonight", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\01 The Lion Sleeps Tonight (Re-Recor.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Badass", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\badass.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Funky Suspense", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\funkysuspense.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Instict", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\instinct.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Bungle In The Jungle", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\06 Bungle In The Jungle.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Run Through The Jungle", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\13 Run Through the Jungle.mp3"));
        nextIndex();
        show.add(createPlaylistMedia("Welcome to the Jungle", "C:\\Users\\Matt\\Music\\2022 Recital\\Open House\\01 Welcome to the Jungle.mp3"));

        return show;
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
                .setName("Where You Are (Instrumental)")
                .setStartAt(Duration.ofSeconds(15)));
        show.add(whereURCue);

        nextIndex();
        MediaCue whereURCue2Cue = (MediaCue) mediaCue("02 Where You Are", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\02 Where You Are (Instrumental).mp3")
                .setName("Where You Are (Instrumental) playoff")
                .setStartAt(Duration.ofSeconds(15))
                .setStopAt(Duration.ofSeconds(35))
                .setFadeOut(Duration.ofSeconds(5)));
        show.add(whereURCue2Cue);

        nextIndex();
        MediaCue howFarCue = (MediaCue) mediaCue("03 How Far I'll Go", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\03 How Far I'll Go (Instrumental).mp3")
                .setName("How Far I'll Go (Instrumental)"));
        show.add(howFarCue);

        return show;
    }

    public static Show moanaScene2() {
        Show show = new Show()
                .setName("Spring Recital 2022 - Moana Sc. 2");

        // Pre scene notes
        NoteCue visualCue1 = noteCue("Visual Cue", "Fade '04 Battle of Wills' out after curtain open")
                .setClearNotes(true);
        show.add(visualCue1);

        nextIndex();
        NoteCue beforeCurtainCue = (NoteCue) noteCue("Before Curtain", "Cue '04 Battle of Wills' music before curtain opens")
                .setTrigger(TriggerType.AUTO_START);
        show.add(beforeCurtainCue);

        // Script
        nextIndex();
        RichTextCue script = (RichTextCue) richTextCue("Moana Scene 2 Script",
                Constants.MOANA_SC2_PG1,
                Constants.MOANA_SC2_PG2,
                Constants.MOANA_SC2_PG3)
                .setClearPages(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(script);

        nextIndex();
        MediaCue battleCue = (MediaCue) mediaCue("04 Battle of Wills", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\04 Battle of Wills.mp3")
                .setName("Battle of Wills"));
        show.add(battleCue);

        nextIndex();
        NoteCue exitNoteCue = (NoteCue) noteCue("Transition out", "Cue '05 You're Welcome (Instrumental)' music for transition out after finish you're welcome")
                .setClearNotes(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(exitNoteCue);

        nextIndex();
        MediaCue welcomeCue = (MediaCue) mediaCue("05 You're Welcome (Instrumental)", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\05 You're Welcome (Instrumental).mp3")
                .setName("You're Welcome")
                .setStartAt(Duration.ofSeconds(9)));
        show.add(welcomeCue);

        nextIndex();
        MediaCue welcome2Cue = (MediaCue) mediaCue("05 You're Welcome (Instrumental) - transition out", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\05 You're Welcome (Instrumental).mp3")
                .setName("You're Welcome")
                .setStartAt(Duration.ofSeconds(9))
                .setFadeIn(Duration.ofSeconds(3))
                .setStopAt(Duration.ofSeconds(24))
                .setFadeOut(Duration.ofSeconds(4)));
        show.add(welcome2Cue);

        return show;
    }

    public static Show moanaScene3() {
        Show show = new Show()
                .setName("Spring Recital 2022 - Moana Sc. 3");

        // Script
        RichTextCue script = (RichTextCue) richTextCue("Moana Scene 3 Script",
                Constants.MOANA_SC3_PG1)
                .setClearPages(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(script);

        nextIndex();
        MediaCue shinyCue = (MediaCue) mediaCue("06 Shiny (Instrumental)", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\06 Shiny (Instrumental).mp3")
                .setName("Shiny")
                .setStartAt(Duration.ofSeconds(4)));
        show.add(shinyCue);

        nextIndex();
        NoteCue exitNoteCue = (NoteCue) noteCue("Transition out", "Cue '06 Shiny (Instrumental)' music for transition out")
                .setClearNotes(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(exitNoteCue);

        nextIndex();
        MediaCue transOutCue = (MediaCue) mediaCue("06 Shiny (Instrumental)", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\06 Shiny (Instrumental).mp3")
                .setName("Shiny")
                .setStartAt(Duration.ofSeconds(17))
                .setFadeIn(Duration.ofSeconds(3)));
        show.add(transOutCue);

        return show;
    }

    public static Show moanaScene4() {
        Show show = new Show()
                .setName("Spring Recital 2022 - Moana Sc. 4");

        // Pre scene notes
        NoteCue curtainCue = noteCue("Curtain Cue", "Cue '07 If I Were the Ocean' when curtain open")
                .setClearNotes(true);
        show.add(curtainCue);

        // Script
        nextIndex();
        RichTextCue script = (RichTextCue) richTextCue("Moana Scene 4 Script",
                Constants.MOANA_SC4_PG1,
                Constants.MOANA_SC4_PG2)
                .setClearPages(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(script);

        nextIndex();
        MediaCue transInCue = (MediaCue) mediaCue("07 If I Were the Ocean", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\07 If I Were the Ocean.mp3")
                .setName("If I Were the Ocean")
                .setStartAt(Duration.parse("PT2M43S"))
                .setFadeIn(Duration.ofSeconds(3)));
        show.add(transInCue);

        nextIndex();
        NoteCue visualCue1 = (NoteCue) noteCue("Visual Cue", "Cue '08 Teka Attacks' visually")
                .setClearNotes(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(visualCue1);

        nextIndex();
        MediaCue tekaAttacksCue = (MediaCue) mediaCue("08 Teka Attacks", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\08 Teka Attacks.mp3")
                .setName("Teka Attacks"));
        show.add(tekaAttacksCue);

        nextIndex();
        NoteCue visualCue2 = (NoteCue) noteCue("Visual Cue", "Cue '09 Tala Returns' visually")
                .setClearNotes(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(visualCue2);

        nextIndex();
        MediaCue talaReturnsCue = (MediaCue) mediaCue("09 Tala Returns", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\09 Tala Returns.mp3")
                .setName("Tala Returns"));
        show.add(talaReturnsCue);

        nextIndex();
        MediaCue iAmMoanaCue = (MediaCue) mediaCue("10 I Am Moana (Instrumental)", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\10 I Am Moana (Instrumental).mp3")
                .setName("I Am Moana"));
        show.add(iAmMoanaCue);

        nextIndex();
        MediaCue knowHowYouAre = (MediaCue) mediaCue("11 Know Who You Are (Instrumental)", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\11 Know Who You Are (Instrumental).mp3")
                .setName("Know Who You Are"));
        show.add(knowHowYouAre);

        nextIndex();
        MediaCue weKnowTheWayCue = (MediaCue) mediaCue("12 We Know The Way", new MediaResource()
                .setFile("C:\\Users\\Matt\\Music\\2022 Recital\\Moana Production\\12 We Know The Way.mp3")
                .setName("We Know The Way")
                .setStartAt(Duration.ofSeconds(11)));
        show.add(weKnowTheWayCue);

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

        for (int ndx = 3; ndx < 6; ndx++) {
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

        NoteCue noteCue = (NoteCue) noteCue("Black out", "Black out lights")
                .setClearNotes(true)
                .setTrigger(TriggerType.AUTO_START);
        show.add(noteCue);

        return show;
    }

    public static Show cinderellaSc3() {
        Show show = new Show().setName("Spring Recital 2022 - Cinderella Sc. 3");

        for (int ndx = 6; ndx < 9; ndx++) {
            MediaResource resource = new MediaResource()
                    .setFile(String.format("C:\\Users\\Matt\\Music\\2022 Recital\\Cinderella\\0%1$d Track 0%1$d.mp3", ndx))
                    .setName(String.format("Track 0%d", ndx));

            MediaCue track = (MediaCue) mediaCue(String.format("Cinderella: Track 0%d", ndx), resource)
                    .setTrigger(TriggerType.AUTO_START);
            show.add(track);

            nextIndex();
        }

        NoteCue noteCue = (NoteCue) noteCue("Curtain Call", "Play Track 09 (next cue) for curtain call - Fade when all dancers line up")
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
