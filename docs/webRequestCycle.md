---
layout: default
title: joist-web Request Cycle
---

Request Cycle
=============

Overview
--------

joist-web follows a simple request cycle that is driven by the [`PageProcessor`](http://github.com/stephenh/joist/blob/master/web/src/main/joist/web/PageProcessor.java) interface.

Each `Page`, but implementing the `Page` interface, has a `getProcessor()` method that allows the page to choose its own page processor and enable per-page processing logic.

This keeps the [`WebServlet`](http://github.com/stephenh/joist/blob/master/web/src/main/joist/web/WebServlet.java) itself quite small, at 50 lines, as it merely jump-start the cycle and hand off to the processor.

Default Page Processor
----------------------

Pages use the [`DefaultPageProcessor`](http://github.com/stephenh/joist/blob/master/web/src/main/joist/web/DefaultPageProcessor.java) unless otherwise overridden. The flow of the default page processor is:

<pre name="code" class="java">
    @Override
    public void process(Page page) {
        try {
            this.doSetPageFieldsFromRequest(page); // takes ?foo=1 and binds it to Page.foo
            this.doOnInit(page);                   // calls page.onInit() for the page to initialize its controls
            this.doAddOrphanControlsToPage(page);  // see orphan controls below
            this.doProcess(page);                  // calls page.onProcess() for controls to respond to user input/form submissions
            this.doOnRender(page);                 // calls page.onRender() for page logic that whats to run right before rendering
        } catch (RedirectException re) {
            this.doRedirect(re);                   // catches any redirects caused by page logic throwing RedirectExceptions
            return;
        } catch (RenderException re) {
            this.doRender(page, re);               // handles pre-emptive rendering caused by page logic throwing RenderExceptions
            return;
        }
        this.doAddAllControlsToModel(page);        // fills the model with controls
        this.doAddFieldsToModel(page);             // fills the model with Page.xxx public fields
        this.doAddFlashToModel(page);              // fills the model with any Flash content
        this.doRender(page);                       // renders, usually with Velocity
        this.doResetFlash(page);                   // resets the flash after a successful render
    }
</pre>

Orphan Controls
---------------

Any control which subclasses `AbstractControl` is tracked as instantiated on the local thread. If, by `doAddOrphanControlsToPage`, a control has no parent set, the control is added to the page. This auto-wires any unowned controls to the page, so that you don't have to repeatedly: `c = new Control(); page.addControl(c)`--the `addControl` call is taken care of.

