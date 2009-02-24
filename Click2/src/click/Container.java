package click;

import java.util.List;

public interface Container {

    void addControl(Control control);

    List<Control> getControls();

}
