package com.nosqlrevolution.waltermittyclient.client.widgets;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Created by noSqlOrBust on 5/24/2014.
 */
public class MittyClickableCellText extends ClickableTextCell {

    String style;
    public MittyClickableCellText()
    {
        super();
        style = "myClickableCellTestStyle";
    }


    @Override
    protected void render(Cell.Context context, SafeHtml value, SafeHtmlBuilder sb) {
        if (value != null) {
            sb.appendHtmlConstant("<div class=\""+style+"\">");
            sb.append(value);
            sb.appendHtmlConstant("</div>");
        }
    }

    public void addStyleName(String style)
    {
        this.style = style;
    }


}
