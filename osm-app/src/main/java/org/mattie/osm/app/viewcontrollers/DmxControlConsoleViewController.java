package org.mattie.osm.app.viewcontrollers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.mattie.osm.app.DmxController;
import org.mattie.osm.app.actions.ActionId;
import org.mattie.osm.app.application.StageReadyEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.DmxCueViewModel;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.mattie.osm.model.DmxCue;
import org.mattie.osm.model.TriggerType;
import org.mattie.osm.model.Utils;
import org.mattie.osm.model.dmx.DmxChannel;
import org.mattie.osm.model.dmx.DmxDimmerChannel;
import org.mattie.osm.model.dmx.DmxSetup;
import org.mattie.osm.model.dmx.DmxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt Tyler
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@FxmlView("DmxControlConsoleView.fxml")
public class DmxControlConsoleViewController extends AbstractViewController {

    @Autowired
    private DmxController dmxController;

    @Autowired
    private DmxKeyFrameViewController keyFrameController;

    @Autowired
    private FxWeaver fxWeaver;

    private Stage dmxStage;

    private Scene scene;

    @FXML
    public MenuItem addDimmerMenuItem;

    @FXML
    public FlowPane controlsList;

    @FXML
    public Button checkAllButton;

    @FXML
    public Button uncheckAllButton;

    @FXML
    public MenuItem saveDmxSetupButton;

    @FXML
    public MenuItem saveDmxSetupAsButton;

    @FXML
    public MenuItem openDmxSetupButton;

    @FXML
    public MenuItem newDmxSetupMenuItem;

    @FXML
    public Button allOnButton;

    @FXML
    public Button allOffButton;

    @FXML
    public Button addKeyButton;

    @FXML
    public Button playPauseButton;

    @FXML
    public MenuItem newKeyFramesMenuItem;

    @FXML
    public MenuItem openKeyFramesMenuItem;

    @FXML
    public MenuItem saveKeyFramesMenuItem;

    @FXML
    public MenuItem saveAsKeyFramesMenuItem;

    private Preferences prefs;

    private int lastAddress = 1;

    private ObjectProperty<File> dmxSetupFile = new SimpleObjectProperty<>();

    private BooleanProperty dmxSetupChanged = new SimpleBooleanProperty(false);

    private BooleanProperty keyFramesChanged = new SimpleBooleanProperty(false);

    private ObjectProperty<File> keyFramesFile = new SimpleObjectProperty<>();

    public ReadOnlyObjectProperty<File> keyFramesFileProperty() {
        return keyFramesFile;
    }

    public void setKeyFramesFile(File keyFramesFile) {
        this.keyFramesFile.set(keyFramesFile);
    }

    public File getKeyFramesFile() {
        return keyFramesFile.get();
    }

    public ReadOnlyBooleanProperty keyFramesChangedProperty() {
        return keyFramesChanged;
    }

    public void setKeyFramesChanged(boolean keyFramesChanged) {
        this.keyFramesChanged.set(keyFramesChanged);
    }

    public boolean isKeyFramesChanged() {
        return keyFramesChanged.get();
    }

    public ReadOnlyObjectProperty<File> dmxSetupFileProperty() {
        return dmxSetupFile;
    }

    public void setDmxSetupFile(File dmxSetupFile) {
        this.dmxSetupFile.set(dmxSetupFile);
    }

    public File getDmxSetupFile() {
        return dmxSetupFile.get();
    }

    public ReadOnlyBooleanProperty dmxSetupChanged() {
        return dmxSetupChanged;
    }

    public void setDmxSetupChanged(boolean dmxSetupChanged) {
        this.dmxSetupChanged.set(dmxSetupChanged);
    }

    public boolean isDmxSetupChanged() {
        return dmxSetupChanged.get();
    }

