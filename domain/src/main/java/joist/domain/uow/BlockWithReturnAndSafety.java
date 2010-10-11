package joist.domain.uow;

public interface BlockWithReturnAndSafety<T> {

  T go();

  T stopped(Exception e);

}
