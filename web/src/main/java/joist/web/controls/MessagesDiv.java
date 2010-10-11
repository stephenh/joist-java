package joist.web.controls;

import java.util.ArrayList;
import java.util.List;

import joist.util.Interpolate;
import joist.web.AbstractControl;
import joist.web.CurrentContext;
import joist.web.util.HtmlWriter;

/** Stores/displays messages in the flash. */
public class MessagesDiv extends AbstractControl<MessagesDiv> {

  public MessagesDiv() {
    this.setId("messages");
  }

  public void addInfo(String text, Object... args) {
    this.add("info", text, args);
  }

  public void addError(String text, Object... args) {
    this.add("error", text, args);
  }

  public void add(String type, String text, Object... args) {
    text = Interpolate.string(text, CurrentContext.get().getWebConfig().getTextConverterRegistry(), args);
    this.getMessages(true).add(new Message(type, text));
  }

  /** Does not render anything unless we have messages to display--also checks the flash. */
  public void render(HtmlWriter w) {
    if (!this.hasMessages()) {
      return;
    }
    List<Message> messages = this.getMessages(false);
    w.line("<div id={} class={}>", this.getId(), "messages");
    w.line("<ul>");
    int i = 0;
    for (Message message : messages) {
      w.line("<li id={} class={}>{}</li>", (this.getId() + "." + (i++)), message.type, message.text);
    }
    w.line("</ul>");
    w.line("</div>");
  }

  public boolean hasMessages() {
    List<Message> messages = this.getMessages(false);
    return messages != null && messages.size() > 0;
  }

  @SuppressWarnings("unchecked")
  private List<Message> getMessages(boolean create) {
    List<Message> messages = (List<Message>) CurrentContext.get().getFlash().get("messages-div-" + this.getId());
    if (messages == null && create) {
      messages = new ArrayList<Message>();
      CurrentContext.get().getFlash().put("messages-div-" + this.getId(), messages);
    }
    return messages;
  }

  protected MessagesDiv getThis() {
    return this;
  }

  public static class Message {
    public final String type;
    public final String text;

    public Message(String type, String text) {
      this.type = type;
      this.text = text;
    }
  }

}
