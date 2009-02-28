package click.pages.controls.table;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.bindgen.Bindable;

import bindgen.click.pages.controls.table.TablePageBinding;
import click.AbstractPage;
import click.controls.table.Table;
import click.controls.table.TextColumn;

@Bindable
public class TablePage extends AbstractPage {

    public Table table = new Table("table");
    public String value = "foo";
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
