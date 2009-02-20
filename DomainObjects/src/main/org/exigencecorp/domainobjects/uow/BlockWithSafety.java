package org.exigencecorp.domainobjects.uow;

public interface BlockWithSafety {

    void go() throws Exception;

    void stopped(Exception e);

}
