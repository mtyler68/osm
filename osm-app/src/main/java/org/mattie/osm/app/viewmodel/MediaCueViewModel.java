package org.mattie.osm.app.viewmodel;

import java.io.File;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaResource;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
@ToString(callSuper = true, of = {"volume", "startAt", "stopAt", "fadeIn", "fadeOut", "source"})
public class MediaCueViewModel extends CueViewModel<MediaCue> {

    @Getter
    @Setter
    private MediaPlayer mediaPlayer;

    @Getter
    @Setter
    private double volume;

    @Getter
    @Setter
    private Duration startAt;

    @Getter
    @Setter
    private Duration stopAt;

    @Getter
    @Setter
    private String source;

    @Getter
    @Setter
    private Duration fadeIn;

    @Getter
    @Setter
    private Duration fadeOut;

    @Getter
    @Setter
    private MediaResource.MediaType mediaType;

    private BooleanProperty videoDisplayed = new SimpleBooleanProperty(false);

    public ReadOnlyBooleanProperty videoDisplayedProperty() {
        return videoDisplayed;
    }

//    @Override
//    public void play() {
//        switch (mediaPlayer.getStatus()) {
//            case PAUSED:
//            case READY:
//            case STOPPED:
//                mediaPlayer.play();
//                break;
//        }
//        super.play();
//    }
//    @Override
//    public void pause() {
//        log.info("{}: pause(): {}", getName(), this);
//        switch (mediaPlayer.getStatus()) {
//            case PLAYING:
//                mediaPlayer.pause();
//                break;
//        }
//        super.pause();
//    }
//
//    @Override
//    public void stop() {
//        log.info("{}: stop(): {}", getName(), this);
//        switch (mediaPlayer.getStatus()) {
//            case PAUSED:
//            case PLAYING:
//
//                mediaPlayer.stop();
//                break;
//        }
//        super.stop();
//    }
    @Override
    public String getSimpleType() {
        return "Media";
    }

    @Override
    public Class<MediaCue> getCueClass() {
        return MediaCue.class;
    }

    public void buildMediaPlayer() {
        log.debug("{}: buildMediaPlayer(): {}", getName(), this);

        if (mediaPlayer == null) {
            File mediaFile = new File(getSource());
            Media media = new Media(mediaFile.toURI().toString());
            setMediaPlayer(new MediaPlayer(media));

            getMediaPlayer().setVolume(getVolume());
            getMediaPlayer().setStartTime(getStartAt());

            if (getStopAt() != null) {
                getMediaPlayer().setStopTime(getStopAt());
            }
        }

//        getMediaPlayer().currentTimeProperty().addListener((ov, oldVal, newVal) -> {
//            setCurrentTime(formatDuration(newVal));
//        });
    }

