package click.pages.stages.addFieldsToModel;

import click.AbstractPage;

public class AddAfterInitPage extends AbstractPage {

    public String value;

    @Override
    public void onInit() {
        this.value = "init";
    }

}
