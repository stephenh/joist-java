package joist.perf;

import joist.domain.util.AbstractPgWithc3p0DataSourceFactory;

public class MyDataSourceFactory extends AbstractPgWithc3p0DataSourceFactory {
    public MyDataSourceFactory() {
        super("features");
    }
}
