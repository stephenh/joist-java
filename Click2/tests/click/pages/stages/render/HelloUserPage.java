package click.pages.stages.render;

import click.AbstractPage;

public class HelloUserPage extends AbstractPage {
    @Override
    public boolean onRender() {
        this.addModel("username", "bob");
        return true;
    }

}
