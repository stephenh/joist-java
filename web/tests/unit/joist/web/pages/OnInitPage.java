package joist.web.pages;

import joist.web.AbstractPage;

public class OnInitPage extends AbstractPage {

    @Override
    public void onInit() {
        this.addModel("init", "foo");
    }

}
