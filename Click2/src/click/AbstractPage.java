package click;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A good default base class for users to extend.
 *
 * Implements the interfaces necessary for the traditional click
 * event flow (onInit, onGet/onPost, etc.)
 */
public class AbstractPage implements Page {

    private Map<String, Object> model = new HashMap<String, Object>();
    private List<Control> controls = new ArrayList();
    private ClickContext context = CurrentContext.get();

    public void onInit() {
    }

    public boolean onRender() {
        return true;
    }

    public Map<String, Object> getModel() {
        return this.model;
    }

    public String getTemplate() {
        return null;
    }

    public void addModel(String name, Object value) {
        this.model.put(name, value);
    }

    public List<Control> getControls() {
        return Collections.unmodifiableList(this.controls);
    }

    public void addControl(Control control) {
        this.controls.add(control);
    }

    public ClickContext getContext() {
        return this.context;
    }

    public PageProcessor getProcessor() {
        return new DefaultPageProcessor();
    }
}