    @Override
    public void buildAnimation() {
        buildMediaPlayer();

        getMediaPlayer().setOnReady(() -> {
//            ObservableList<Track> tracks = media.getTracks();
//            for (Track track : tracks) {
//                log.debug("{}: buildMediaPlayer(): track={}: {}",
//                        getName(), track, MediaCueViewModel.this);
//            }

            Timeline timeline = new Timeline();
//        timeline.setDelay(getDelay());
            double startVolume = (getFadeIn().greaterThan(Duration.ZERO) ? 0 : getVolume());

            getMediaPlayer().setOnPlaying(() -> log.debug("{}: (playing): {}", getName(), MediaCueViewModel.this));
            getMediaPlayer().setOnEndOfMedia(() -> {
                log.debug("{}: (endOfMedia): mediaPlay.status={}: {}",
                        getName(), getMediaPlayer().getStatus(), MediaCueViewModel.this);
                getMediaPlayer().stop();
            });

            // Start
            timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO,
                    (evt) -> {
                        log.debug("{}: [timeline play]: mediaPlayer.status={}: {}", getName(), getMediaPlayer().getStatus(), MediaCueViewModel.this);
                        getMediaPlayer().play();
                    },
                    new KeyValue(getMediaPlayer().volumeProperty(), startVolume)));

            if (getFadeIn().greaterThan(Duration.ZERO)) {
                timeline.getKeyFrames().add(new KeyFrame(getFadeIn(),
                        (evt) -> {
                            log.debug("{}: [fadeIn complete]: {}", getName(), MediaCueViewModel.this);
                        },
                        new KeyValue(getMediaPlayer().volumeProperty(), getVolume(), Interpolator.EASE_BOTH)));
            }

            // End
            double endVolume = getVolume();
            if (getFadeOut().greaterThan(Duration.ZERO)) {
                endVolume = 0;
                timeline.getKeyFrames().add(new KeyFrame(getMediaPlayer().getTotalDuration().subtract(getFadeOut()),
                        (evt) -> {
                            log.debug("{}: [fadeOut start]: {}", getName(), MediaCueViewModel.this);
                        },
                        new KeyValue(getMediaPlayer().volumeProperty(), getVolume())));
            }

            log.debug("{}: buildAnimation(): mediaPlayer.totalDuration={}", getName(), getMediaPlayer().getTotalDuration());
            timeline.getKeyFrames().add(new KeyFrame(getMediaPlayer().getTotalDuration(),
                    (evt) -> {
                        log.debug("{}: [fadeOut complete]: {}", getName(), MediaCueViewModel.this);
                    },
                    new KeyValue(getMediaPlayer().volumeProperty(), endVolume, Interpolator.EASE_BOTH)));

            // Controlling media playback
            timeline.statusProperty().addListener((ov, oldVal, newVal) -> {
                switch (newVal) {
                    case PAUSED:
                        getMediaPlayer().pause();
                        break;

                    case RUNNING:
                        getMediaPlayer().play();
                        break;

                    case STOPPED:
                        getMediaPlayer().stop();
                        break;
                }
            });

            // Done
            setAnimation(timeline);
        });

    }

    @Override
    public void jumpTo(Duration time) {
        log.debug("jumpTo(): time={}", time);
        super.jumpTo(time);

        // The MediaPlayer does not take into account the startTime or delay values when jumping to
        Duration jumpToTime = getStartAt().add(time).subtract(getDelay());

        getMediaPlayer().seek(jumpToTime);
    }

    @Override
    protected CueViewModel newInstance() {
        return new MediaCueViewModel();
    }

    @Override
    public void copyFrom(MediaCue cue, CueViewModelContext context) {
        log.info("copyFrom(): cue={}", cue);
        super.copyFrom(cue, context);
        copyFrom(cue.getMedia());
    }

    public void copyFrom(MediaResource resource) {
        log.info("copyFrom(): resource={}", resource);

        if (getName() == null) {
            setName(resource.getName());
        }

        setSource(resource.getFile());
        setVolume(resource.getVolume());
        setStartAt(fromTimeDuration(resource.getStartAt()));

        if (resource.getStopAt() != null) {
            setStopAt(fromTimeDuration(resource.getStopAt()));
        }

        setFadeIn(fromTimeDuration(resource.getFadeIn()));
        setFadeOut(fromTimeDuration(resource.getFadeOut()));

        setMediaType(resource.getType());
    }

    @Override
    public boolean isFadeableOut() {
        return true;
    }

    /**
     * Instructs cue to finish immediately and trigger lifecycle events.
     */
    public void end() {
        getMediaPlayer().stop();
        getAnimation().get().stop();
        finished();
    }

    @Override
    public void fadeOut(Duration dur) {

        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(getMediaPlayer().volumeProperty(), getMediaPlayer().getVolume())),
                new KeyFrame(dur, new KeyValue(getMediaPlayer().volumeProperty(), 0, Interpolator.EASE_BOTH)));
        timeline.setOnFinished((evt) -> {
            end();
        });

        stateProperty().addListener((ov, oldVal, newVal) -> {
            if (timeline.getStatus() != Animation.Status.STOPPED) {
                switch (newVal) {
                    case PAUSED:
                        timeline.pause();
                        break;

                    case PLAYING:
                        if (timeline.getStatus() != Animation.Status.RUNNING) {
                            timeline.play();
                        }
                        break;

                    default:
                        timeline.stop();
                        break;
                }
            }
        });

        timeline.play();
    }

}
