package com.nosqlrevolution.waltermittyclient.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/cbrown/NetBeansProjects/waltermitty/webconsole/src/main/resources/com/nosqlrevolution/waltermittyclient/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "<member id>".
   * 
   * @return translated "<member id>"
   */
  @DefaultMessage("<member id>")
  @Key("nameField")
  String nameField();

  /**
   * Translated "Search".
   * 
   * @return translated "Search"
   */
  @DefaultMessage("Search")
  @Key("sendButton")
  String sendButton();
}
