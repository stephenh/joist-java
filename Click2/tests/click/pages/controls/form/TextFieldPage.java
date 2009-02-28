package click.pages.controls.form;

import org.exigencecorp.bindgen.Bindable;

import bindgen.click.pages.controls.form.TextFieldPageBinding;
import click.AbstractPage;
import click.controls.form.Form;
import click.controls.form.TextField;

@Bindable
public class TextFieldPage extends AbstractPage {

    public Form form = new Form();
    public String value = "foo";

    public void onInit() {
        TextFieldPageBinding b = new TextFieldPageBinding(this);
        this.form.add(new TextField(b.value()));
    }

}
