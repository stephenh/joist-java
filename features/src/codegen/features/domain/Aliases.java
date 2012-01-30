package features.domain;

import joist.domain.orm.AliasRegistry;

public class Aliases {

  private static ChildAlias child;
  private static CodeADomainObjectAlias codeADomainObject;
  private static GrandChildAlias grandChild;
  private static HistoryEntryAlias historyEntry;
  private static InheritanceABaseAlias inheritanceABase;
  private static InheritanceAOwnerAlias inheritanceAOwner;
  private static InheritanceASubOneAlias inheritanceASubOne;
  private static InheritanceASubTwoAlias inheritanceASubTwo;
  private static InheritanceAThingAlias inheritanceAThing;
  private static InheritanceBBottomAlias inheritanceBBottom;
  private static InheritanceBMiddleAlias inheritanceBMiddle;
  private static InheritanceBRootAlias inheritanceBRoot;
  private static InheritanceBRootChildAlias inheritanceBRootChild;
  private static InheritanceCAlias inheritanceC;
  private static InheritanceCFoo1Alias inheritanceCFoo1;
  private static InheritanceCFoo2Alias inheritanceCFoo2;
  private static ManyToManyABarAlias manyToManyABar;
  private static ManyToManyAFooAlias manyToManyAFoo;
  private static ManyToManyAFooToBarAlias manyToManyAFooToBar;
  private static ManyToManyBBarAlias manyToManyBBar;
  private static ManyToManyBFooAlias manyToManyBFoo;
  private static ManyToManyBFooToBarAlias manyToManyBFooToBar;
  private static OneToOneABarAlias oneToOneABar;
  private static OneToOneAFooAlias oneToOneAFoo;
  private static OneToOneBBarAlias oneToOneBBar;
  private static OneToOneBFooAlias oneToOneBFoo;
  private static ParentAlias parent;
  private static ParentBChildBarAlias parentBChildBar;
  private static ParentBChildFooAlias parentBChildFoo;
  private static ParentBParentAlias parentBParent;
  private static ParentCBarAlias parentCBar;
  private static ParentCFooAlias parentCFoo;
  private static ParentDAlias parentD;
  private static ParentDChildAAlias parentDChildA;
  private static ParentDChildBAlias parentDChildB;
  private static ParentDChildCAlias parentDChildC;
  private static ParentDToChildCAlias parentDToChildC;
  private static PrimitivesAlias primitives;
  private static PrimitivesBAlias primitivesB;
  private static PrimitivesCAlias primitivesC;
  private static UserTypesAFooAlias userTypesAFoo;
  private static ValidationAFooAlias validationAFoo;

  public static void init() {
  }

  public static ChildAlias child() {
    if (child == null) {
      child = new ChildAlias("c0");
      AliasRegistry.register(Child.class, child);
    }
    return child;
  }

  public static CodeADomainObjectAlias codeADomainObject() {
    if (codeADomainObject == null) {
      codeADomainObject = new CodeADomainObjectAlias("cado0");
      AliasRegistry.register(CodeADomainObject.class, codeADomainObject);
    }
    return codeADomainObject;
  }

  public static GrandChildAlias grandChild() {
    if (grandChild == null) {
      grandChild = new GrandChildAlias("gc0");
      AliasRegistry.register(GrandChild.class, grandChild);
    }
    return grandChild;
  }

  public static HistoryEntryAlias historyEntry() {
    if (historyEntry == null) {
      historyEntry = new HistoryEntryAlias("he0");
      AliasRegistry.register(HistoryEntry.class, historyEntry);
    }
    return historyEntry;
  }

  public static InheritanceABaseAlias inheritanceABase() {
    if (inheritanceABase == null) {
      inheritanceABase = new InheritanceABaseAlias("iab0");
      AliasRegistry.register(InheritanceABase.class, inheritanceABase);
    }
    return inheritanceABase;
  }

  public static InheritanceAOwnerAlias inheritanceAOwner() {
    if (inheritanceAOwner == null) {
      inheritanceAOwner = new InheritanceAOwnerAlias("iao0");
      AliasRegistry.register(InheritanceAOwner.class, inheritanceAOwner);
    }
    return inheritanceAOwner;
  }

  public static InheritanceASubOneAlias inheritanceASubOne() {
    if (inheritanceASubOne == null) {
      inheritanceASubOne = new InheritanceASubOneAlias("iaso0");
      AliasRegistry.register(InheritanceASubOne.class, inheritanceASubOne);
    }
    return inheritanceASubOne;
  }

  public static InheritanceASubTwoAlias inheritanceASubTwo() {
    if (inheritanceASubTwo == null) {
      inheritanceASubTwo = new InheritanceASubTwoAlias("iast0");
      AliasRegistry.register(InheritanceASubTwo.class, inheritanceASubTwo);
    }
    return inheritanceASubTwo;
  }

