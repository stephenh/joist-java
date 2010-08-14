package joist.perf.hibernate;

import java.util.Set;
import java.util.TreeSet;

public class Child {

    private Integer id;
    private String name;
    private Integer version;
    private Parent parent;
    private Set<GrandChild> grandchilds = new TreeSet<GrandChild>();

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<GrandChild> getGrandchilds() {
        return this.grandchilds;
    }

    public void setGrandchilds(Set<GrandChild> grandchilds) {
        this.grandchilds = grandchilds;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parent getParent() {
        return this.parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

}
