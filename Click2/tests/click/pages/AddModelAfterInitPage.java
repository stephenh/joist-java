package click.pages;

import click.AbstractPage;

public class AddModelAfterInitPage extends AbstractPage {

    public String value;

    @Override
    public void onInit() {
        this.value = "init";
    }

}