    @PostConstruct
    public void init() {
        log.debug("init()");
        ActionMap.register(this);

        prefs = Preferences.userNodeForPackage(DmxControlConsoleViewController.class);

    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {

        addKeyButton.setOnAction(evt -> {
            List<DmxDimmerControlViewController> dimmers = controlsList.getChildren().stream()
                    .map(v -> (DmxDimmerControlViewController) v.getUserData())
                    .collect(Collectors.toList());

            keyFrameController.createKeyFrame(dimmers);
        });

        // Save
        saveDmxSetupButton.disableProperty().bind(Bindings.not(dmxSetupChanged));

        // Open
        openDmxSetupButton.setOnAction(evt -> {
            trySave(evt);
            if (!evt.isConsumed()) {
                try {
                    File lastPath = new File(prefs.get("dmxSetup.path", null));
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open DMX Setup");
                    if (lastPath != null && lastPath.exists()) {
                        fileChooser.setInitialDirectory(lastPath);
                    }
                    final File dmxSetupFile = fileChooser.showOpenDialog(dmxStage);

                    if (dmxSetupFile != null) {

                        prefs.put("dmxSetup.path", dmxSetupFile.getParentFile().getAbsolutePath());
                        prefs.flush();

                        DmxSetup dmxSetup = DmxUtils.getObjectMapper().readValue(dmxSetupFile, DmxSetup.class);
                        loadDmxSetup(dmxSetup);

                        setDmxSetupFile(dmxSetupFile);
                        setDmxSetupChanged(false);

                    } else {
                        evt.consume();
                    }
                } catch (Exception ex) {
                    log.error("Exception while opening DMX setup", ex);
                }
            }
        });

        // Stage
        dmxStage.setOnCloseRequest(evt -> {
            trySave(evt);
        });

        addDimmerMenuItem.setOnAction((evt) -> {
            FxControllerAndView<DmxDimmerDialogViewController, Node> controllerAndView
                    = fxWeaver.load(DmxDimmerDialogViewController.class);
            DmxDimmerDialogViewController controller = controllerAndView.getController();
            controller.addressTextField.setText(Integer.toString(lastAddress));

            Scene scene = new Scene((Parent) controllerAndView.getView().get());
            Stage stage = new Stage();
            controller.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("DMX Dimmer Properties");
            stage.showAndWait();
            if (controller.isOkPressed()) {
                log.debug("DMX Dimmer Dialog 'Ok' pressed");
                String name = controller.nameTextField.getText();
                int address = Integer.parseInt(controller.addressTextField.getText());
                addChannel(new DmxDimmerChannel().setName(name).setAddresses(address));
                setDmxSetupChanged(true);
            }
        });

        // Check/Uncheck All
        checkAllButton.setOnAction(evt -> setAllKeyFrameCheckBoxes(true));
        uncheckAllButton.setOnAction(evt -> setAllKeyFrameCheckBoxes(false));

        // Save DMX Setup Save As
        saveDmxSetupAsButton.setOnAction(evt -> saveAs(evt));
        saveDmxSetupButton.setOnAction(evt -> save(evt));

        dmxSetupFileProperty().addListener((ov, oldVal, newVal) -> {
            if (newVal == null) {
                prefs.remove("dmxSetup.lastFile");
            } else {
                prefs.put("dmxSetup.lastFile", newVal.getPath());
            }

            try {
                prefs.flush();
            } catch (BackingStoreException ex) {
                log.warn("Exception while saving prefs", ex);
            }
            dmxStage.setTitle("DMX Control Console" + (newVal == null ? "" : " - " + newVal.getPath()));
        });

        String lastFilePath = prefs.get("dmxSetup.lastFile", null);
        if (lastFilePath != null) {
            File file = new File(lastFilePath);
            try {
                DmxSetup dmxSetup = DmxUtils.getObjectMapper().readValue(file, DmxSetup.class);
                loadDmxSetup(dmxSetup);

                setDmxSetupFile(file);
                setDmxSetupChanged(false);

            } catch (IOException ex) {
                log.error("Exception while opening DMX setup", ex);
            }
        }

        newDmxSetupMenuItem.setOnAction(evt -> {
            clear();
            setDmxSetupChanged(false);
            setDmxSetupFile(null);
        });

        configureActionButton(ActionId.DMX_ALL_ON, allOnButton);
        configureActionButton(ActionId.DMX_ALL_OFF, allOffButton);

        playPauseButton.setOnAction(evt -> keyFrameController.togglePlayPause());

        newKeyFramesMenuItem.setOnAction(evt -> {
            keyFrameController.keyFrameList.getItems().clear();
            setKeyFramesChanged(false);
            setKeyFramesFile(null);
        });

        saveKeyFramesMenuItem.setOnAction(this::saveKeyFrames);
        saveAsKeyFramesMenuItem.setOnAction(this::saveAsKeyFrames);

    }

    @ActionProxy(id = ActionId.DMX_ALL_ON, text = "DMX All On")
    public void dmxAllOnAction() {
        controlsList.getChildren().stream()
                .map(c -> (DmxDimmerControlViewController) c.getUserData())
                .forEach(vc -> vc.turnOn());
    }

    @ActionProxy(id = ActionId.DMX_ALL_OFF, text = "DMX All Off")
    public void dmxAllOffAction() {
        controlsList.getChildren().stream()
                .map(c -> (DmxDimmerControlViewController) c.getUserData())
                .forEach(vc -> vc.turnOff());
    }

    /**
     * If the event is consumed, then the save action was cancelled.
     *
     * @param evt
     */
    private void trySave(Event evt) {
        if (isDmxSetupChanged()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("DMX Setup Changed");
            alert.setHeaderText("DMX Setup Changes");
            alert.setContentText("DMX Setup has changed. What would you like to do?");

            ButtonType save = new ButtonType("Save");
            ButtonType discard = new ButtonType("Discard");
            ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(save, discard, cancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == save) {
                save(evt);
            } else if (result.get() == cancel) {
                evt.consume();
            }
        }
    }

    /**
     * If the event is consumed, then the save action was cancelled.
     *
     * @param evt
     */
    private void trySaveKeyFrames(Event evt) {
        if (isKeyFramesChanged()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Key Frames Changed");
            alert.setHeaderText("Key Frame Changes");
            alert.setContentText("Key Frames have changed. What would you like to do?");

            ButtonType save = new ButtonType("Save");
            ButtonType discard = new ButtonType("Discard");
            ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(save, discard, cancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == save) {
                saveKeyFrames(evt);
            } else if (result.get() == cancel) {
                evt.consume();
            }
        }
    }

    private void saveAs(Event evt) {
        try {
            File lastPath = new File(prefs.get("dmxSetup.path", null));
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save DMX Setup As");
            if (lastPath != null && lastPath.exists()) {
                fileChooser.setInitialDirectory(lastPath);
            }
            File dmxSetupFile = fileChooser.showSaveDialog(dmxStage);

            if (dmxSetupFile != null) {

                prefs.put("dmxSetup.path", dmxSetupFile.getParentFile().getAbsolutePath());
                prefs.flush();

                if (!dmxSetupFile.getName().endsWith(".yaml")) {
                    dmxSetupFile = new File(dmxSetupFile.getPath() + ".yaml");
                }

                DmxUtils.getObjectMapper().writeValue(dmxSetupFile, toDmxSetup());

                setDmxSetupFile(dmxSetupFile);
                setDmxSetupChanged(false);
            } else {
                evt.consume();
            }
        } catch (Exception ex) {
            log.error("Exception while saving as", ex);
        }
    }

    private void saveAsKeyFrames(Event evt) {
        try {
            String lastKeyFramesPath = prefs.get("keyFrames.path", null);
            File lastPath = null;
            if (lastKeyFramesPath != null) {
                lastPath = new File(lastKeyFramesPath);
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Key Frames As");
            if (lastPath != null && lastPath.exists()) {
                fileChooser.setInitialDirectory(lastPath);
            }
            File keyFramesFile = fileChooser.showSaveDialog(dmxStage);

            if (keyFramesFile != null) {

                prefs.put("keyFrames.path", keyFramesFile.getParentFile().getAbsolutePath());
                prefs.flush();

                if (!keyFramesFile.getName().endsWith(".yaml")) {
                    keyFramesFile = new File(keyFramesFile.getPath() + ".yaml");
                }

                setKeyFramesFile(keyFramesFile);
                saveKeyFrames(evt);
            } else {
                evt.consume();
            }
        } catch (Exception ex) {
            log.error("Exception while saving key frames as", ex);
        }
    }

    /**
     * Saves the current DMX setup. Will perform a Save As if not file is
     * currently specified.
     *
     * @return True if saved. False is save action was cancelled.
     */
    private void save(Event evt) {
        try {
            if (getDmxSetupFile() != null) {
                DmxUtils.getObjectMapper().writeValue(getDmxSetupFile(), toDmxSetup());
                setDmxSetupChanged(false);
            } else {
                saveAs(evt);
            }
        } catch (Exception ex) {
            log.error("Error saving file", ex);
        }
    }

    private void saveKeyFrames(Event evt) {
        try {
            if (getKeyFramesFile() != null) {
                TextInputDialog textInputDialog = new TextInputDialog("1");
                textInputDialog.setTitle("Enter Hot Key Value");
                textInputDialog.setContentText("Enter hot key value");
                Optional<String> showAndWait = textInputDialog.showAndWait();

                DmxCue dmxCue = (DmxCue) new DmxCue()
                        .setTrigger(TriggerType.HOT_KEY)
                        .setTriggerProp("hotKey", textInputDialog.getEditor().getText());
                keyFrameController.keyFrameList.getItems().stream()
                        .forEach(dmxCue::add);
                Utils.getObjectMapper().writeValue(getKeyFramesFile(), dmxCue);
                setKeyFramesChanged(false);
            } else {
                saveAsKeyFrames(evt);
            }
        } catch (Exception ex) {
            log.error("Error saving file", ex);
        }
    }

    private DmxSetup toDmxSetup() {
        List<DmxChannel> dmxChannels = controlsList.getChildren().stream()
                .map(v -> (DmxDimmerControlViewController) v.getUserData())
                .map(c -> c.toChannel())
                .collect(Collectors.toList());
        DmxSetup dmxSetup = new DmxSetup().setChannels(dmxChannels);
        return dmxSetup;
    }

    private void setAllKeyFrameCheckBoxes(boolean state) {
        controlsList.getChildren().stream()
                .map(v -> (DmxDimmerControlViewController) v.getUserData())
                .forEach(v -> v.keyFrameCheck.setSelected(state));
    }

    @EventListener
    public void handleStageReady(StageReadyEvent evt) {
        dmxStage = new Stage();
        dmxStage.initOwner(evt.stage);

        Node root = fxWeaver.loadView(DmxControlConsoleViewController.class);
        scene = new Scene((Parent) root, 800, 600);
        dmxStage.setScene(scene);
        dmxStage.setTitle("DMX Control Console");

        dmxStage.showingProperty().addListener((ov, oldVal, newVal) -> {
            dmxStage.setTitle("DMX Control Console" + (getDmxSetupFile() == null ? "" : " - " + getDmxSetupFile().getPath()));
        });
    }

    @ActionProxy(id = ActionId.TOGGLE_DMX_CONSOLE, text = "DMX Console", graphic = "font>FontAwesome|SLIDERS")
    public void toggleDmxConsoleAction() {
        if (dmxStage.isShowing()) {
            dmxStage.hide();
        } else {
            dmxStage.show();
        }
    }

    private void clear() {
        lastAddress = 1;
        controlsList.getChildren().clear();
    }

    private void loadDmxSetup(DmxSetup dmxSetup) {
        clear();
        dmxSetup.getChannels().stream()
                .forEach(c -> addChannel(c));
    }

    private void addChannel(DmxChannel channel) {
        if (channel instanceof DmxDimmerChannel) {
            addChannel((DmxDimmerChannel) channel);
        }
    }

    private void addChannel(DmxDimmerChannel channel) {
        FxControllerAndView<DmxDimmerControlViewController, Node> dmxDimmerCV
                = fxWeaver.load(DmxDimmerControlViewController.class);

        final DmxDimmerControlViewController controller = dmxDimmerCV.getController();
        final Node view = dmxDimmerCV.getView().get();
        controller.nameText.setText(channel.getName());
        controller.setAddress(channel.getAddresses()[0]);
        view.setUserData(dmxDimmerCV.getController());

        view.setOnDragDetected((evt) -> {
            log.debug("Drag detected");
            evt.setDragDetect(true);
        });

        controller.deleteLink.setOnAction(evt -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete DMX Control \"" + controller.nameText.getText() + "\"");
            alert.setContentText("Select Ok to delete");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                controlsList.getChildren().remove(view);
                setDmxSetupChanged(true);
            }
        });

