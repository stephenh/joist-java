package org.exigencecorp.domainobjects.codegen.passes;

import org.exigencecorp.domainobjects.codegen.Codegen;

public class OutputPass implements Pass {

    public void pass(Codegen codegen) {
        codegen.getOutputSourceDirectory().output();
        codegen.getOutputCodegenDirectory().output();
    }

}
