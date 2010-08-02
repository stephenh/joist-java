package joist.domain.uow;

public interface BlockWithReturn<T> {

  T go();

}