        controller.editLink.setOnAction(evt -> {
            FxControllerAndView<DmxDimmerDialogViewController, Node> controllerAndView
                    = fxWeaver.load(DmxDimmerDialogViewController.class);
            DmxDimmerDialogViewController dialog = controllerAndView.getController();
            dialog.addressTextField.setText(controller.addressLabel.getText());
            dialog.nameTextField.setText(controller.nameText.getText());

            Scene scene = new Scene((Parent) controllerAndView.getView().get());
            Stage stage = new Stage();
            dialog.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("DMX Dimmer Properties");
            stage.showAndWait();
            if (dialog.isOkPressed()) {
                log.debug("DMX Dimmer Dialog 'Ok' pressed");
                String name = dialog.nameTextField.getText();
                int address = Integer.parseInt(dialog.addressTextField.getText());
                controller.addressLabel.setText(Integer.toString(address));
                controller.nameText.setText(name);
                setDmxSetupChanged(true);
            }

        });

        controller.valueProperty().addListener((ov, oldVal, newVal) -> {
            int data = (int) (255d * newVal.doubleValue() / 100d);
            dmxController.getDmxBuffer().render(controller.getAddress(), data);
        });

        controlsList.getChildren().add(dmxDimmerCV.getView().get());

        lastAddress = channel.getAddresses()[0] + 1;
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        log.info("showOpenedHandler()");
        ShowViewModel showViewModel = evt.getShowViewModel();

        configureCues(showViewModel.getHotKeyViewModels(),
                ((cvm) -> cvm instanceof DmxCueViewModel),
                ((cvm) -> configure((DmxCueViewModel) cvm)));
    }

    public DmxDimmerControlViewController getDmxControllerForAddress(int address) {
        for (Node node : controlsList.getChildren()) {
            if (((DmxDimmerControlViewController) node.getUserData()).getAddress() == address) {
                return (DmxDimmerControlViewController) node.getUserData();
            }
        }
        return null;
    }

    private void configure(DmxCueViewModel dmxCueViewModel) {
        log.info("binding DMX cues");
//        dmxCueViewModel.getKeyFrames().stream().forEach(kf -> {
//
////            for (DmxKeyValue value : kf.getValues()) {
////                DmxDimmerControlViewController controller = getDmxControllerForAddress(value.getAddress());
////                log.info("bind to address {}", value.getAddress());
////                if (controller != null) {
////                    log.info("bind to address {}, controller not null", value.getAddress());
////                    //controller.valueProperty().bind(dmxCueViewModel.getValues().get(value.getAddress() - 1));
////                    //dmxCueViewModel.getValues().get(value.getAddress() - 1).bind(controller.valueProperty());
////                }
////            }
//
//        });
    }

}
