package joist.codegen.passes;

public interface Pass<T> {

  void pass(T parameter);

}
