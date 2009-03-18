package joist.web.pages;

import joist.web.AbstractPage;

public class RenderHelloUserPage extends AbstractPage {
    @Override
    public boolean onRender() {
        this.addModel("username", "bob");
        return true;
    }

}
