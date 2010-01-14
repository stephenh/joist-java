package joist.domain.util;

public class Pgc3p0Factory extends AbstractC3p0Factory {

    protected Pgc3p0Factory(ConnectionSettings settings) {
        super(settings, "postgresql");
    }

}
