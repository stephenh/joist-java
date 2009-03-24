package joist.web;

public abstract class AbstractControl implements Control {

    private String id;
    private Control parent;

    protected AbstractControl() {
        CurrentContext.addControl(this);
    }

    public void onProcess() {
    }

    public String getFullId() {
        if (this.getParent() != null && !(this.getParent() instanceof Page)) {
            return this.getParent().getId() + "." + this.getId();
        } else {
            return this.getId();
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Control getParent() {
        return this.parent;
    }

    public void setParent(Control parent) {
        this.parent = parent;
    }

}
