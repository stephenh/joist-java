package click.pages.controls.form;

import org.exigencecorp.bindgen.Bindable;

import bindgen.click.pages.controls.form.TwoFormsPageBinding;
import click.AbstractPage;
import click.controls.form.Form;
import click.controls.form.TextField;

@Bindable
public class TwoFormsPage extends AbstractPage {

    public Form form1 = new Form("form1");
    public Form form2 = new Form("form2");
    public String value1 = "foo";
    public String value2 = "foo";

    public void onInit() {
        TwoFormsPageBinding bind = new TwoFormsPageBinding(this);
        this.form1.add(new TextField("Value", bind.value1()));
        this.form2.add(new TextField("Value", bind.value2()));
    }

}