  public static InheritanceAThingAlias inheritanceAThing() {
    if (inheritanceAThing == null) {
      inheritanceAThing = new InheritanceAThingAlias("iat0");
      AliasRegistry.register(InheritanceAThing.class, inheritanceAThing);
    }
    return inheritanceAThing;
  }

  public static InheritanceBBottomAlias inheritanceBBottom() {
    if (inheritanceBBottom == null) {
      inheritanceBBottom = new InheritanceBBottomAlias("ibb0");
      AliasRegistry.register(InheritanceBBottom.class, inheritanceBBottom);
    }
    return inheritanceBBottom;
  }

  public static InheritanceBMiddleAlias inheritanceBMiddle() {
    if (inheritanceBMiddle == null) {
      inheritanceBMiddle = new InheritanceBMiddleAlias("ibm0");
      AliasRegistry.register(InheritanceBMiddle.class, inheritanceBMiddle);
    }
    return inheritanceBMiddle;
  }

  public static InheritanceBRootAlias inheritanceBRoot() {
    if (inheritanceBRoot == null) {
      inheritanceBRoot = new InheritanceBRootAlias("ibr0");
      AliasRegistry.register(InheritanceBRoot.class, inheritanceBRoot);
    }
    return inheritanceBRoot;
  }

  public static InheritanceBRootChildAlias inheritanceBRootChild() {
    if (inheritanceBRootChild == null) {
      inheritanceBRootChild = new InheritanceBRootChildAlias("ibrc0");
      AliasRegistry.register(InheritanceBRootChild.class, inheritanceBRootChild);
    }
    return inheritanceBRootChild;
  }

  public static InheritanceCAlias inheritanceC() {
    if (inheritanceC == null) {
      inheritanceC = new InheritanceCAlias("ic0");
      AliasRegistry.register(InheritanceC.class, inheritanceC);
    }
    return inheritanceC;
  }

  public static InheritanceCFoo1Alias inheritanceCFoo1() {
    if (inheritanceCFoo1 == null) {
      inheritanceCFoo1 = new InheritanceCFoo1Alias("icf0");
      AliasRegistry.register(InheritanceCFoo1.class, inheritanceCFoo1);
    }
    return inheritanceCFoo1;
  }

  public static InheritanceCFoo2Alias inheritanceCFoo2() {
    if (inheritanceCFoo2 == null) {
      inheritanceCFoo2 = new InheritanceCFoo2Alias("icf0");
      AliasRegistry.register(InheritanceCFoo2.class, inheritanceCFoo2);
    }
    return inheritanceCFoo2;
  }

  public static ManyToManyABarAlias manyToManyABar() {
    if (manyToManyABar == null) {
      manyToManyABar = new ManyToManyABarAlias("mtmab0");
      AliasRegistry.register(ManyToManyABar.class, manyToManyABar);
    }
    return manyToManyABar;
  }

  public static ManyToManyAFooAlias manyToManyAFoo() {
    if (manyToManyAFoo == null) {
      manyToManyAFoo = new ManyToManyAFooAlias("mtmaf0");
      AliasRegistry.register(ManyToManyAFoo.class, manyToManyAFoo);
    }
    return manyToManyAFoo;
  }

  public static ManyToManyAFooToBarAlias manyToManyAFooToBar() {
    if (manyToManyAFooToBar == null) {
      manyToManyAFooToBar = new ManyToManyAFooToBarAlias("mtmaftb0");
      AliasRegistry.register(ManyToManyAFooToBar.class, manyToManyAFooToBar);
    }
    return manyToManyAFooToBar;
  }

  public static ManyToManyBBarAlias manyToManyBBar() {
    if (manyToManyBBar == null) {
      manyToManyBBar = new ManyToManyBBarAlias("mtmbb0");
      AliasRegistry.register(ManyToManyBBar.class, manyToManyBBar);
    }
    return manyToManyBBar;
  }

  public static ManyToManyBFooAlias manyToManyBFoo() {
    if (manyToManyBFoo == null) {
      manyToManyBFoo = new ManyToManyBFooAlias("mtmbf0");
      AliasRegistry.register(ManyToManyBFoo.class, manyToManyBFoo);
    }
    return manyToManyBFoo;
  }

  public static ManyToManyBFooToBarAlias manyToManyBFooToBar() {
    if (manyToManyBFooToBar == null) {
      manyToManyBFooToBar = new ManyToManyBFooToBarAlias("mtmbftb0");
      AliasRegistry.register(ManyToManyBFooToBar.class, manyToManyBFooToBar);
    }
    return manyToManyBFooToBar;
  }

  public static OneToOneABarAlias oneToOneABar() {
    if (oneToOneABar == null) {
      oneToOneABar = new OneToOneABarAlias("otoab0");
      AliasRegistry.register(OneToOneABar.class, oneToOneABar);
    }
    return oneToOneABar;
  }

