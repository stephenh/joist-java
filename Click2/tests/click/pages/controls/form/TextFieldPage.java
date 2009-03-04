package click.pages.controls.form;

import org.exigencecorp.bindgen.Bindable;
import org.exigencecorp.util.TestCounter;

import bindgen.click.pages.controls.form.TextFieldPageBinding;
import click.AbstractPage;
import click.controls.form.Form;
import click.controls.form.SubmitButton;
import click.controls.form.TextField;

@Bindable
public class TextFieldPage extends AbstractPage {

    public static TestCounter submitted = new TestCounter();
    public Form form = new Form("form");
    public String value = "foo";

    public void onInit() {
        TextFieldPageBinding b = new TextFieldPageBinding(this);
        this.form.add(new TextField(b.value()));
        this.form.add(new SubmitButton(b.submit()));
    }

    public void submit() {
        TextFieldPage.submitted.next();
    }

}
