package org.exigencecorp.domainobjects.uow;

public interface BlockWithReturnAndSafety<T> {

    T go();

    void stopped(Exception e);

}
