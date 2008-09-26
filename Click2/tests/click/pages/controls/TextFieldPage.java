package click.pages.controls;

import click.AbstractPage;
import click.controls.form.Form;
import click.controls.form.TextField;

public class TextFieldPage extends AbstractPage {

    public Form form;
    public String value = "foo";

    public void onInit() {
        this.form = new Form();
        this.form.add(new TextField("Value", "value"));
    }

}
