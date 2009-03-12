package joist.web.pages.controls.form;

import joist.web.AbstractPage;
import joist.web.controls.form.Form;
import joist.web.controls.form.SelectField;
import joist.web.controls.form.SubmitButton;

import org.exigencecorp.bindgen.Bindable;
import org.exigencecorp.util.Copy;

import bindgen.BindKeyword;

@Bindable
public class SelectFieldPage extends AbstractPage {

    public Form form = new Form("form");
    public String value;
    public Boolean flipShowBlank = false;

    public void onInit() {
        SelectField<String> sf = new SelectField<String>(BindKeyword.bind(this).value());
        sf.options(Copy.list("one", "two"));
        if (this.flipShowBlank) {
            sf.showBlank();
        }
        this.form.add(sf);
        this.form.add(new SubmitButton(BindKeyword.bind(this).submit()));
    }

    public void submit() {
    }

}
