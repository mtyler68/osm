package org.mattie.osm.demo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import static org.mattie.osm.demo.Cues.mediaCue;
import static org.mattie.osm.demo.Cues.newSubIndex;
import static org.mattie.osm.demo.Cues.nextIndex;
import static org.mattie.osm.demo.Cues.noteCue;
import static org.mattie.osm.demo.Cues.parallelCue;
import static org.mattie.osm.demo.Cues.popSubIndex;
import static org.mattie.osm.demo.Cues.sequentialCue;
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.DmxCue;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaResource;
import org.mattie.osm.model.NoteCue;
import org.mattie.osm.model.ParallelCue;
import org.mattie.osm.model.SequentialCue;
import org.mattie.osm.model.Show;
import org.mattie.osm.model.TriggerType;
import org.mattie.osm.model.Utils;
import org.mattie.osm.model.dmx.DmxSetup;
import org.mattie.osm.model.dmx.DmxUtils;

/**
 *
 * @author Matt
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        generateDemo(fullDemo(), "full_demo");
//        generateDemo(oneMediaCueWithDelay(), "one_media_cue_with_delay");
//        generateDemo(oneMediaCueInSequenceWithDelay(), "one_media_cue_in_sequence_with_delay");
//        generateDemo(SpringRecital22.springRecital2022(), "2022_spring_recital");

        generateDemo(includeDmxHotKeys(SpringRecital22.openHouseCues()), "2022_spring_recital_01_open_house");
        generateDemo(includeDmxHotKeys(SpringRecital22.entireShow()), "2022_spring_recital_02_entire_show");

//        generateDemo(includeDmxHotKeys(SpringRecital22.danceFunBoogieBabies()), "2022_spring_recital_02_dancefun_boogiebabies");
//        generateDemo(includeDmxHotKeys(SpringRecital22.ttKcPc()), "2022_spring_recital_03_tt_kc_pc");
//
//        generateDemo(includeDmxHotKeys(SpringRecital22.trevor2022()), "2022_spring_recital_04_trevor");
        generateDemo(includeDmxHotKeys(SpringRecital22.moanaScene1()), "2022_spring_recital_06_moana_sc1");
        generateDemo(includeDmxHotKeys(SpringRecital22.moanaScene2()), "2022_spring_recital_06_moana_sc2");
        generateDemo(includeDmxHotKeys(SpringRecital22.moanaScene3()), "2022_spring_recital_06_moana_sc3");
        generateDemo(includeDmxHotKeys(SpringRecital22.moanaScene4()), "2022_spring_recital_06_moana_sc4");

//        generateDemo(includeDmxHotKeys(SpringRecital22.cinderellaSc1()), "2022_spring_recital_05_cinderella_sc1");
//        generateDemo(includeDmxHotKeys(SpringRecital22.cinderellaSc2()), "2022_spring_recital_05_cinderella_sc2");
//        generateDemo(includeDmxHotKeys(SpringRecital22.cinderellaSc3()), "2022_spring_recital_05_cinderella_sc3");
    }

    private static void generateDmxConfig(DmxSetup dmxSetup, String title) throws IOException {
        System.out.println(title + ": " + dmxSetup);

        File dir = new File("C:\\projects\\shows\\22recital\\dmx");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, title + ".yaml");
        DmxUtils.getObjectMapper().writeValue(file, dmxSetup);

        DmxSetup readValue = DmxUtils.getObjectMapper().readValue(file, DmxSetup.class);
        System.out.println(title + " (r): " + readValue);
    }

    private static Show includeDmxHotKeys(Show show) throws IOException {
        DmxCue hk1 = Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\1_all_on.yaml"), DmxCue.class);
        hk1.setDesc("All On");
        show.add(hk1);

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\Q_all_off.yaml"), DmxCue.class)
                .setDesc("All Off"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\2_all_75_pct.yaml"), DmxCue.class)
                .setDesc("All 75%"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\3_all_50_pct.yaml"), DmxCue.class)
                .setDesc("All 50%"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\4_all_wht_down_on.yaml"), DmxCue.class)
                .setDesc("All White Down On"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\R_all_wht_down_off.yaml"), DmxCue.class)
                .setDesc("All White Down Off"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\5_all_wht_rear_on.yaml"), DmxCue.class)
                .setDesc("All White Rear On"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\T_all_wht_rear_off.yaml"), DmxCue.class)
                .setDesc("All White Rear Off"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\6_all_blue_on.yaml"), DmxCue.class)
                .setDesc("All Blue On"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\Y_all_blue_off.yaml"), DmxCue.class)
                .setDesc("All Blue Off"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\7_all_red_on.yaml"), DmxCue.class)
                .setDesc("All Red On"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\U_all_read_off.yaml"), DmxCue.class)
                .setDesc("All Red Off"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\8_all_green_on.yaml"), DmxCue.class)
                .setDesc("All Green On"));

        show.add(Utils.getObjectMapper().readValue(
                new File("C:\\projects\\shows\\22recital\\dmxkeyframes\\I_all_green_off.yaml"), DmxCue.class)
                .setDesc("All Green Off"));

        return show;
    }

    private static void generateDemo(Show show, String title) throws IOException {
        Cues.reset();
        System.out.println(title + ": " + show);
        save(title + ".yaml", show);
        Show demor = open(title + ".yaml");
        System.out.println(title + "r: " + demor);
    }

    private static void save(String filename, Show show) throws IOException {
        File dir = new File("C:\\projects\\shows\\22recital");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename);
        Utils.getObjectMapper().writeValue(file, show);
    }

    private static Show open(String filename) throws IOException {
        File dir = new File("C:\\projects\\shows\\22recital");
        File file = new File(dir, filename);
        return Utils.getObjectMapper().readValue(file, Show.class);
    }

    private static Show fullDemo() {
        Show show = new Show()
                .setName("Full Demo");

        // H1
        MediaResource circusHorn = Constants.resourceCircusHorn().setVolume(0.6);
        Cue h1 = mediaCue(circusHorn.getName(), circusHorn)
                .setTrigger(TriggerType.HOT_KEY)
                .setTriggerProp("hotKey", "H")
                .setName("H1");
        show.add(h1);

        show.add(noteCue("No delay", "Note cue for '" + Cues.cueName() + "'"));

        // Q2
        nextIndex();
        SequentialCue q2 = sequentialCue("SEQUENCE");

        newSubIndex();
        Cue q2_1 = noteCue("NOTE Q2.1", "Note cue for 'Q2.1'")
                .setDelay(Duration.ofSeconds(5))
                .setTrigger(TriggerType.AUTO_START);

        nextIndex();
        Cue q2_2 = noteCue("NOTE Q2.2", "Note cue for 'Q2.2'")
                .setDelay(Duration.ofSeconds(5));

        nextIndex();
        ParallelCue q2_3 = (ParallelCue) parallelCue("PARALLEL Q2.3")
                .setDelay(Duration.ZERO.ofSeconds(10));

        newSubIndex();
        Cue q2_3_1 = noteCue("NOTE Q2.3.1", "Note cue for 'Q2.3.1'")
                .setDelay(Duration.ofMillis(1500));
        popSubIndex();
        q2_3.add(q2_3_1);

        popSubIndex();
        q2.add(q2_1, q2_2, q2_3);

        show.add(q2);

        // Q3
        nextIndex();
        ParallelCue q3 = parallelCue("PARALLEL");

        newSubIndex();
        Cue q3_1 = noteCue("NOTE Q3.1", "Note cue for 'Q3.1'");

        nextIndex();
        Cue q3_2 = noteCue("NOTE Q3.2", "Note cue for 'Q3.2'");

        popSubIndex();

        q3.add(q3_1, q3_2);
        show.add(q3);

        return show;
    }

    private static Show oneMediaCueWithDelay() {
        Show show = new Show()
                .setName("One Media Cue 5s Delay");

        MediaResource resource = Constants.resourceBrazilSamba()
                .setStopAt(Duration.ofSeconds(20))
                .setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(0.3);
        MediaCue cue = (MediaCue) mediaCue("Brazil Samba", resource)
                .setDelay(Duration.ofSeconds(5));
        show.add(cue);

        return show;
    }

    private static Show oneMediaCueInSequenceWithDelay() {
        Show show = new Show()
                .setName("One Media Cue in Sequence 5s Delay");

        MediaResource resource = Constants.resourceBrazilSamba()
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.5);
        Cue cue = mediaCue("Brazil Samba", resource)
                .setDelay(Duration.ofSeconds(3));

        Cue sequnce = sequentialCue("Media Sequence", cue)
                .setDelay(Duration.ofSeconds(3));

        return show.add(sequnce);
    }

    private static Show moanaAct1() {
        Show show = new Show()
                .setName("Moana Act 1");

        NoteCue setupNote = noteCue("Stage: Sets", "Setup stage for Moana Act 1")
                .setClearNotes(true);
        NoteCue micsNote = noteCue("Audio: Wireless Mics", "Pot up wireless mics for Moana Act 1");

        return show;
    }

}
