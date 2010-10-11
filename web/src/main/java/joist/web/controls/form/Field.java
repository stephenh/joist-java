package joist.web.controls.form;

import java.util.List;

import joist.web.Control;

public interface Field extends Control {

  String getLabel();

  List<String> getErrors();

  boolean isHidden();

}
