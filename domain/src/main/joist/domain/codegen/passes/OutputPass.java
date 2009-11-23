package joist.domain.codegen.passes;

import joist.domain.codegen.Codegen;

public class OutputPass implements Pass {

    public void pass(Codegen codegen) {
        codegen.getOutputSourceDirectory().output();
        codegen.getOutputCodegenDirectory().output();
        // codegen.getOutputCodegenDirectory().pruneIfNotTouched();
    }

}
