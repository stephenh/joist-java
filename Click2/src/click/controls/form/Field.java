package click.controls.form;

public interface Field {

    String getName();

    String getLabel();

    void bindRequestValue(String value);

}
