package joist.web.controls.table;

import java.util.ArrayList;
import java.util.List;

import joist.util.Inflector;
import joist.web.AbstractControl;
import joist.web.controls.PageLink;
import joist.web.util.HtmlWriter;

import org.apache.commons.lang.StringUtils;
import org.bindgen.Binding;

// Should extend AbstractContainer?
public class Table<T> extends AbstractControl<Table<T>> {

  private String label;
  private List<Column> columns = new ArrayList<Column>();
  private List<T> list = new ArrayList<T>();
  private Binding<? super T> current = null;
  // Only set if doing paging
  private Binding<? extends Number> pageNumber;
  private Binding<? extends Number> pageRows;
  private Binding<String> sortKey;

  public Table(String id) {
    this.id(id);
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public void setCurrent(Binding<? super T> binding) {
    this.current = binding;
  }

  public void addColumn(Column column) {
    this.columns.add(column);
    column.setParent(this);
  }

  @Override
  public void render(HtmlWriter w) {
    this.renderHeader(w);
    this.renderRows(w);
    this.renderFooter(w);
    this.renderPagingLinks(w);
  }

  public Table<T> id(String id) {
    this.setId(id);
    this.setLabel(Inflector.humanize(StringUtils.removeEnd(id, "Table")));
    return this;
  }

  public Table<T> label(String label) {
    this.setLabel(label);
    return this;
  }

  private void renderHeader(HtmlWriter w) {
    w.line("<h3>{}</h3>", this.getLabel());
    w.line("<table id={}{}>", this.getId(), this.attributes);
    w.line("  <thead>");
    w.line("    <tr>");
    for (Column column : this.columns) {
      w.append("      <th id={}>", column.getFullId());
      column.renderHeader(w);
      w.line("</th>");
    }
    w.line("    </tr>");
    w.line("  </thead>");
  }

  private void renderRows(HtmlWriter w) {
    if (this.current == null) {
      throw new RuntimeException("The current binding is not set");
    }
    w.line("  <tbody>");
    int i = 0;
    for (T object : this.getRowsToRender()) {
      this.current.set(object);
      w.line("    <tr>");
      for (Column column : this.columns) {
        column.setCurrentRowIndex(i);
        w.append("      <td id={}>", column.getFullId());
        column.render(w);
        w.line("</td>");
      }
      w.line("    </tr>");
      i++;
    }
    w.line("  </tbody>");
  }

  protected List<T> getRowsToRender() {
    if (!this.isPaging()) {
      return this.list;
    }
    int beginIndex = (this.getCurrentPageNumber() - 1) * this.getCurrentPageRows();
    int endIndex = beginIndex + this.getCurrentPageRows();
    // Hack to keep indices within the range of size
    endIndex = (endIndex > this.list.size()) ? this.list.size() : endIndex;
    return this.list.subList(beginIndex, endIndex);
  }

  private void renderFooter(HtmlWriter w) {
    w.line("</table>");
  }

  private void renderPagingLinks(HtmlWriter w) {
    if (!this.isPaging()) {
      return;
    }
    boolean showPrevious = this.getCurrentPageNumber() > 1;
    if (showPrevious) {
      PageLink previous = PageLink.forCurrentPage();
      previous.setParent(this);
      previous.id("previous");
      previous.param(this.pageNumber.getName(), this.getCurrentPageNumber() - 1);
      previous.text("previous");
      previous.render(w);
    }
    boolean showNext = this.getCurrentPageNumber() < this.getMaxPageNumber();
    if (showNext) {
      PageLink next = PageLink.forCurrentPage();
      next.setParent(this);
      next.id("next");
      next.param(this.pageNumber.getName(), this.getCurrentPageNumber() + 1);
      next.text("next");
      next.render(w);
    }
  }

  public boolean isPaging() {
    return this.pageNumber != null && this.pageRows != null;
  }

  public int getCurrentPageNumber() {
    int pageNumber = this.pageNumber.get() != null ? this.pageNumber.get().intValue() : 1;
    if (pageNumber > this.getMaxPageNumber()) {
      pageNumber = this.getMaxPageNumber();
    }
    return pageNumber;
  }

  public int getCurrentPageRows() {
    return this.pageRows.get() != null ? this.pageRows.get().intValue() : 10;
  }

  public int getMaxPageNumber() {
    // 1 2 3 4 5 ( 6 7 8 9 10 ) 11, pageRows = 5, count = 11, so page <= 3
    int maxNumber = this.list.size() / this.getCurrentPageRows();
    if (this.list.size() % this.getCurrentPageRows() > 0) {
      maxNumber++;
    }
    if (maxNumber == 0) {
      maxNumber = 1;
    }
    return maxNumber;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Binding<? extends Number> getPageNumber() {
    return this.pageNumber;
  }

  public void setPageNumber(Binding<? extends Number> pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Binding<? extends Number> getPageRows() {
    return this.pageRows;
  }

  public void setPageRows(Binding<? extends Number> pageRows) {
    this.pageRows = pageRows;
  }

  protected Table<T> getThis() {
    return this;
  }

  public Binding<String> getSortKey() {
    return this.sortKey;
  }

  public void setSortKey(Binding<String> sortKey) {
    this.sortKey = sortKey;
  }

}
