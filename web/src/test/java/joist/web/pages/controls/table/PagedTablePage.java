package joist.web.pages.controls.table;

import java.util.ArrayList;
import java.util.List;

import joist.web.AbstractPage;
import joist.web.controls.table.Table;
import joist.web.controls.table.TextColumn;

import org.bindgen.Bindable;

@Bindable
public class PagedTablePage extends AbstractPage {

  public Table<String> table = new Table<String>("table");
  public String current;
  public Integer page;
  public Integer rows;
  public Boolean emptyTable = false;

  public void onInit() {
    List<String> strings = new ArrayList<String>();
    // one-based, so foo1 through foo100
    if (!this.emptyTable) {
      for (int i = 1; i <= 100; i++) {
        strings.add("foo" + Integer.toString(i));
      }
    }

    PagedTablePageBinding bind = new PagedTablePageBinding(this);
    this.table.setPageNumber(bind.page());
    this.table.setPageRows(bind.rows());
    this.table.setList(strings);
    this.table.setCurrent(bind.current());
    this.table.addColumn(new TextColumn(bind.current()));
  }

}
