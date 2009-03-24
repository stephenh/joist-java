package joist.web;

public interface Page extends Container {

    /** Called immediately after the page is instantiated to get its processor. */
    PageProcessor getProcessor();

    void onInit();

}
