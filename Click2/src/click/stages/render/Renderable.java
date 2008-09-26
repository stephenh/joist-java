package click.stages.render;

import java.util.Map;

public interface Renderable {

    boolean onRender();

    Map<String, Object> getModel();

    void addModel(String key, Object value);

    String getTemplate();

}
