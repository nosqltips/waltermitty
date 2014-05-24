package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.*;
import com.nosqlrevolution.waltermittyclient.model.FacetRequest;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;

import java.util.ArrayList;

/**
 *
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author cmorgan
 */
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WalterMittyClient implements EntryPoint, ClickHandler, KeyUpHandler, FocusHandler, BlurHandler {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    private final WalterMittyServiceAsync walterMittyService = GWT.create(WalterMittyService.class);
    private final Messages messages = GWT.create(Messages.class);
    private ScrollPanel facetScrollContainer = new ScrollPanel();
    private FlowPanel westPanelContainer = new FlowPanel();
    private VerticalPanel facetContainer = new VerticalPanel();
    private ArrayList<DisclosurePanel> disclosurePanelList = new ArrayList<>();
    private boolean expandAllState = true;
    private Command postCmd = postNewQueryCmd();
    private SearchQuery searchQuery;

    private WestPanel westPanel;
    private NorthPanel northPanel;
    private SouthPanel southPanel;
    private CenterPanel centerPanel;
    private DialogBox dialogBox;
    private HTML serverResponseLabel;
    private Label textToServerLabel;
    private VerticalPanel dialogVPanel;
    private Button closeButton;
    private TextBox nameField;
    private Button sendButton;
//    private Label errorLabel;


    /**
     * Create a remote service proxy to talk to the server-side Walter Mitty service.
     */

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Attach five widgets to a DockLayoutPanel, one in each direction. Lay
        // them out in 'em' units.
        DockLayoutPanel p = new DockLayoutPanel(Style.Unit.EM);
        northPanel = new NorthPanel();
        p.addNorth(northPanel, 5);

        southPanel = new SouthPanel();
        p.addSouth(southPanel, 2);

//        p.addEast(new HTML("east"), 2);
        westPanel = new WestPanel();
        p.addWest(westPanel, 20);

        centerPanel = new CenterPanel(null, null);
        p.add(centerPanel);

        // Attach the LayoutPanel to the RootLayoutPanel. The latter will listen for
        // resize events on the window to ensure that its children are informed of
        // possible size changes.
        RootLayoutPanel rp = RootLayoutPanel.get();
        rp.add(p);


        sendButton = new Button(messages.sendButton());
        sendButton.addClickHandler(this);

        nameField = new TextBox();
        nameField.setStyleName("textBoxWaterMark");
        nameField.setText(messages.nameField());

        nameField.addKeyUpHandler(this);
        nameField.addFocusHandler(this);
        nameField.addClickHandler(this);
        nameField.addBlurHandler(this);

//        errorLabel = new Label();

        // We can add style names to widgets
        sendButton.addStyleName("sendButton");

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(nameField);
        hp.add(sendButton);
//        hp.add(errorLabel);
        hp.setStyleName("westPanelTopPanel");
        westPanel.add(hp);

        westPanelContainer.getElement().setId("westPanelVertContainer");
        westPanelContainer.setStyleName("westPanelVertContainer");
        westPanelContainer.add(facetScrollContainer);

        facetScrollContainer.getElement().setId("facetScrollContainer");
        facetContainer.getElement().setId("facetContainer");
        facetContainer.setStyleName("facetContainer");

        facetScrollContainer.add(facetContainer);
        facetScrollContainer.setStyleName("facetScrollContainer");
        westPanel.add(westPanelContainer);


        // Create the popup dialog box
        dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        textToServerLabel = new Label();
        serverResponseLabel = new HTML();

        dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending member id to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });

        // Add a handler to send the name to the server
