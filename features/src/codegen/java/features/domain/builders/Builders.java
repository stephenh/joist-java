package features.domain.builders;

import features.domain.Child;
import features.domain.GrandChild;
import features.domain.Parent;
import features.domain.Primitives;

public class Builders {

  public static ChildBuilder aChild() {
    return new ChildBuilder(new Child());
  }

  public static ChildBuilder existing(Child child) {
    return new ChildBuilder(child);
  }

  public static ChildBuilder theChild(long id) {
    return new ChildBuilder(Child.queries.find(id).get());
  }

  public static ChildBuilder theChild(int id) {
    return new ChildBuilder(Child.queries.find((long) id).get());
  }

  public static GrandChildBuilder aGrandChild() {
    return new GrandChildBuilder(new GrandChild());
  }

  public static GrandChildBuilder existing(GrandChild grandChild) {
    return new GrandChildBuilder(grandChild);
  }

  public static GrandChildBuilder theGrandChild(long id) {
    return new GrandChildBuilder(GrandChild.queries.find(id).get());
  }

  public static GrandChildBuilder theGrandChild(int id) {
    return new GrandChildBuilder(GrandChild.queries.find((long) id).get());
  }

  public static ParentBuilder aParent() {
    return new ParentBuilder(new Parent());
  }

  public static ParentBuilder existing(Parent parent) {
    return new ParentBuilder(parent);
  }

  public static ParentBuilder theParent(long id) {
    return new ParentBuilder(Parent.queries.find(id).get());
  }

  public static ParentBuilder theParent(int id) {
    return new ParentBuilder(Parent.queries.find((long) id).get());
  }

  public static PrimitivesBuilder aPrimitives() {
    return new PrimitivesBuilder(new Primitives());
  }

  public static PrimitivesBuilder existing(Primitives primitives) {
    return new PrimitivesBuilder(primitives);
  }

  public static PrimitivesBuilder thePrimitives(long id) {
    return new PrimitivesBuilder(Primitives.queries.find(id).get());
  }

  public static PrimitivesBuilder thePrimitives(int id) {
    return new PrimitivesBuilder(Primitives.queries.find((long) id).get());
  }

}
