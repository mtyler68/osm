package org.mattie.osm.app.viewcontrollers;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.mattie.osm.app.application.ResetEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.RichTextCueViewModel;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt
 */
@Component
@FxmlView("RichTextView.fxml")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RichTextViewController extends AbstractViewController {

    @FXML
    public WebView webView;

    @FXML
    public Pagination pagination;

    @FXML
    public Label titleLabel;

    @Getter(AccessLevel.PRIVATE)
    private WebEngine webEngine;

    @Getter(AccessLevel.PRIVATE)
    private List<String> richTextPages = new ArrayList<>();

    @PostConstruct
    public void postConstruct() {
        // Action configuration
        ActionMap.register(this);
    }

    @FXML
    public void initialize() {
        webEngine = webView.getEngine();

        getWebEngine().titleProperty().addListener((t) -> {
            titleLabel.setText(getWebEngine().getTitle());
        });

        pagination.currentPageIndexProperty().addListener((ov, oldVal, newVal) -> {
            displayRichTextPage(newVal.intValue());
        });
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        clear();
        ShowViewModel show = evt.getShowViewModel();

        // TODO: delete next line
        //configureTargets(show.getCueViewModels());
        configureCues(show.getCueViewModels(),
                ((cvm) -> cvm instanceof RichTextCueViewModel),
                ((cvm) -> {
                    ((RichTextCueViewModel) cvm).displayedProperty()
                            .addListener((ov, oldVal, newVal) -> {
                                if (newVal.booleanValue()) {
                                    loadPages((RichTextCueViewModel) cvm);
                                }
                            });
                }));
    }

    private void loadPages(RichTextCueViewModel cvm) {
        clear();
        setRichTextPages(cvm.getPages());
        setRichTextPage(0);
    }

    @EventListener
    public void handleReset(ResetEvent evt) {
        clear();
    }

    void clear() {
        richTextPages = new ArrayList<>();
        getWebEngine().loadContent("");
        titleLabel.setText("Title");
        pagination.setPageCount(1);
    }

    public void setRichTextPages(List<String> richTextPages) {
        this.richTextPages = richTextPages;
        getWebEngine().loadContent("");

        pagination.setPageCount(richTextPages.size() > 0 ? richTextPages.size() : 1);
        pagination.setCurrentPageIndex(0);

        if (!richTextPages.isEmpty()) {
            displayRichTextPage(0);
        }
    }

    public void setRichTextPage(int index) {
        pagination.setCurrentPageIndex(index);
    }

    /**
     * Called by pagination handler to update the web view.
     *
     * @param index
     */
    private void displayRichTextPage(int index) {
        setRichTextContent(richTextPages.get(index));
    }

    public void setRichTextContent(String text) {
        getWebEngine().loadContent(text);

    }

    @ActionProxy(id = "PAGE_DOWN", text = "Next Page")
    public void nextRichTextPage() {
        int index = pagination.getCurrentPageIndex() + 1;
        if (index < richTextPages.size()) {
            setRichTextPage(index);
        }
    }

    @ActionProxy(id = "PAGE_UP", text = "Prev Page")
    public void prevRichTextPage() {
        int index = pagination.getCurrentPageIndex() - 1;
        if (index >= 0) {
            setRichTextPage(index);
        }
    }

}
