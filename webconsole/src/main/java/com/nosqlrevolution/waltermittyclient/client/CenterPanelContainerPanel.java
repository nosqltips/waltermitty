package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.nosqlrevolution.waltermittyclient.client.charts.ChartPanel;
import com.nosqlrevolution.waltermittyclient.client.cmd.GetRestCmd;
import com.nosqlrevolution.waltermittyclient.client.widgets.PleaseWaitWidget;
import com.nosqlrevolution.waltermittyclient.model.Result;

import java.util.List;

/**
 *
 * @author noSqlOrBust
 */
public class CenterPanelContainerPanel extends FlowPanel
{
    private ChartPanel chartPanel;
    private CenterPanel centerPanel;
    private GetRestCmd restGetCmd;
    private PleaseWaitWidget pleaseWaitWidget;

    public CenterPanelContainerPanel() {
        setStyleName("centerPanelContainerPanel");
    }

    public CenterPanelContainerPanel(GetRestCmd restGetCmd) {
        this.restGetCmd = restGetCmd;

        setStyleName("centerPanelContainerPanel");

        // add top panel (charts)
        HorizontalPanel hp = new HorizontalPanel();
        hp.setStyleName("sliderHoriztonalPanel");
        add(hp);

//        VerticalPanel verticalPanel = new VerticalPanel();
//        verticalPanel.setStyleName("sliderVerticalPanel");
//
//        SliderBar slider = new SliderBar(0.0, 3.0);
//        slider.setStepSize(0.1);
//        slider.setCurrentValue(1.5);
//        slider.setNumTicks(16);
//        slider.setNumLabels(6);
//        slider.setTitle("Birth Year");
//
//
//        SliderBar slider1 = new SliderBar(0.0, 3.0);
//        slider1.setStepSize(0.1);
//        slider1.setCurrentValue(1.5);
//        slider1.setNumTicks(16);
//        slider1.setNumLabels(6);
//        slider1.setTitle("Gender");
//
//
//        SliderBar slider2 = new SliderBar(0.0, 3.0);
//        slider2.setStepSize(0.1);
//        slider2.setCurrentValue(1.5);
//        slider2.setNumTicks(16);
//        slider2.setNumLabels(6);
//        slider2.setTitle("");
//
//        SliderBar slider3 = new SliderBar(0.0, 3.0);
//        slider3.setStepSize(0.1);
//        slider3.setCurrentValue(1.5);
//        slider3.setNumTicks(16);
//        slider3.setNumLabels(6);
//        slider3.setTitle("");
//
//        SliderBar slider4 = new SliderBar(0.0, 3.0);
//        slider4.setStepSize(0.1);
//        slider4.setCurrentValue(1.5);
//        slider4.setNumTicks(16);
//        slider4.setNumLabels(6);
//
//        SliderBar slider5 = new SliderBar(0.0, 3.0);
//        slider5.setStepSize(0.1);
//        slider5.setCurrentValue(1.5);
//        slider5.setNumTicks(16);
//        slider5.setNumLabels(6);
//        slider5.setTitle("");
//
//        SliderBar slider6 = new SliderBar(0.0, 3.0);
//        slider6.setStepSize(0.1);
//        slider6.setCurrentValue(1.5);
//        slider6.setNumTicks(16);
//        slider6.setNumLabels(6);
//        slider6.setTitle("");
//
//        SliderBar slider7 = new SliderBar(0.0, 3.0);
//        slider7.setStepSize(0.1);
//        slider7.setCurrentValue(1.5);
//        slider7.setNumTicks(16);
//        slider7.setNumLabels(6);
//        slider7.setTitle("");
//
//        SliderBar slider8 = new SliderBar(0.0, 3.0);
//        slider8.setStepSize(0.1);
//        slider8.setCurrentValue(1.5);
//        slider8.setNumTicks(16);
//        slider8.setNumLabels(6);
//        slider8.setTitle("");
//
//        SliderBar slider9 = new SliderBar(0.0, 3.0);
//        slider9.setStepSize(0.1);
//        slider9.setCurrentValue(1.5);
//        slider9.setNumTicks(16);
//        slider9.setNumLabels(6);
//        slider9.setTitle("");
//
//        verticalPanel.add(slider);
//        verticalPanel.add(slider1);
//        verticalPanel.add(slider2);
//        verticalPanel.add(slider3);
//        verticalPanel.add(slider4);
//        verticalPanel.add(slider5);
//        verticalPanel.add(slider6);
//        verticalPanel.add(slider7);
//        verticalPanel.add(slider8);
//        verticalPanel.add(slider9);
//
//        hp.add(verticalPanel);


        chartPanel = new ChartPanel();
        hp.add(chartPanel);


        // add bottom panel (table panel)
        centerPanel = new CenterPanel(restGetCmd);

//        add(hp);
        add(centerPanel);
    }

    public void showPleaseWait(boolean show)
    {
        if (pleaseWaitWidget == null)
            pleaseWaitWidget = new PleaseWaitWidget(null);

        if (show)
        {
            pleaseWaitWidget.setPopupPositionAndShow(new PopupPanel.PositionCallback()
            {
                @Override
                public void setPosition(int offsetWidth, int offsetHeight)
                {
                    int left = CenterPanelContainerPanel.this.getAbsoluteLeft() + CenterPanelContainerPanel.this.getOffsetWidth() / 2 - offsetWidth / 2;
                    int top = CenterPanelContainerPanel.this.getAbsoluteTop() + CenterPanelContainerPanel.this.getOffsetHeight() / 2 - offsetHeight / 2;
//                    int left = Windows.this.getAbsoluteLeft() + CenterPanel.this.getOffsetWidth() / 2 - offsetWidth / 2;
//                    int top = CenterPanel.this.getAbsoluteTop() + CenterPanel.this.getOffsetHeight() / 2 - offsetHeight / 2;
                    pleaseWaitWidget.setPopupPosition(left, top);
                }
            });
            pleaseWaitWidget.setGlassEnabled(true);
        }
        else
        {
            pleaseWaitWidget.hide(true);
        }
    }

    public void update(List<Result> results) {
        centerPanel.update(results);
    }
}
