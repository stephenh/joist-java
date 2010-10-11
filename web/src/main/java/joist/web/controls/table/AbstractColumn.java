package joist.web.controls.table;

import joist.util.Inflector;
import joist.web.AbstractControl;
import joist.web.controls.PageLink;
import joist.web.util.HtmlWriter;

import org.bindgen.Binding;

public abstract class AbstractColumn<T extends AbstractColumn<T>> extends AbstractControl<T> implements Column {

  private String label;
  private String sortKey;
  private Integer currentRowIndex;

  protected AbstractColumn(String id) {
    this.id(id);
  }

  // http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#What is the getThis trick?
  protected abstract T getThis();

  public T id(String id) {
    this.setId(id);
    this.setLabel(Inflector.humanize(id));
    return this.getThis();
  }

  public T sortable(String key) {
    this.sortKey = key;
    return this.getThis();
  }

  public void renderHeader(HtmlWriter sb) {
    if (this.getLabel() != null) {
      Binding<String> sortKeyBinding = this.getParent() == null ? null : ((Table<?>) this.getParent()).getSortKey();
      if (sortKeyBinding == null || this.sortKey == null) {
        sb.append(this.getLabel());
      } else {
        // sortKey should be "foo", binding would be "afoo" or "dfoo"
        String currentKey = sortKeyBinding.get();
        boolean currentlySelected = currentKey != null && currentKey.substring(1).equals(this.sortKey);
        String currentOrder = currentKey.substring(0, 1);
        String oppositeOrder = "a".equals(currentOrder) ? "d" : "a";
        String nextKey = currentlySelected ? oppositeOrder + this.sortKey : currentOrder + this.sortKey;

        PageLink currentPage = PageLink.forCurrentPage();
        currentPage.id(this.getFullId() + "-link");
        currentPage.param(sortKeyBinding.getName(), nextKey);
        currentPage.text(this.getLabel());
        sb.append("{}", currentPage);
      }
    }
  }

  public String getFullId() {
    String prefix = this.getParent() == null ? "" : this.getParent().getId() + "-";
    String suffix = this.currentRowIndex == null ? "" : "-" + this.currentRowIndex;
    return prefix + this.getId() + suffix;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Integer getCurrentRowIndex() {
    return this.currentRowIndex;
  }

  public void setCurrentRowIndex(Integer currentRowIndex) {
    this.currentRowIndex = currentRowIndex;
  }

}
