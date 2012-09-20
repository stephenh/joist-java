package joist.codegen.passes;

import joist.codegen.Codegen;

public class OutputPass implements Pass<Codegen> {

  public void pass(Codegen codegen) {
    codegen.getOutputSourceDirectory().output();
    codegen.getOutputCodegenDirectory().output();
    if (codegen.getConfig().pruneCodegenDirectory) {
      if (codegen.getConfig().pruneInAllDirectories) {
        codegen.getOutputCodegenDirectory().pruneIfNotTouched();
      } else {
        codegen.getOutputCodegenDirectory().pruneIfNotTouchedWithinUsedPackages();
      }
    }
  }

}
