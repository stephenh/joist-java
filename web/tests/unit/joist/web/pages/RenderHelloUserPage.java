package joist.web.pages;

import joist.web.AbstractPage;

public class RenderHelloUserPage extends AbstractPage {
    @Override
    public void onInit() {
        this.addModel("username", "bob");
    }

}
