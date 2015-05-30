package features.domain;

import joist.domain.orm.AliasRegistry;

public class Aliases {

  private static ChildAlias child;
  private static ChildFAlias childF;
  private static ChildGAlias childG;
  private static ChildHAlias childH;
  private static ChildIAAlias childIA;
  private static ChildIBAlias childIB;
  private static CodeADomainObjectAlias codeADomainObject;
  private static GrandChildAlias grandChild;
  private static HistoryEntryAlias historyEntry;
  private static InheritanceABaseAlias inheritanceABase;
  private static InheritanceAOwnerAlias inheritanceAOwner;
  private static InheritanceASubOneAlias inheritanceASubOne;
  private static InheritanceASubOneChildAlias inheritanceASubOneChild;
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
  private static ParentBChildZazAlias parentBChildZaz;
  private static ParentBParentAlias parentBParent;
  private static ParentCBarAlias parentCBar;
  private static ParentCFooAlias parentCFoo;
  private static ParentDAlias parentD;
  private static ParentDChildAAlias parentDChildA;
  private static ParentDChildBAlias parentDChildB;
  private static ParentDChildCAlias parentDChildC;
  private static ParentDToChildCAlias parentDToChildC;
  private static ParentEAlias parentE;
  private static ParentFAlias parentF;
  private static ParentGAlias parentG;
  private static ParentHAlias parentH;
  private static ParentIAlias parentI;
  private static PrimitivesAlias primitives;
  private static PrimitivesBAlias primitivesB;
  private static PrimitivesCAlias primitivesC;
  private static UserTypesAFooAlias userTypesAFoo;
  private static ValidationAFooAlias validationAFoo;
  private static ValuesAAlias valuesA;
  private static ValuesBAlias valuesB;

  public static ChildAlias child() {
    if (child == null) {
      child = new ChildAlias();
      AliasRegistry.register(Child.class, child);
    }
    return child;
  }

  public static ChildFAlias childF() {
    if (childF == null) {
      childF = new ChildFAlias();
      AliasRegistry.register(ChildF.class, childF);
    }
    return childF;
  }

  public static ChildGAlias childG() {
    if (childG == null) {
      childG = new ChildGAlias();
      AliasRegistry.register(ChildG.class, childG);
    }
    return childG;
  }

  public static ChildHAlias childH() {
    if (childH == null) {
      childH = new ChildHAlias();
      AliasRegistry.register(ChildH.class, childH);
    }
    return childH;
  }

  public static ChildIAAlias childIA() {
    if (childIA == null) {
      childIA = new ChildIAAlias();
      AliasRegistry.register(ChildIA.class, childIA);
    }
    return childIA;
  }

  public static ChildIBAlias childIB() {
    if (childIB == null) {
      childIB = new ChildIBAlias();
      AliasRegistry.register(ChildIB.class, childIB);
    }
    return childIB;
  }

  public static CodeADomainObjectAlias codeADomainObject() {
    if (codeADomainObject == null) {
      codeADomainObject = new CodeADomainObjectAlias();
      AliasRegistry.register(CodeADomainObject.class, codeADomainObject);
    }
    return codeADomainObject;
  }

  public static GrandChildAlias grandChild() {
    if (grandChild == null) {
      grandChild = new GrandChildAlias();
      AliasRegistry.register(GrandChild.class, grandChild);
    }
    return grandChild;
  }

  public static HistoryEntryAlias historyEntry() {
    if (historyEntry == null) {
      historyEntry = new HistoryEntryAlias();
      AliasRegistry.register(HistoryEntry.class, historyEntry);
    }
    return historyEntry;
  }

  public static InheritanceABaseAlias inheritanceABase() {
    if (inheritanceABase == null) {
      inheritanceABase = new InheritanceABaseAlias();
      AliasRegistry.register(InheritanceABase.class, inheritanceABase);
    }
    return inheritanceABase;
  }

  public static InheritanceAOwnerAlias inheritanceAOwner() {
    if (inheritanceAOwner == null) {
      inheritanceAOwner = new InheritanceAOwnerAlias();
      AliasRegistry.register(InheritanceAOwner.class, inheritanceAOwner);
    }
    return inheritanceAOwner;
  }

