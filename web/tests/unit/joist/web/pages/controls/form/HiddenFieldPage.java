package joist.web.pages.controls.form;

import joist.web.AbstractPage;
import joist.web.controls.form.Form;
import joist.web.controls.form.HiddenField;
import joist.web.controls.form.SubmitButton;

import org.exigencecorp.bindgen.Bindable;

import bindgen.BindKeyword;

@Bindable
public class HiddenFieldPage extends AbstractPage {

    public Form form = new Form("form");
    public Integer integer;

    public void onInit() {
        this.form.add(new HiddenField(BindKeyword.bind(this).integer()));
        this.form.add(new SubmitButton(BindKeyword.bind(this).submit()));
    }

    public void submit() {
    }

}
