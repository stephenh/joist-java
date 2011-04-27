package joist.codegen.passes;

import joist.codegen.Codegen;

public class OutputPass implements Pass {

  public void pass(Codegen codegen) {
    codegen.getOutputSourceDirectory().output();
    codegen.getOutputCodegenDirectory().output();
    if (codegen.getConfig().pruneCodegenDirectory) {
      codegen.getOutputCodegenDirectory().pruneIfNotTouched();
    }
  }

}
