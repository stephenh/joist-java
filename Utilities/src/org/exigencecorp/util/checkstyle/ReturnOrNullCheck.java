package org.exigencecorp.util.checkstyle;

// import antlr.collections.AST;

/**
 * Returning null means the method must be suffixed with OrNull.
 */
public class ReturnOrNullCheck { // extends Check {

    /*
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.LITERAL_NULL };
    }

    public void visitToken(DetailAST ast) {
        if (ast.getType() != TokenTypes.LITERAL_NULL) {
            return;
        }

        // Is parent EXPR and its parent LITERAL_RETURN?
        if (ast.getParent() == null
            || ast.getParent().getType() != TokenTypes.EXPR
            || ast.getParent().getParent() == null
            || ast.getParent().getParent().getType() != TokenTypes.LITERAL_RETURN) {
            return;
        }

        // Walk up to METHOD_DEF
        DetailAST methodDef = ast.getParent();
        while (methodDef != null && methodDef.getType() != TokenTypes.METHOD_DEF) {
            methodDef = methodDef.getParent();
        }
        if (methodDef == null) {
            return;
        }

        // Find IDENT within METHOD_DEF
        AST ident = methodDef.getFirstChild();
        while (ident != null && ident.getType() != TokenTypes.IDENT) {
            ident = ident.getNextSibling();
        }
        if (ident == null) {
            return;
        }

        String declaredMethodName = ident.getText();
        if (!declaredMethodName.endsWith("OrNull")) {
            this.log(ast, "Method name must end with 'OrNull' if explicitly returning null.");
        }
    }
    */

}
