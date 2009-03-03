package org.exigencecorp.domainobjects.uow;

public interface BlockWithReturnAndSafety<T> {

    T go();

    T stopped(Exception e);

}
