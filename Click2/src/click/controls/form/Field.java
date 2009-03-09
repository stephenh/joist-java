package click.controls.form;

import java.util.List;

import click.Control;

public interface Field extends Control {

    String getLabel();

    List<String> getErrors();

}
