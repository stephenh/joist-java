package click;

public interface Page extends Container {

    /** Called immediately after the page is instantiated to get its processor. */
    PageProcessor getProcessor();

    void onInit();

    boolean onRender();

}
