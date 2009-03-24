package joist.web.pages;

import static joist.web.ClickKeywords.addModel;
import joist.web.AbstractPage;

public class RenderHelloUserPage extends AbstractPage {

    @Override
    public void onInit() {
        addModel("username", "bob");
    }

}
