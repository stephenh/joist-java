package features.domain.builders;

import features.domain.CodeAColor;
import features.domain.CodeADomainObject;
import features.domain.CodeASize;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class CodeADomainObjectBuilderCodegen extends AbstractBuilder<CodeADomainObject> {

  public CodeADomainObjectBuilderCodegen(CodeADomainObject instance) {
    super(instance);
  }

  public Long id() {
    return get().getId();
  }

  public CodeADomainObjectBuilder id(Long id) {
    get().setId(id);
    return (CodeADomainObjectBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public CodeADomainObjectBuilder name(String name) {
    get().setName(name);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeADomainObjectBuilder with(String name) {
    return name(name);
  }

  @Override
  public CodeADomainObjectBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    if (codeAColor() == null) {
      codeAColor(CodeAColor.BLUE);
    }
    if (codeASize() == null) {
      codeASize(CodeASize.ONE);
    }
    return (CodeADomainObjectBuilder) super.defaults();
  }

  public CodeAColor codeAColor() {
    return get().getCodeAColor();
  }

  public CodeADomainObjectBuilder codeAColor(CodeAColor codeAColor) {
    get().setCodeAColor(codeAColor);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeADomainObjectBuilder with(CodeAColor codeAColor) {
    return codeAColor(codeAColor);
  }

  public CodeADomainObjectBuilder blue() {
    return codeAColor(CodeAColor.BLUE);
  }

  public boolean isBlue() {
    return get().isBlue();
  }

  public CodeADomainObjectBuilder green() {
    return codeAColor(CodeAColor.GREEN);
  }

  public boolean isGreen() {
    return get().isGreen();
  }

  public CodeASize codeASize() {
    return get().getCodeASize();
  }

  public CodeADomainObjectBuilder codeASize(CodeASize codeASize) {
    get().setCodeASize(codeASize);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeADomainObjectBuilder with(CodeASize codeASize) {
    return codeASize(codeASize);
  }

  public CodeADomainObjectBuilder one() {
    return codeASize(CodeASize.ONE);
  }

  public boolean isOne() {
    return get().isOne();
  }

  public CodeADomainObjectBuilder two() {
    return codeASize(CodeASize.TWO);
  }

  public boolean isTwo() {
    return get().isTwo();
  }

  public CodeADomainObject get() {
    return (features.domain.CodeADomainObject) super.get();
  }

}
