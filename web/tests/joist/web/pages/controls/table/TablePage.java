package joist.web.pages.controls.table;

import java.util.ArrayList;
import java.util.List;

import joist.web.AbstractPage;
import joist.web.controls.table.Table;
import joist.web.controls.table.TextColumn;

import org.exigencecorp.bindgen.Bindable;

import bindgen.joist.web.pages.controls.table.TablePageBinding;

@Bindable
public class TablePage extends AbstractPage {

    public Table<String> table = new Table<String>("table");
    public String current;

    public void onInit() {
        List<String> strings = new ArrayList<String>();
        strings.add("One");
        strings.add("Two");

        TablePageBinding bind = new TablePageBinding(this);
        this.table.setList(strings);
        this.table.setCurrent(bind.current());
        this.table.addColumn(new TextColumn(bind.current().string()));
        this.table.addColumn(new TextColumn(bind.current().lowerCase()));
    }

}
