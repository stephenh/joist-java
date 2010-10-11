package joist.domain.uow;

public interface BlockWithSafety {

  void go() throws Exception;

  void stopped(Exception e);

}
