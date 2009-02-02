package click.pages.controls;

import click.AbstractPage;
import click.controls.form.Form;

public class TextFieldPage extends AbstractPage {

    public Form form;
    public String value = "foo";

    public void onInit() {
        this.form = new Form();
    }
}
