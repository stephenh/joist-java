package org.exigencecorp.util.checkstyle;

// import antlr.collections.AST;


/**
 * Using LITERAL_SUPER if calling the base class implementation.
 *
 * Good code for viewing the AST with the checkstyle GUI:
 * 
 * <code>
 *      public class Foo {
 *          public Foo() {
 *              super();
 *          }
 *          public void fooMethod() {
 *              this.toString();
 *              Object.super.toString();
 *              super.toString().toString();
 *              toString();
 *          }
 *      }
 *  </code>
 */
public class InvalidSuperCheck { // extends Check {

    /*
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.LITERAL_SUPER };
    }

    public void visitToken(DetailAST ast) {
        if (ast.getType() != TokenTypes.LITERAL_SUPER) {
            return;
        }

        String callingMethodName = null;
        if (ast.getNextSibling() != null) {
            // If the syntax is "super.foo()" then 'foo' is our sibling
            callingMethodName = ast.getNextSibling().getText();
        } else {
            // The syntax is "Object.super.foo()" and then 'foo' is up and over
            callingMethodName = ast.getParent().getNextSibling().getText();
        }

        // See if the method you are trying to call is in fact also declared in
        // the current class, indicating you might know what you are doing and
        // do in fact want to explicitly use the overridden super implementation
        DetailAST objectBlock = ast.getParent();
        while (objectBlock != null && objectBlock.getType() != TokenTypes.OBJBLOCK) {
            objectBlock = objectBlock.getParent();
        }
        if (objectBlock == null) {
            return;
        }

        AST objectBlockChild = objectBlock.getFirstChild();
        while (objectBlockChild != null) {
            if (objectBlockChild.getType() == TokenTypes.METHOD_DEF) {
                // Now find the method identifier (name)
                AST methodIdentifer = objectBlockChild.getFirstChild();
                while (methodIdentifer != null && methodIdentifer.getType() != TokenTypes.IDENT) {
                    methodIdentifer = methodIdentifer.getNextSibling();
                }
                if (methodIdentifer == null) {
                    return;
                }
                if (methodIdentifer.getText().equals(callingMethodName)) {
                    return; // You're okay
                }
            }
            objectBlockChild = objectBlockChild.getNextSibling();
        }

        this.log(ast, "Only use super to call the base class implementation of a method you override in your current class.");
    }
    */

}
