package click.controls.table;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.StringBuilderr;

import click.Control;

public class Table<T> implements Control {

    private String name;
    private List<Column> columns = new ArrayList<Column>();
    private List<T> list = new ArrayList<T>();
    private Binding<? super T> current = null;

    public Table(String name) {
        this.name = name;
    }

    public String getId() {
        return this.name;
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

    public String toString() {
        StringBuilderr sb = new StringBuilderr();
        this.renderHeader(sb);
        this.renderRows(sb);
        this.renderFooter(sb);
        return sb.toString();
    }

    private void renderHeader(StringBuilderr sb) {
        sb.line("<table id={}>", this.getId());
        sb.line("  <thead>");
        sb.line("    <tr>");
        for (Column column : this.columns) {
            column.renderHeader(sb);
        }
        sb.line("    </tr>");
        sb.line("  </thead>");
    }

    private void renderRows(StringBuilderr sb) {
        sb.line("  <tbody>");
        int i = 0;
        for (T object : this.list) {
            this.current.set(object);
            sb.line("    <tr>");
            for (Column column : this.columns) {
                sb.append("      <td id=\"{}.{}.{}\">", this.getId(), column.getName(), i);
                column.renderRow(sb);
                sb.line("</td>");
            }
            sb.line("    </tr>");
            i++;
        }
        sb.line("  </tbody>");
    }

    private void renderFooter(StringBuilderr sb) {
        sb.line("</table>");
    }

}
