package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;


/**
 *
 * @author noSqlOrBust
 */
public class PleaseWaitWidget extends PopupPanel
{
    interface WalterMittyImages extends ClientBundle
    {
        @ClientBundle.Source("ajax-loader.gif")
        public ImageResource waitingAnimatedGif();
    }
    private static final WalterMittyImages images = GWT.create(WalterMittyImages.class);

    private final InlineLabel label;

    public PleaseWaitWidget(String message)
    {
        // Set the style for this widget
        setStyleName("pleaseWaitWidget");
        setModal(true);

        // Add a image and style
        FlowPanel dialogContent = new FlowPanel();
        Image img = new Image(images.waitingAnimatedGif());
        dialogContent.add(img);

        if (message == null)
            message = "";

        label = new InlineLabel(message);
        dialogContent.add(label);
        setWidget(dialogContent);
    }

    public void setText(String text)
    {
        label.setText(text);
    }

}
