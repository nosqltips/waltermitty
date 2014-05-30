package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.*;
import com.nosqlrevolution.waltermittyclient.client.cmd.GetRestCmd;
import com.nosqlrevolution.waltermittyclient.client.cmd.PostRestCmd;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;

import java.util.ArrayList;

/**
 *
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 * @author noSqlOrBust
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
    private GetRestCmd restGetCmd = getRestCmd();
    private PostRestCmd restPostCmd = postRestCmd();
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
    private TabLayoutPanel centerTabPanel;
//    private Label errorLabel;
    private CenterPanelContainerPanel centerPanelContainerPanel;
    //    private Label errorLabel;
    private SliderVerticalPanelPanel sliderVerticalPanelPanel;


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
//        p.addNorth(northPanel, 5);

        southPanel = new SouthPanel();
        p.addSouth(southPanel, 2);

//        p.addEast(new HTML("east"), 2);
        westPanel = new WestPanel();
        p.addWest(westPanel, 20);

        centerTabPanel = new TabLayoutPanel(1.5, Style.Unit.EM);
        centerTabPanel.addStyleName("mitty-TabLayoutPanelTab");
//        centerPanel = new CenterPanel(restGetCmd);
//        centerTabPanel.add(centerPanel, "Data");
        centerPanelContainerPanel = new CenterPanelContainerPanel(restGetCmd);
        centerTabPanel.add(centerPanelContainerPanel, "Data");

//        centerTabPanel.add(new ScatterChartScrollPanel(), "Scatter Chart");
//        centerTabPanel.add(new LineChartScrollPanel(), "Line Chart");
//        centerTabPanel.add(new BarChartScrollPanel(), "Bar Chart");
//        centerTabPanel.add(new PieChartScrollPanel(), "Pie Chart");
//        centerTabPanel.add(new JsonChartPanel(), "Json Query Results");

        centerTabPanel.selectTab(0);

        p.add(centerTabPanel);

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
        nameField.setTitle("Enter member id");

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
//        facetContainer.getElement().setId("facetContainer");
//        facetContainer.setStyleName("facetContainer");

        sliderVerticalPanelPanel = new SliderVerticalPanelPanel();
        facetScrollContainer.add(sliderVerticalPanelPanel);
        facetScrollContainer.setStyleName("facetScrollContainer");
        westPanel.add(westPanelContainer);
//
//        westPanel.add(hp);


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

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand()
        {
            @Override
            public void execute()
            {
                doGetRestServiceCall("");
            }
        });
    }

    public GetRestCmd getRestCmd() {
        return new GetRestCmd()
        {
            @Override
            public void execute()
            {
                doGetRestServiceCall(getMemberId());
            }
        };
    }

    private PostRestCmd postRestCmd() {
        return new PostRestCmd()
        {
            @Override
            public void execute()
            {
                doPostRestServiceCall();
            }
        };
    }

    private void doPostRestServiceCall() {
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

    @Override
    public void onClick(ClickEvent clickEvent) {
        if (clickEvent.getSource() == sendButton) {
            doGetRestServiceCall(nameField.getText());
        }
    }

    @Override
    public void onKeyUp(KeyUpEvent keyUpEvent) {
        if (keyUpEvent.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            doGetRestServiceCall(nameField.getText());
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
     * Send the memberId to the server and wait for a response.
     */
    private void doGetRestServiceCall(final String memberId) {
        String textToServer = memberId;
        if (textToServer == null || textToServer.isEmpty() || textToServer.equalsIgnoreCase(messages.nameField()))
        {
            textToServer = "460";
        }

        // Then, we send the input to the server.
        //sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        centerPanelContainerPanel.showPleaseWait(true);

        final String finalTextToServer = textToServer;
        walterMittyService.get(textToServer,new AsyncCallback<SearchQuery>() {

            @Override
            public void onFailure(Throwable throwable) {
                //dialogBox.setWidget(dialogVPanel);
                // Show the RPC error message to the user
                centerPanelContainerPanel.showPleaseWait(false);
//                facetContainer.clear();
//                sliderVerticalPanelPanel.clear();

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
                    centerPanelContainerPanel.showPleaseWait(false);

                    dialogBox.setText("Remote Procedure Call - Failure");
                    serverResponseLabel.addStyleName("serverResponseLabelError");
                    serverResponseLabel.setHTML(SERVER_ERROR);
                    dialogBox.center();
                    closeButton.setFocus(true);

                    return;
                }

                WalterMittyClient.this.searchQuery = searchQuery;
                sliderVerticalPanelPanel.update(searchQuery.getBoosts());

                // Update center panel
                centerPanelContainerPanel.update(searchQuery);
                centerPanelContainerPanel.showPleaseWait(false);

                // Update south/bottom panel
                southPanel.update(searchQuery);

                // update nameField textbox with current memberId
                nameField.setText(finalTextToServer);

                if (nameField.getText().isEmpty() || nameField.getText().equals(messages.nameField())) {
                    nameField.addStyleName("textBoxWaterMark");
                    nameField.setText(messages.nameField());
                }
                else
                {
                    nameField.removeStyleName("textBoxWaterMark");
                }
            }
        });
    }
}