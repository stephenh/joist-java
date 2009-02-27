package click;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Base class for Page, Form, Table, etc. */
public class AbstractContainer implements Container {

    private List<Control> controls = new ArrayList<Control>();
    private List<Control> controlsReadOnly = Collections.unmodifiableList(this.controls);

    @Override
    public void addControl(Control control) {
        if (!this.controls.contains(control)) {
            this.controls.add(control);
        }
    }

    @Override
    public List<Control> getControls() {
        return this.controlsReadOnly;
    }

}