  public static InheritanceASubOneAlias inheritanceASubOne() {
    if (inheritanceASubOne == null) {
      inheritanceASubOne = new InheritanceASubOneAlias();
      AliasRegistry.register(InheritanceASubOne.class, inheritanceASubOne);
    }
    return inheritanceASubOne;
  }

  public static InheritanceASubOneChildAlias inheritanceASubOneChild() {
    if (inheritanceASubOneChild == null) {
      inheritanceASubOneChild = new InheritanceASubOneChildAlias();
      AliasRegistry.register(InheritanceASubOneChild.class, inheritanceASubOneChild);
    }
    return inheritanceASubOneChild;
  }

  public static InheritanceASubTwoAlias inheritanceASubTwo() {
    if (inheritanceASubTwo == null) {
      inheritanceASubTwo = new InheritanceASubTwoAlias();
      AliasRegistry.register(InheritanceASubTwo.class, inheritanceASubTwo);
    }
    return inheritanceASubTwo;
  }

  public static InheritanceAThingAlias inheritanceAThing() {
    if (inheritanceAThing == null) {
      inheritanceAThing = new InheritanceAThingAlias();
      AliasRegistry.register(InheritanceAThing.class, inheritanceAThing);
    }
    return inheritanceAThing;
  }

  public static InheritanceBBottomAlias inheritanceBBottom() {
    if (inheritanceBBottom == null) {
      inheritanceBBottom = new InheritanceBBottomAlias();
      AliasRegistry.register(InheritanceBBottom.class, inheritanceBBottom);
    }
    return inheritanceBBottom;
  }

  public static InheritanceBMiddleAlias inheritanceBMiddle() {
    if (inheritanceBMiddle == null) {
      inheritanceBMiddle = new InheritanceBMiddleAlias();
      AliasRegistry.register(InheritanceBMiddle.class, inheritanceBMiddle);
    }
    return inheritanceBMiddle;
  }

  public static InheritanceBRootAlias inheritanceBRoot() {
    if (inheritanceBRoot == null) {
      inheritanceBRoot = new InheritanceBRootAlias();
      AliasRegistry.register(InheritanceBRoot.class, inheritanceBRoot);
    }
    return inheritanceBRoot;
  }

  public static InheritanceBRootChildAlias inheritanceBRootChild() {
    if (inheritanceBRootChild == null) {
      inheritanceBRootChild = new InheritanceBRootChildAlias();
      AliasRegistry.register(InheritanceBRootChild.class, inheritanceBRootChild);
    }
    return inheritanceBRootChild;
  }

  public static InheritanceCAlias inheritanceC() {
    if (inheritanceC == null) {
      inheritanceC = new InheritanceCAlias();
      AliasRegistry.register(InheritanceC.class, inheritanceC);
    }
    return inheritanceC;
  }

  public static InheritanceCFoo1Alias inheritanceCFoo1() {
    if (inheritanceCFoo1 == null) {
      inheritanceCFoo1 = new InheritanceCFoo1Alias();
      AliasRegistry.register(InheritanceCFoo1.class, inheritanceCFoo1);
    }
    return inheritanceCFoo1;
  }

  public static InheritanceCFoo2Alias inheritanceCFoo2() {
    if (inheritanceCFoo2 == null) {
      inheritanceCFoo2 = new InheritanceCFoo2Alias();
      AliasRegistry.register(InheritanceCFoo2.class, inheritanceCFoo2);
    }
    return inheritanceCFoo2;
  }

  public static ManyToManyABarAlias manyToManyABar() {
    if (manyToManyABar == null) {
      manyToManyABar = new ManyToManyABarAlias();
      AliasRegistry.register(ManyToManyABar.class, manyToManyABar);
    }
    return manyToManyABar;
  }

  public static ManyToManyAFooAlias manyToManyAFoo() {
    if (manyToManyAFoo == null) {
      manyToManyAFoo = new ManyToManyAFooAlias();
      AliasRegistry.register(ManyToManyAFoo.class, manyToManyAFoo);
    }
    return manyToManyAFoo;
  }

