package org.mattie.osm.app.actions;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.mattie.osm.app.ShowController;
import org.mattie.osm.app.application.ResetEvent;
import org.mattie.osm.app.application.StageReadyEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.CueViewModelContext;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.mattie.osm.model.Show;
import org.mattie.osm.model.Utils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FileActions implements ApplicationContextAware {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private Stage primaryStage;

    private ApplicationContext applicationContext;

    @Autowired
    private ShowController showController;

    @PostConstruct
    public void init() {
        ActionMap.register(this);
    }

    @ActionProxy(id = ActionId.OPEN_SHOW, text = "Open...", graphic = "font>FontAwesome|FOLDER_OPEN")
    public void openShowAction() {
        log.debug("openShowAction()");

        showController.stop();
        showOpenShowDialog()
                .ifPresent(showFile
                        -> Platform.runLater(() -> openShow(showFile)));
    }

    @EventListener
    public void handleStageReady(StageReadyEvent evt) {
        if (this.primaryStage == null) {
            this.primaryStage = evt.stage;
        }
    }

    private Optional<File> showOpenShowDialog() {
        File recentDir = null;

        Preferences prefs = Preferences.userNodeForPackage(org.mattie.osm.app.OsmApplication.class);
        recentDir = new File(prefs.get("recentDir", null));

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Show");
        if (recentDir != null && recentDir.exists()) {
            fileChooser.setInitialDirectory(recentDir);
        }
        final File showFile = fileChooser.showOpenDialog(primaryStage);

        if (showFile != null) {

            prefs.put("recentDir", showFile.getParentFile().getAbsolutePath());
            try {
                prefs.flush();
            } catch (BackingStoreException ex) {
                log.warn("showOpenShowDialog(): exception saving preferences", ex);
            }
        }

        return Optional.ofNullable(showFile);
    }

    private void openShow(File showFile) {
        log.debug("openShow(): showFile={}", showFile);
        try {
            Show show = Utils.getObjectMapper().readValue(showFile, Show.class);
            CueViewModelContext context = new CueViewModelContext()
                    .setApplicationContext(applicationContext)
                    .setEventPublisher(eventPublisher);
            ShowViewModel showViewModel = new ShowViewModel(show, context);
            eventPublisher.publishEvent(new ResetEvent());
            eventPublisher.publishEvent(new ShowOpenedEvent(showViewModel));

        } catch (IOException ex) {
            log.error("Exception loading show file '{}'", showFile, ex);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
}
