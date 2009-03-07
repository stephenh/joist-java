package click.controls.table;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.Control;
import click.util.HtmlWriter;

public class Table<T> implements Control {

    private String id;
    private String label;
    private List<Column> columns = new ArrayList<Column>();
    private List<T> list = new ArrayList<T>();
    private Binding<? super T> current = null;

    public Table(String id) {
        this.setId(id);
    }

    public String getId() {
        return this.id;
    }

    public void onProcess() {
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setCurrent(Binding<? super T> binding) {
        this.current = binding;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
        column.setTable(this);
    }

    public void render(HtmlWriter w) {
        this.renderHeader(w);
        this.renderRows(w);
        this.renderFooter(w);
    }

    private void renderHeader(HtmlWriter w) {
        w.line("<h3>{}</h3>", this.getLabel());
        w.line("<table id={}>", this.getId());
        w.line("  <thead>");
        w.line("    <tr>");
        for (Column column : this.columns) {
            w.append("      <th id=\"{}.{}\">", this.getId(), column.getName());
            column.renderHeader(w);
            w.line("</th>");
        }
        w.line("    </tr>");
        w.line("  </thead>");
    }

    private void renderRows(HtmlWriter w) {
        w.line("  <tbody>");
        int i = 0;
        for (T object : this.list) {
            this.current.set(object);
            w.line("    <tr>");
            for (Column column : this.columns) {
                w.append("      <td id=\"{}.{}.{}\">", this.getId(), column.getName(), i);
                column.renderRow(w);
                w.line("</td>");
            }
            w.line("    </tr>");
            i++;
        }
        w.line("  </tbody>");
    }

    private void renderFooter(HtmlWriter w) {
        w.line("</table>");
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(String id) {
        this.id = id;
        if (this.label == null) {
            this.setLabel(Inflector.humanize(id));
        }
    }

}
