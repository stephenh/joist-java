package click.pages.stages.onInit;

import click.AbstractPage;

public class OnInitPage extends AbstractPage {

    @Override
    public void onInit() {
        this.addModel("init", "foo");
    }

}
