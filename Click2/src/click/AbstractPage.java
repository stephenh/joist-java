package click;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/** A good base class for users to extend. */
public abstract class AbstractPage extends AbstractContainer implements Page {

    private final List<Control> controls = new ArrayList<Control>();
    private final ClickContext context = CurrentContext.get();

    public void onInit() {
    }

    public boolean onRender() {
        return true;
    }

    public Map<String, Object> getModel() {
        return this.context.getModel();
    }

    public void addModel(String name, Object value) {
        this.getModel().put(name, value);
    }

    public List<Control> getControls() {
        return Collections.unmodifiableList(this.controls);
    }

    public void addControl(Control control) {
        this.controls.add(control);
    }

    public PageProcessor getProcessor() {
        return DefaultPageProcessor.INSTANCE;
    }

}
