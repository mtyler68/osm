package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.app.application.MediaPlaylistLoadedEvent;
import org.mattie.osm.model.MediaPlaylistCue;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
public class MediaPlaylistCueViewModel extends CueViewModel<MediaPlaylistCue> {

    @Getter
    @Setter
    private double volume;

    @Getter
    @Setter
    private Duration crossfade;

    @Getter
    @Setter
    private List<MediaCueViewModel> mediaViewModels = new ArrayList<>();

    @Override
    public void play() {
        log.info("{}: play(): {}", getName(), this);
        embeddedPlay();

        super.play();
        publishEvent(new MediaPlaylistLoadedEvent(this));
    }

    @Override
    public void pause() {
        log.info("{}: pause(): {}", getName(), this);
        embeddedPause();
        super.pause();
    }

    @Override
    public void embeddedPause() {
        log.info("{}: embeddedPause(): {}", getName(), this);
        mediaViewModels.stream()
                .filter(mvm -> mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING)
                .forEach(mvm -> mvm.getMediaPlayer().pause());
        super.embeddedPause();
    }

    @Override
    public void embeddedPlay() {
        log.info("{}: embeddedPlay(): {}", getName(), this);
        getMediaViewModels().stream()
                .filter(mvm -> mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED)
                .forEach(mvm -> mvm.getMediaPlayer().play());
        super.embeddedPlay();
    }

    @Override
    public void stop() {
        log.info("{}: stop(): {}", getName(), this);
        embeddedStop();
        super.stop();
    }

    @Override
    public void embeddedStop() {
        log.info("{}: embeddedStop(): {}", getName(), this);
        getMediaViewModels().stream()
                .filter(mvm -> mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING
                || mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED)
                .forEach(mvm -> mvm.getMediaPlayer().stop());
        super.embeddedStop();
    }

    @Override
    public String getSimpleType() {
        return "Media Playlist";
    }

    @Override
    public void copyFrom(MediaPlaylistCue cue, CueViewModelContext context) {
        log.debug("copyFrom(): cue={}", cue);
        super.copyFrom(cue, context);

        setVolume(cue.getVolume());
        setCrossfade(fromTimeDuration(cue.getCrossfade()));

        // Create MediaCueViewModel instances to hold this playlist's media resources
        mediaViewModels = cue.getResources().stream()
                .map(r -> {
                    MediaCueViewModel viewModel = new MediaCueViewModel();
                    viewModel.copyFrom(r);
                    viewModel.buildMediaPlayer();
                    return viewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Class<MediaPlaylistCue> getCueClass() {
        return MediaPlaylistCue.class;
    }

    @Override
    public void buildAnimation() {
        Timeline timeline = new Timeline();
        //timeline.setDelay(getDelay());

        Duration startPos = Duration.ZERO;
        MediaCueViewModel lastMediaCueViewModel = null;

        for (MediaCueViewModel mediaViewModel : getMediaViewModels()) {
            double volume = mediaViewModel.getVolume() * getVolume();
            double startVolume = getCrossfade().equals(Duration.ZERO) ? volume : 0d;

            // Start frame
            timeline.getKeyFrames().add(new KeyFrame(startPos, (evt) -> {
                log.debug("{}: [start media]: {}", getName(), mediaViewModel.getSource());
                mediaViewModel.getMediaPlayer().play();
            }, new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), startVolume)));

            if (getCrossfade().greaterThan(Duration.ZERO)) {
                timeline.getKeyFrames().add(
                        new KeyFrame(
                                startPos.add(getCrossfade()),
                                (evt) -> log.debug("{}: [fade in finished]: {}", getName(), mediaViewModel.getSource()),
                                new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), volume)));
                timeline.getKeyFrames().add(
                        new KeyFrame(
                                startPos.add(mediaViewModel.getMediaPlayer().getTotalDuration().subtract(getCrossfade())),
                                (evt) -> log.debug("{}: [fade out start]: {}", getName(), mediaViewModel.getSource()),
                                new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), volume)));
                timeline.getKeyFrames().add(
                        new KeyFrame(
                                startPos.add(mediaViewModel.getMediaPlayer().getTotalDuration()),
                                (evt) -> log.debug("{}: [fade out finish]: {}", getName(), mediaViewModel.getSource()),
                                new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), 0)));
            }

            // End frame
            mediaViewModel.getMediaPlayer().setOnEndOfMedia(()
                    -> log.debug("{}: [end media]: {}", getName(), mediaViewModel.getSource()));

            startPos = startPos.add(mediaViewModel.getMediaPlayer().getTotalDuration());

            // End keyframe of media to get timeline length correct for last media
            timeline.getKeyFrames().add(new KeyFrame(startPos));

            if (getCrossfade().greaterThan(Duration.ZERO)) {
                startPos = startPos.subtract(getCrossfade());
            }

            lastMediaCueViewModel = mediaViewModel;

            // Controlling media playback
//            timeline.statusProperty().addListener((ov, oldVal, newVal) -> {
//                log.debug("{}: [animation state change]: newState={}", getName(), newVal);
//
//                switch (newVal) {
//                    case PAUSED:
//                        if (mediaViewModel.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
//                            mediaViewModel.pause();
//                        }
//                        break;
//                    case RUNNING:
//                        if (mediaViewModel.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED) {
//                            mediaViewModel.play();
//                        }
//                        break;
//                    case STOPPED:
//                        if (mediaViewModel.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED
//                                || mediaViewModel.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
//                            mediaViewModel.stop();
//                        }
//
//                        break;
//                    default:
//                        throw new AssertionError();
//                }
//            });
        }

        // Finish configuration of view model
//        timeline.setOnFinished((evt) -> finished());
//        timeline.currentTimeProperty().addListener((ov, oldVal, newVal) -> {
//            java.time.Duration d = java.time.Duration.ofMillis((int) newVal.toMillis());
//            String durString = String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
//            setCurrentTime(durString);
//        });
//        setDuration(formatDuration(timeline.getTotalDuration()));
        setAnimation(timeline);;

    }

    @Override
    protected CueViewModel newInstance() {
        return new MediaPlaylistCueViewModel();
    }

}
