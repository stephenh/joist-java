package click.pages;

import click.AbstractPage;

public class RenderHelloUserPage extends AbstractPage {
    @Override
    public boolean onRender() {
        this.addModel("username", "bob");
        return true;
    }

}