//        final MyHandler handler = new MyHandler();
//        sendButton.addClickHandler(this);
//        nameField.addKeyUpHandler(this);

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand()
        {
            @Override
            public void execute()
            {
                sendNameToServer();
            }
        });
    }

    private Command postNewQueryCmd() {
        return new Command()
        {
            @Override
            public void execute()
            {
                postNewQuery();
            }
        };
    }

    private void postNewQuery() {
        centerPanel.showPleaseWait(true);
        walterMittyService.post(this.searchQuery, new AsyncCallback<SearchQuery>() {
            public void onFailure(Throwable caught) {
                caught.printStackTrace();

                centerPanel.update(searchQuery.getResults());
                southPanel.setMessage(caught.getMessage());
                centerPanel.showPleaseWait(false);

                if (((StatusCodeException)caught).getStatusCode() != 500)
                {
                    // Show the RPC error message to the user
                    dialogBox.setText("Remote Procedure Call - Failure");
                    serverResponseLabel.addStyleName("serverResponseLabelError");
                    serverResponseLabel.setHTML(SERVER_ERROR);
                    dialogBox.center();
                    closeButton.setFocus(true);
                }
            }

            @Override
            public void onSuccess(SearchQuery searchQuery) {
                // Re-initialize UI - Tables, ???
                centerPanel.update(searchQuery.getResults());
                southPanel.update(searchQuery);
                centerPanel.showPleaseWait(false);
            }
        });
    }

    private void setDisclosurePanelExpandAll(boolean expandAllState)
    {
        this.expandAllState = expandAllState;
        for (DisclosurePanel disclosurePanel : disclosurePanelList)
            disclosurePanel.setOpen(expandAllState);
    }

//    @Override
//    public HandlerRegistration addAttachHandler(AttachEvent.Handler handler) {
//        return null;
//    }
//
//    @Override
//    public boolean isAttached() {
//        return false;
//    }
//
//    @Override
//    public void fireEvent(GwtEvent<?> gwtEvent) {
//
//    }

    @Override
    public void onClick(ClickEvent clickEvent) {
        if (clickEvent.getSource() == sendButton) {
            sendNameToServer();
        }
    }

    @Override
    public void onKeyUp(KeyUpEvent keyUpEvent) {
        if (keyUpEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            sendNameToServer();
        }
    }

    @Override
    public void onFocus(FocusEvent focusEvent) {
        if (focusEvent.getSource() == nameField) {
            nameField.removeStyleName("textBoxWaterMark");

            if (nameField.getText().equals(messages.nameField())) {
                nameField.setText("");
            }
        }
    }

    @Override
    public void onBlur(BlurEvent blurEvent) {
        if (blurEvent.getSource() == nameField) {
            if (nameField.getText().isEmpty()) {
                nameField.addStyleName("textBoxWaterMark");
                nameField.setText(messages.nameField());
            }
        }
    }

    /**
     * Send the name from the nameField to the server and wait for a response.
     */
    private void sendNameToServer() {
        String textToServer = nameField.getText();
        if (textToServer.equalsIgnoreCase(messages.nameField()))
        {
            textToServer = "";
        }
//                if (!FieldVerifier.isValidName(textToServer)) {
//                    errorLabel.setText("Please enter a member id");
//                    return;
//                }

        // Then, we send the input to the server.
        //sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        centerPanel.showPleaseWait(true);
        walterMittyService.get(textToServer,new AsyncCallback<SearchQuery>() {

            @Override
            public void onFailure(Throwable throwable) {
                //dialogBox.setWidget(dialogVPanel);
                // Show the RPC error message to the user
                centerPanel.showPleaseWait(false);
                facetContainer.clear();

                dialogBox.setText("Remote Procedure Call - Failure");
                serverResponseLabel.addStyleName("serverResponseLabelError");
                serverResponseLabel.setHTML(SERVER_ERROR);
                dialogBox.center();
                closeButton.setFocus(true);
            }

            @Override
            public void onSuccess(SearchQuery searchQuery)
            {
                if (searchQuery == null)
                {
                    centerPanel.showPleaseWait(false);

                    dialogBox.setText("Remote Procedure Call - Failure");
                    serverResponseLabel.addStyleName("serverResponseLabelError");
                    serverResponseLabel.setHTML(SERVER_ERROR);
                    dialogBox.center();
                    closeButton.setFocus(true);

                    return;
                }

                WalterMittyClient.this.searchQuery = searchQuery;
                facetContainer.clear();

                for (FacetRequest facetRequest : searchQuery.getFacets()) {
                    DisclosurePanel disclosurePanel = new DisclosurePanel(facetRequest.getField().getDisplay());
                    disclosurePanel.add(new FacetPanel(facetRequest, postCmd));
                    disclosurePanel.setOpen(true);
                    facetContainer.add(disclosurePanel);

                    disclosurePanelList.add(disclosurePanel);
                }
                // Update center panel
                southPanel.update(searchQuery);

                centerPanel.update(searchQuery.getResults());
                centerPanel.showPleaseWait(false);
            }
        });
    }
}