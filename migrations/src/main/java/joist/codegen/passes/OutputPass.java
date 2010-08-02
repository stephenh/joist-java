package joist.codegen.passes;

import joist.codegen.Codegen;

public class OutputPass implements Pass {

  public void pass(Codegen codegen) {
    codegen.getOutputSourceDirectory().output();
    codegen.getOutputCodegenDirectory().output();
    // codegen.getOutputCodegenDirectory().pruneIfNotTouched();
  }

}
