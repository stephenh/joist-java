package joist.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Base class for Page, Form, Table, etc. */
public abstract class AbstractContainer extends AbstractControl {

    private final List<Control> controls = new ArrayList<Control>();
    private final List<Control> controlsReadOnly = Collections.unmodifiableList(this.controls);

    @Override
    public void onProcess() {
        for (Control control : this.getControls()) {
            control.onProcess();
        }
    }

    @Override
    public void onRender() {
        for (Control control : this.getControls()) {
            control.onRender();
        }
    }

    public void addControl(Control control) {
        this.controls.add(control);
        control.setParent(this);
    }

    public List<Control> getControls() {
        return this.controlsReadOnly;
    }

}
