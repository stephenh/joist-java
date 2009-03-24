package joist.web;

import java.util.List;

public interface Container extends Control {

    void addControl(Control control);

    List<Control> getControls();

}
