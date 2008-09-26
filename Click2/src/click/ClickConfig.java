package click;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import click.stages.Stage;
import click.stages.addFieldsToModel.AddFieldsToModelStage;
import click.stages.onInit.OnInitStage;
import click.stages.pageResolution.PageResolutionStage;
import click.stages.processControls.ProcessControlsStage;
import click.stages.render.RenderStage;
import click.stages.setFieldsFromRequest.SetFieldsFromRequestStage;

public class ClickConfig {

    private final List<Stage> stages;

    public ClickConfig() {
        this.stages = Collections.unmodifiableList(this.createStages());
    }

    protected List<Stage> createStages() {
        List<Stage> stages = new ArrayList<Stage>();
        stages.add(new PageResolutionStage());
        stages.add(new SetFieldsFromRequestStage());
        stages.add(new OnInitStage());
        stages.add(new ProcessControlsStage());
        stages.add(new AddFieldsToModelStage());
        stages.add(new RenderStage());
        return stages;
    }

    public List<Stage> getStages() {
        return this.stages;
    }

    public <T extends Stage> T get(Class<T> type) {
        for (Stage stage : this.stages) {
            if (type.isInstance(stage)) {
                return (T) stage;
            }
        }
        throw new IllegalArgumentException("No stage found for type " + type);
    }
}
