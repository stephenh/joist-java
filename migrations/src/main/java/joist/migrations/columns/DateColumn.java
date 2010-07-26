package joist.migrations.columns;

/** Store YYYY-MM-DD. */
public class DateColumn extends AbstractColumn<DateColumn> {

    public DateColumn(String name) {
        super(name, "date");
    }

}
