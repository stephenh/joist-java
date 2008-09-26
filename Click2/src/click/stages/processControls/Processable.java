package click.stages.processControls;

import java.util.List;

import click.controls.Control;

public interface Processable {

    void addControl(Control control);

    List<Control> getControls();

}
