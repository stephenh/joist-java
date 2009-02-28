package click.pages;

import click.AbstractPage;

public class OnInitPage extends AbstractPage {

    @Override
    public void onInit() {
        this.addModel("init", "foo");
    }

}
