package joist.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

/** Base class for Page, Form, Table, etc. */
public abstract class AbstractContainer implements Container {

    private final List<Control> controls = new ArrayList<Control>();
    private final List<Control> controlsReadOnly = Collections.unmodifiableList(this.controls);

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

    public HttpSession getSession() {
        return CurrentContext.get().getRequest().getSession();
    }

}
