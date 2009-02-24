package click;

/** Base--see stage-specific interfaces like {@link click.stages.onInit.Initable}. */
public interface Page extends Container {

    /** Called immediately after the page is instantiated to get its processor. */
    PageProcessor getProcessor();

    void onInit();

    boolean onRender();

}
