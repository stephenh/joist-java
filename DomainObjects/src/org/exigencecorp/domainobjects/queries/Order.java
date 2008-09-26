package org.exigencecorp.domainobjects.queries;

public class Order {

    private final String text;

    public Order(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }

}