  public static ManyToManyAFooToBarAlias manyToManyAFooToBar() {
    if (manyToManyAFooToBar == null) {
      manyToManyAFooToBar = new ManyToManyAFooToBarAlias();
      AliasRegistry.register(ManyToManyAFooToBar.class, manyToManyAFooToBar);
    }
    return manyToManyAFooToBar;
  }

  public static ManyToManyBBarAlias manyToManyBBar() {
    if (manyToManyBBar == null) {
      manyToManyBBar = new ManyToManyBBarAlias();
      AliasRegistry.register(ManyToManyBBar.class, manyToManyBBar);
    }
    return manyToManyBBar;
  }

  public static ManyToManyBFooAlias manyToManyBFoo() {
    if (manyToManyBFoo == null) {
      manyToManyBFoo = new ManyToManyBFooAlias();
      AliasRegistry.register(ManyToManyBFoo.class, manyToManyBFoo);
    }
    return manyToManyBFoo;
  }

  public static ManyToManyBFooToBarAlias manyToManyBFooToBar() {
    if (manyToManyBFooToBar == null) {
      manyToManyBFooToBar = new ManyToManyBFooToBarAlias();
      AliasRegistry.register(ManyToManyBFooToBar.class, manyToManyBFooToBar);
    }
    return manyToManyBFooToBar;
  }

  public static OneToOneABarAlias oneToOneABar() {
    if (oneToOneABar == null) {
      oneToOneABar = new OneToOneABarAlias();
      AliasRegistry.register(OneToOneABar.class, oneToOneABar);
    }
    return oneToOneABar;
  }

  public static OneToOneAFooAlias oneToOneAFoo() {
    if (oneToOneAFoo == null) {
      oneToOneAFoo = new OneToOneAFooAlias();
      AliasRegistry.register(OneToOneAFoo.class, oneToOneAFoo);
    }
    return oneToOneAFoo;
  }

  public static OneToOneBBarAlias oneToOneBBar() {
    if (oneToOneBBar == null) {
      oneToOneBBar = new OneToOneBBarAlias();
      AliasRegistry.register(OneToOneBBar.class, oneToOneBBar);
    }
    return oneToOneBBar;
  }

  public static OneToOneBFooAlias oneToOneBFoo() {
    if (oneToOneBFoo == null) {
      oneToOneBFoo = new OneToOneBFooAlias();
      AliasRegistry.register(OneToOneBFoo.class, oneToOneBFoo);
    }
    return oneToOneBFoo;
  }

  public static ParentAlias parent() {
    if (parent == null) {
      parent = new ParentAlias();
      AliasRegistry.register(Parent.class, parent);
    }
    return parent;
  }

  public static ParentBChildBarAlias parentBChildBar() {
    if (parentBChildBar == null) {
      parentBChildBar = new ParentBChildBarAlias();
      AliasRegistry.register(ParentBChildBar.class, parentBChildBar);
    }
    return parentBChildBar;
  }

  public static ParentBChildFooAlias parentBChildFoo() {
    if (parentBChildFoo == null) {
      parentBChildFoo = new ParentBChildFooAlias();
      AliasRegistry.register(ParentBChildFoo.class, parentBChildFoo);
    }
    return parentBChildFoo;
  }

  public static ParentBChildZazAlias parentBChildZaz() {
    if (parentBChildZaz == null) {
      parentBChildZaz = new ParentBChildZazAlias();
      AliasRegistry.register(ParentBChildZaz.class, parentBChildZaz);
    }
    return parentBChildZaz;
  }

  public static ParentBParentAlias parentBParent() {
    if (parentBParent == null) {
      parentBParent = new ParentBParentAlias();
      AliasRegistry.register(ParentBParent.class, parentBParent);
    }
    return parentBParent;
  }

  public static ParentCBarAlias parentCBar() {
    if (parentCBar == null) {
      parentCBar = new ParentCBarAlias();
      AliasRegistry.register(ParentCBar.class, parentCBar);
    }
    return parentCBar;
  }