  public static OneToOneAFooAlias oneToOneAFoo() {
    if (oneToOneAFoo == null) {
      oneToOneAFoo = new OneToOneAFooAlias("otoaf0");
      AliasRegistry.register(OneToOneAFoo.class, oneToOneAFoo);
    }
    return oneToOneAFoo;
  }

  public static OneToOneBBarAlias oneToOneBBar() {
    if (oneToOneBBar == null) {
      oneToOneBBar = new OneToOneBBarAlias("otobb0");
      AliasRegistry.register(OneToOneBBar.class, oneToOneBBar);
    }
    return oneToOneBBar;
  }

  public static OneToOneBFooAlias oneToOneBFoo() {
    if (oneToOneBFoo == null) {
      oneToOneBFoo = new OneToOneBFooAlias("otobf0");
      AliasRegistry.register(OneToOneBFoo.class, oneToOneBFoo);
    }
    return oneToOneBFoo;
  }

  public static ParentAlias parent() {
    if (parent == null) {
      parent = new ParentAlias("p0");
      AliasRegistry.register(Parent.class, parent);
    }
    return parent;
  }

  public static ParentBChildBarAlias parentBChildBar() {
    if (parentBChildBar == null) {
      parentBChildBar = new ParentBChildBarAlias("pbcb0");
      AliasRegistry.register(ParentBChildBar.class, parentBChildBar);
    }
    return parentBChildBar;
  }

  public static ParentBChildFooAlias parentBChildFoo() {
    if (parentBChildFoo == null) {
      parentBChildFoo = new ParentBChildFooAlias("pbcf0");
      AliasRegistry.register(ParentBChildFoo.class, parentBChildFoo);
    }
    return parentBChildFoo;
  }

  public static ParentBParentAlias parentBParent() {
    if (parentBParent == null) {
      parentBParent = new ParentBParentAlias("pbp0");
      AliasRegistry.register(ParentBParent.class, parentBParent);
    }
    return parentBParent;
  }

  public static ParentCBarAlias parentCBar() {
    if (parentCBar == null) {
      parentCBar = new ParentCBarAlias("pcb0");
      AliasRegistry.register(ParentCBar.class, parentCBar);
    }
    return parentCBar;
  }

  public static ParentCFooAlias parentCFoo() {
    if (parentCFoo == null) {
      parentCFoo = new ParentCFooAlias("pcf0");
      AliasRegistry.register(ParentCFoo.class, parentCFoo);
    }
    return parentCFoo;
  }

  public static ParentDAlias parentD() {
    if (parentD == null) {
      parentD = new ParentDAlias("pd0");
      AliasRegistry.register(ParentD.class, parentD);
    }
    return parentD;
  }

  public static ParentDChildAAlias parentDChildA() {
    if (parentDChildA == null) {
      parentDChildA = new ParentDChildAAlias("pdca0");
      AliasRegistry.register(ParentDChildA.class, parentDChildA);
    }
    return parentDChildA;
  }

  public static ParentDChildBAlias parentDChildB() {
    if (parentDChildB == null) {
      parentDChildB = new ParentDChildBAlias("pdcb0");
      AliasRegistry.register(ParentDChildB.class, parentDChildB);
    }
    return parentDChildB;
  }

  public static ParentDChildCAlias parentDChildC() {
    if (parentDChildC == null) {
      parentDChildC = new ParentDChildCAlias("pdcc0");
      AliasRegistry.register(ParentDChildC.class, parentDChildC);
    }
    return parentDChildC;
  }

  public static ParentDToChildCAlias parentDToChildC() {
    if (parentDToChildC == null) {
      parentDToChildC = new ParentDToChildCAlias("pdtcc0");
      AliasRegistry.register(ParentDToChildC.class, parentDToChildC);
    }
    return parentDToChildC;
  }

  public static PrimitivesAlias primitives() {
    if (primitives == null) {
      primitives = new PrimitivesAlias("p0");
      AliasRegistry.register(Primitives.class, primitives);
    }
    return primitives;
  }

  public static PrimitivesBAlias primitivesB() {
    if (primitivesB == null) {
      primitivesB = new PrimitivesBAlias("pb0");
      AliasRegistry.register(PrimitivesB.class, primitivesB);
    }
    return primitivesB;
  }

  public static PrimitivesCAlias primitivesC() {
    if (primitivesC == null) {
      primitivesC = new PrimitivesCAlias("pc0");
      AliasRegistry.register(PrimitivesC.class, primitivesC);
    }
    return primitivesC;
  }

  public static UserTypesAFooAlias userTypesAFoo() {
    if (userTypesAFoo == null) {
      userTypesAFoo = new UserTypesAFooAlias("utaf0");
      AliasRegistry.register(UserTypesAFoo.class, userTypesAFoo);
    }
    return userTypesAFoo;
  }

  public static ValidationAFooAlias validationAFoo() {
    if (validationAFoo == null) {
      validationAFoo = new ValidationAFooAlias("vaf0");
      AliasRegistry.register(ValidationAFoo.class, validationAFoo);
    }
    return validationAFoo;
  }

}
