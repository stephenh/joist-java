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
    get().setName(name);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeAColor codeAColor() {
    return get().getCodeAColor();
  }

  public CodeADomainObjectBuilder codeAColor(CodeAColor codeAColor) {
    get().setCodeAColor(codeAColor);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeADomainObjectBuilder with(CodeAColor codeAColor) {
    get().setCodeAColor(codeAColor);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeASize codeASize() {
    return get().getCodeASize();
  }

  public CodeADomainObjectBuilder codeASize(CodeASize codeASize) {
    get().setCodeASize(codeASize);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeADomainObjectBuilder with(CodeASize codeASize) {
    get().setCodeASize(codeASize);
    return (CodeADomainObjectBuilder) this;
  }

  public CodeADomainObject get() {
    return (features.domain.CodeADomainObject) super.get();
  }

}