  public static ParentCFooAlias parentCFoo() {
    if (parentCFoo == null) {
      parentCFoo = new ParentCFooAlias();
      AliasRegistry.register(ParentCFoo.class, parentCFoo);
    }
    return parentCFoo;
  }

  public static ParentDAlias parentD() {
    if (parentD == null) {
      parentD = new ParentDAlias();
      AliasRegistry.register(ParentD.class, parentD);
    }
    return parentD;
  }

  public static ParentDChildAAlias parentDChildA() {
    if (parentDChildA == null) {
      parentDChildA = new ParentDChildAAlias();
      AliasRegistry.register(ParentDChildA.class, parentDChildA);
    }
    return parentDChildA;
  }

  public static ParentDChildBAlias parentDChildB() {
    if (parentDChildB == null) {
      parentDChildB = new ParentDChildBAlias();
      AliasRegistry.register(ParentDChildB.class, parentDChildB);
    }
    return parentDChildB;
  }

  public static ParentDChildCAlias parentDChildC() {
    if (parentDChildC == null) {
      parentDChildC = new ParentDChildCAlias();
      AliasRegistry.register(ParentDChildC.class, parentDChildC);
    }
    return parentDChildC;
  }

  public static ParentDToChildCAlias parentDToChildC() {
    if (parentDToChildC == null) {
      parentDToChildC = new ParentDToChildCAlias();
      AliasRegistry.register(ParentDToChildC.class, parentDToChildC);
    }
    return parentDToChildC;
  }

  public static ParentEAlias parentE() {
    if (parentE == null) {
      parentE = new ParentEAlias();
      AliasRegistry.register(ParentE.class, parentE);
    }
    return parentE;
  }

  public static ParentFAlias parentF() {
    if (parentF == null) {
      parentF = new ParentFAlias();
      AliasRegistry.register(ParentF.class, parentF);
    }
    return parentF;
  }

  public static ParentGAlias parentG() {
    if (parentG == null) {
      parentG = new ParentGAlias();
      AliasRegistry.register(ParentG.class, parentG);
    }
    return parentG;
  }

  public static ParentHAlias parentH() {
    if (parentH == null) {
      parentH = new ParentHAlias();
      AliasRegistry.register(ParentH.class, parentH);
    }
    return parentH;
  }

  public static ParentIAlias parentI() {
    if (parentI == null) {
      parentI = new ParentIAlias();
      AliasRegistry.register(ParentI.class, parentI);
    }
    return parentI;
  }

  public static PrimitivesAlias primitives() {
    if (primitives == null) {
      primitives = new PrimitivesAlias();
      AliasRegistry.register(Primitives.class, primitives);
    }
    return primitives;
  }

  public static PrimitivesBAlias primitivesB() {
    if (primitivesB == null) {
      primitivesB = new PrimitivesBAlias();
      AliasRegistry.register(PrimitivesB.class, primitivesB);
    }
    return primitivesB;
  }

  public static PrimitivesCAlias primitivesC() {
    if (primitivesC == null) {
      primitivesC = new PrimitivesCAlias();
      AliasRegistry.register(PrimitivesC.class, primitivesC);
    }
    return primitivesC;
  }

  public static UserTypesAFooAlias userTypesAFoo() {
    if (userTypesAFoo == null) {
      userTypesAFoo = new UserTypesAFooAlias();
      AliasRegistry.register(UserTypesAFoo.class, userTypesAFoo);
    }
    return userTypesAFoo;
  }

  public static ValidationAFooAlias validationAFoo() {
    if (validationAFoo == null) {
      validationAFoo = new ValidationAFooAlias();
      AliasRegistry.register(ValidationAFoo.class, validationAFoo);
    }
    return validationAFoo;
  }

  public static ValuesAAlias valuesA() {
    if (valuesA == null) {
      valuesA = new ValuesAAlias();
      AliasRegistry.register(ValuesA.class, valuesA);
    }
    return valuesA;
  }

  public static ValuesBAlias valuesB() {
    if (valuesB == null) {
      valuesB = new ValuesBAlias();
      AliasRegistry.register(ValuesB.class, valuesB);
    }
    return valuesB;
  }

}
