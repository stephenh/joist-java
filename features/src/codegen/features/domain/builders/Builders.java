package features.domain.builders;

import features.domain.Child;
import features.domain.ChildF;
import features.domain.ChildG;
import features.domain.ChildH;
import features.domain.ChildIA;
import features.domain.ChildIB;
import features.domain.CodeADomainObject;
import features.domain.GrandChild;
import features.domain.HistoryEntry;
import features.domain.InheritanceABase;
import features.domain.InheritanceAOwner;
import features.domain.InheritanceASubOne;
import features.domain.InheritanceASubOneChild;
import features.domain.InheritanceASubTwo;
import features.domain.InheritanceAThing;
import features.domain.InheritanceBBottom;
import features.domain.InheritanceBMiddle;
import features.domain.InheritanceBRoot;
import features.domain.InheritanceBRootChild;
import features.domain.InheritanceC;
import features.domain.InheritanceCFoo1;
import features.domain.InheritanceCFoo2;
import features.domain.ManyToManyABar;
import features.domain.ManyToManyAFoo;
import features.domain.ManyToManyAFooToBar;
import features.domain.ManyToManyBBar;
import features.domain.ManyToManyBFoo;
import features.domain.ManyToManyBFooToBar;
import features.domain.OneToOneABar;
import features.domain.OneToOneAFoo;
import features.domain.OneToOneBBar;
import features.domain.OneToOneBFoo;
import features.domain.Parent;
import features.domain.ParentBChildBar;
import features.domain.ParentBChildFoo;
import features.domain.ParentBChildZaz;
import features.domain.ParentBParent;
import features.domain.ParentCBar;
import features.domain.ParentCFoo;
import features.domain.ParentD;
import features.domain.ParentDChildA;
import features.domain.ParentDChildB;
import features.domain.ParentDChildC;
import features.domain.ParentDToChildC;
import features.domain.ParentE;
import features.domain.ParentF;
import features.domain.ParentG;
import features.domain.ParentH;
import features.domain.ParentI;
import features.domain.Primitives;
import features.domain.PrimitivesB;
import features.domain.PrimitivesC;
import features.domain.UserTypesAFoo;
import features.domain.ValidationAFoo;
import features.domain.ValuesA;
import features.domain.ValuesB;

public class Builders {

  public static ChildBuilder aChild() {
    return new ChildBuilder(new Child());
  }

  public static ChildBuilder existing(Child child) {
    return new ChildBuilder(child);
  }

  public static ChildBuilder theChild(long id) {
    return new ChildBuilder(Child.queries.find(id));
  }

  public static ChildBuilder theChild(int id) {
    return new ChildBuilder(Child.queries.find((long) id));
  }

  public static ChildFBuilder aChildF() {
    return new ChildFBuilder(new ChildF());
  }

  public static ChildFBuilder existing(ChildF childF) {
    return new ChildFBuilder(childF);
  }

  public static ChildFBuilder theChildF(long id) {
    return new ChildFBuilder(ChildF.queries.find(id));
  }

  public static ChildFBuilder theChildF(int id) {
    return new ChildFBuilder(ChildF.queries.find((long) id));
  }

  public static ChildGBuilder aChildG() {
    return new ChildGBuilder(new ChildG());
  }

  public static ChildGBuilder existing(ChildG childG) {
    return new ChildGBuilder(childG);
  }

  public static ChildGBuilder theChildG(long id) {
    return new ChildGBuilder(ChildG.queries.find(id));
  }

  public static ChildGBuilder theChildG(int id) {
    return new ChildGBuilder(ChildG.queries.find((long) id));
  }

  public static ChildHBuilder aChildH() {
    return new ChildHBuilder(new ChildH());
  }

  public static ChildHBuilder existing(ChildH childH) {
    return new ChildHBuilder(childH);
  }

  public static ChildHBuilder theChildH(long id) {
    return new ChildHBuilder(ChildH.queries.find(id));
  }

  public static ChildHBuilder theChildH(int id) {
    return new ChildHBuilder(ChildH.queries.find((long) id));
  }

  public static ChildIABuilder aChildIA() {
    return new ChildIABuilder(new ChildIA());
  }

  public static ChildIABuilder existing(ChildIA childIA) {
    return new ChildIABuilder(childIA);
  }

  public static ChildIABuilder theChildIA(long id) {
    return new ChildIABuilder(ChildIA.queries.find(id));
  }

  public static ChildIABuilder theChildIA(int id) {
    return new ChildIABuilder(ChildIA.queries.find((long) id));
  }

  public static ChildIBBuilder aChildIB() {
    return new ChildIBBuilder(new ChildIB());
  }

  public static ChildIBBuilder existing(ChildIB childIB) {
    return new ChildIBBuilder(childIB);
  }

  public static ChildIBBuilder theChildIB(long id) {
    return new ChildIBBuilder(ChildIB.queries.find(id));
  }

  public static ChildIBBuilder theChildIB(int id) {
    return new ChildIBBuilder(ChildIB.queries.find((long) id));
  }

  public static CodeADomainObjectBuilder aCodeADomainObject() {
    return new CodeADomainObjectBuilder(new CodeADomainObject());
  }

  public static CodeADomainObjectBuilder existing(CodeADomainObject codeADomainObject) {
    return new CodeADomainObjectBuilder(codeADomainObject);
  }

  public static CodeADomainObjectBuilder theCodeADomainObject(long id) {
    return new CodeADomainObjectBuilder(CodeADomainObject.queries.find(id));
  }

  public static CodeADomainObjectBuilder theCodeADomainObject(int id) {
    return new CodeADomainObjectBuilder(CodeADomainObject.queries.find((long) id));
  }

  public static GrandChildBuilder aGrandChild() {
    return new GrandChildBuilder(new GrandChild());
  }

  public static GrandChildBuilder existing(GrandChild grandChild) {
    return new GrandChildBuilder(grandChild);
  }

  public static GrandChildBuilder theGrandChild(long id) {
    return new GrandChildBuilder(GrandChild.queries.find(id));
  }

  public static GrandChildBuilder theGrandChild(int id) {
    return new GrandChildBuilder(GrandChild.queries.find((long) id));
  }

  public static HistoryEntryBuilder aHistoryEntry() {
    return new HistoryEntryBuilder(new HistoryEntry());
  }

  public static HistoryEntryBuilder existing(HistoryEntry historyEntry) {
    return new HistoryEntryBuilder(historyEntry);
  }

  public static HistoryEntryBuilder theHistoryEntry(long id) {
    return new HistoryEntryBuilder(HistoryEntry.queries.find(id));
  }

  public static HistoryEntryBuilder theHistoryEntry(int id) {
    return new HistoryEntryBuilder(HistoryEntry.queries.find((long) id));
  }

  public static InheritanceABaseBuilder existing(InheritanceABase inheritanceABase) {
    if (inheritanceABase instanceof InheritanceASubTwo) {
      return new InheritanceASubTwoBuilder((InheritanceASubTwo) inheritanceABase);
    }
    if (inheritanceABase instanceof InheritanceASubOne) {
      return new InheritanceASubOneBuilder((InheritanceASubOne) inheritanceABase);
    }
    return new InheritanceABaseBuilder(inheritanceABase);
  }

  public static InheritanceABaseBuilder theInheritanceABase(long id) {
    return new InheritanceABaseBuilder(InheritanceABase.queries.find(id));
  }

  public static InheritanceABaseBuilder theInheritanceABase(int id) {
    return new InheritanceABaseBuilder(InheritanceABase.queries.find((long) id));
  }

  public static InheritanceAOwnerBuilder aInheritanceAOwner() {
    return new InheritanceAOwnerBuilder(new InheritanceAOwner());
  }

  public static InheritanceAOwnerBuilder existing(InheritanceAOwner inheritanceAOwner) {
    return new InheritanceAOwnerBuilder(inheritanceAOwner);
  }

  public static InheritanceAOwnerBuilder theInheritanceAOwner(long id) {
    return new InheritanceAOwnerBuilder(InheritanceAOwner.queries.find(id));
  }

  public static InheritanceAOwnerBuilder theInheritanceAOwner(int id) {
    return new InheritanceAOwnerBuilder(InheritanceAOwner.queries.find((long) id));
  }

  public static InheritanceASubOneBuilder aInheritanceASubOne() {
    return new InheritanceASubOneBuilder(new InheritanceASubOne());
  }

  public static InheritanceASubOneBuilder existing(InheritanceASubOne inheritanceASubOne) {
    return new InheritanceASubOneBuilder(inheritanceASubOne);
  }

  public static InheritanceASubOneBuilder theInheritanceASubOne(long id) {
    return new InheritanceASubOneBuilder(InheritanceASubOne.queries.find(id));
  }

  public static InheritanceASubOneBuilder theInheritanceASubOne(int id) {
    return new InheritanceASubOneBuilder(InheritanceASubOne.queries.find((long) id));
  }

  public static InheritanceASubOneChildBuilder aInheritanceASubOneChild() {
    return new InheritanceASubOneChildBuilder(new InheritanceASubOneChild());
  }

  public static InheritanceASubOneChildBuilder existing(InheritanceASubOneChild inheritanceASubOneChild) {
    return new InheritanceASubOneChildBuilder(inheritanceASubOneChild);
  }

  public static InheritanceASubOneChildBuilder theInheritanceASubOneChild(long id) {
    return new InheritanceASubOneChildBuilder(InheritanceASubOneChild.queries.find(id));
  }

  public static InheritanceASubOneChildBuilder theInheritanceASubOneChild(int id) {
    return new InheritanceASubOneChildBuilder(InheritanceASubOneChild.queries.find((long) id));
  }

  public static InheritanceASubTwoBuilder aInheritanceASubTwo() {
    return new InheritanceASubTwoBuilder(new InheritanceASubTwo());
  }

  public static InheritanceASubTwoBuilder existing(InheritanceASubTwo inheritanceASubTwo) {
    return new InheritanceASubTwoBuilder(inheritanceASubTwo);
  }

  public static InheritanceASubTwoBuilder theInheritanceASubTwo(long id) {
    return new InheritanceASubTwoBuilder(InheritanceASubTwo.queries.find(id));
  }

  public static InheritanceASubTwoBuilder theInheritanceASubTwo(int id) {
    return new InheritanceASubTwoBuilder(InheritanceASubTwo.queries.find((long) id));
  }

  public static InheritanceAThingBuilder aInheritanceAThing() {
    return new InheritanceAThingBuilder(new InheritanceAThing());
  }

  public static InheritanceAThingBuilder existing(InheritanceAThing inheritanceAThing) {
    return new InheritanceAThingBuilder(inheritanceAThing);
  }

  public static InheritanceAThingBuilder theInheritanceAThing(long id) {
    return new InheritanceAThingBuilder(InheritanceAThing.queries.find(id));
  }

  public static InheritanceAThingBuilder theInheritanceAThing(int id) {
    return new InheritanceAThingBuilder(InheritanceAThing.queries.find((long) id));
  }

  public static InheritanceBBottomBuilder aInheritanceBBottom() {
    return new InheritanceBBottomBuilder(new InheritanceBBottom());
  }

  public static InheritanceBBottomBuilder existing(InheritanceBBottom inheritanceBBottom) {
    return new InheritanceBBottomBuilder(inheritanceBBottom);
  }

  public static InheritanceBBottomBuilder theInheritanceBBottom(long id) {
    return new InheritanceBBottomBuilder(InheritanceBBottom.queries.find(id));
  }

  public static InheritanceBBottomBuilder theInheritanceBBottom(int id) {
    return new InheritanceBBottomBuilder(InheritanceBBottom.queries.find((long) id));
  }

  public static InheritanceBMiddleBuilder existing(InheritanceBMiddle inheritanceBMiddle) {
    if (inheritanceBMiddle instanceof InheritanceBBottom) {
      return new InheritanceBBottomBuilder((InheritanceBBottom) inheritanceBMiddle);
    }
    return new InheritanceBMiddleBuilder(inheritanceBMiddle);
  }

  public static InheritanceBMiddleBuilder theInheritanceBMiddle(long id) {
    return new InheritanceBMiddleBuilder(InheritanceBMiddle.queries.find(id));
  }

  public static InheritanceBMiddleBuilder theInheritanceBMiddle(int id) {
    return new InheritanceBMiddleBuilder(InheritanceBMiddle.queries.find((long) id));
  }

  public static InheritanceBRootBuilder existing(InheritanceBRoot inheritanceBRoot) {
    if (inheritanceBRoot instanceof InheritanceBBottom) {
      return new InheritanceBBottomBuilder((InheritanceBBottom) inheritanceBRoot);
    }
    if (inheritanceBRoot instanceof InheritanceBMiddle) {
      return new InheritanceBMiddleBuilder((InheritanceBMiddle) inheritanceBRoot);
    }
    return new InheritanceBRootBuilder(inheritanceBRoot);
  }

  public static InheritanceBRootBuilder theInheritanceBRoot(long id) {
    return new InheritanceBRootBuilder(InheritanceBRoot.queries.find(id));
  }

  public static InheritanceBRootBuilder theInheritanceBRoot(int id) {
    return new InheritanceBRootBuilder(InheritanceBRoot.queries.find((long) id));
  }

  public static InheritanceBRootChildBuilder aInheritanceBRootChild() {
    return new InheritanceBRootChildBuilder(new InheritanceBRootChild());
  }

  public static InheritanceBRootChildBuilder existing(InheritanceBRootChild inheritanceBRootChild) {
    return new InheritanceBRootChildBuilder(inheritanceBRootChild);
  }

  public static InheritanceBRootChildBuilder theInheritanceBRootChild(long id) {
    return new InheritanceBRootChildBuilder(InheritanceBRootChild.queries.find(id));
  }

  public static InheritanceBRootChildBuilder theInheritanceBRootChild(int id) {
    return new InheritanceBRootChildBuilder(InheritanceBRootChild.queries.find((long) id));
  }

  public static InheritanceCBuilder existing(InheritanceC inheritanceC) {
    if (inheritanceC instanceof InheritanceCFoo2) {
      return new InheritanceCFoo2Builder((InheritanceCFoo2) inheritanceC);
    }
    if (inheritanceC instanceof InheritanceCFoo1) {
      return new InheritanceCFoo1Builder((InheritanceCFoo1) inheritanceC);
    }
    return new InheritanceCBuilder(inheritanceC);
  }

  public static InheritanceCBuilder theInheritanceC(long id) {
    return new InheritanceCBuilder(InheritanceC.queries.find(id));
  }

  public static InheritanceCBuilder theInheritanceC(int id) {
    return new InheritanceCBuilder(InheritanceC.queries.find((long) id));
  }

  public static InheritanceCFoo1Builder aInheritanceCFoo1() {
    return new InheritanceCFoo1Builder(new InheritanceCFoo1());
  }

  public static InheritanceCFoo1Builder existing(InheritanceCFoo1 inheritanceCFoo1) {
    return new InheritanceCFoo1Builder(inheritanceCFoo1);
  }

  public static InheritanceCFoo1Builder theInheritanceCFoo1(long id) {
    return new InheritanceCFoo1Builder(InheritanceCFoo1.queries.find(id));
  }

  public static InheritanceCFoo1Builder theInheritanceCFoo1(int id) {
    return new InheritanceCFoo1Builder(InheritanceCFoo1.queries.find((long) id));
  }

  public static InheritanceCFoo2Builder aInheritanceCFoo2() {
    return new InheritanceCFoo2Builder(new InheritanceCFoo2());
  }

  public static InheritanceCFoo2Builder existing(InheritanceCFoo2 inheritanceCFoo2) {
    return new InheritanceCFoo2Builder(inheritanceCFoo2);
  }

  public static InheritanceCFoo2Builder theInheritanceCFoo2(long id) {
    return new InheritanceCFoo2Builder(InheritanceCFoo2.queries.find(id));
  }

  public static InheritanceCFoo2Builder theInheritanceCFoo2(int id) {
    return new InheritanceCFoo2Builder(InheritanceCFoo2.queries.find((long) id));
  }

  public static ManyToManyABarBuilder aManyToManyABar() {
    return new ManyToManyABarBuilder(new ManyToManyABar());
  }

  public static ManyToManyABarBuilder existing(ManyToManyABar manyToManyABar) {
    return new ManyToManyABarBuilder(manyToManyABar);
  }

  public static ManyToManyABarBuilder theManyToManyABar(long id) {
    return new ManyToManyABarBuilder(ManyToManyABar.queries.find(id));
  }

  public static ManyToManyABarBuilder theManyToManyABar(int id) {
    return new ManyToManyABarBuilder(ManyToManyABar.queries.find((long) id));
  }

  public static ManyToManyAFooBuilder aManyToManyAFoo() {
    return new ManyToManyAFooBuilder(new ManyToManyAFoo());
  }

  public static ManyToManyAFooBuilder existing(ManyToManyAFoo manyToManyAFoo) {
    return new ManyToManyAFooBuilder(manyToManyAFoo);
  }

  public static ManyToManyAFooBuilder theManyToManyAFoo(long id) {
    return new ManyToManyAFooBuilder(ManyToManyAFoo.queries.find(id));
  }

  public static ManyToManyAFooBuilder theManyToManyAFoo(int id) {
    return new ManyToManyAFooBuilder(ManyToManyAFoo.queries.find((long) id));
  }

  public static ManyToManyAFooToBarBuilder aManyToManyAFooToBar() {
    return new ManyToManyAFooToBarBuilder(new ManyToManyAFooToBar());
  }

  public static ManyToManyAFooToBarBuilder existing(ManyToManyAFooToBar manyToManyAFooToBar) {
    return new ManyToManyAFooToBarBuilder(manyToManyAFooToBar);
  }

  public static ManyToManyAFooToBarBuilder theManyToManyAFooToBar(long id) {
    return new ManyToManyAFooToBarBuilder(ManyToManyAFooToBar.queries.find(id));
  }

  public static ManyToManyAFooToBarBuilder theManyToManyAFooToBar(int id) {
    return new ManyToManyAFooToBarBuilder(ManyToManyAFooToBar.queries.find((long) id));
  }

  public static ManyToManyBBarBuilder aManyToManyBBar() {
    return new ManyToManyBBarBuilder(new ManyToManyBBar());
  }

  public static ManyToManyBBarBuilder existing(ManyToManyBBar manyToManyBBar) {
    return new ManyToManyBBarBuilder(manyToManyBBar);
  }

  public static ManyToManyBBarBuilder theManyToManyBBar(long id) {
    return new ManyToManyBBarBuilder(ManyToManyBBar.queries.find(id));
  }

  public static ManyToManyBBarBuilder theManyToManyBBar(int id) {
    return new ManyToManyBBarBuilder(ManyToManyBBar.queries.find((long) id));
  }

  public static ManyToManyBFooBuilder aManyToManyBFoo() {
    return new ManyToManyBFooBuilder(new ManyToManyBFoo());
  }

  public static ManyToManyBFooBuilder existing(ManyToManyBFoo manyToManyBFoo) {
    return new ManyToManyBFooBuilder(manyToManyBFoo);
  }

  public static ManyToManyBFooBuilder theManyToManyBFoo(long id) {
    return new ManyToManyBFooBuilder(ManyToManyBFoo.queries.find(id));
  }

  public static ManyToManyBFooBuilder theManyToManyBFoo(int id) {
    return new ManyToManyBFooBuilder(ManyToManyBFoo.queries.find((long) id));
  }

  public static ManyToManyBFooToBarBuilder aManyToManyBFooToBar() {
    return new ManyToManyBFooToBarBuilder(new ManyToManyBFooToBar());
  }

  public static ManyToManyBFooToBarBuilder existing(ManyToManyBFooToBar manyToManyBFooToBar) {
    return new ManyToManyBFooToBarBuilder(manyToManyBFooToBar);
  }

  public static ManyToManyBFooToBarBuilder theManyToManyBFooToBar(long id) {
    return new ManyToManyBFooToBarBuilder(ManyToManyBFooToBar.queries.find(id));
  }

  public static ManyToManyBFooToBarBuilder theManyToManyBFooToBar(int id) {
    return new ManyToManyBFooToBarBuilder(ManyToManyBFooToBar.queries.find((long) id));
  }

  public static OneToOneABarBuilder aOneToOneABar() {
    return new OneToOneABarBuilder(new OneToOneABar());
  }

  public static OneToOneABarBuilder existing(OneToOneABar oneToOneABar) {
    return new OneToOneABarBuilder(oneToOneABar);
  }

  public static OneToOneABarBuilder theOneToOneABar(long id) {
    return new OneToOneABarBuilder(OneToOneABar.queries.find(id));
  }

  public static OneToOneABarBuilder theOneToOneABar(int id) {
    return new OneToOneABarBuilder(OneToOneABar.queries.find((long) id));
  }

  public static OneToOneAFooBuilder aOneToOneAFoo() {
    return new OneToOneAFooBuilder(new OneToOneAFoo());
  }

  public static OneToOneAFooBuilder existing(OneToOneAFoo oneToOneAFoo) {
    return new OneToOneAFooBuilder(oneToOneAFoo);
  }

  public static OneToOneAFooBuilder theOneToOneAFoo(long id) {
    return new OneToOneAFooBuilder(OneToOneAFoo.queries.find(id));
  }

  public static OneToOneAFooBuilder theOneToOneAFoo(int id) {
    return new OneToOneAFooBuilder(OneToOneAFoo.queries.find((long) id));
  }

  public static OneToOneBBarBuilder aOneToOneBBar() {
    return new OneToOneBBarBuilder(new OneToOneBBar());
  }

  public static OneToOneBBarBuilder existing(OneToOneBBar oneToOneBBar) {
    return new OneToOneBBarBuilder(oneToOneBBar);
  }

  public static OneToOneBBarBuilder theOneToOneBBar(long id) {
    return new OneToOneBBarBuilder(OneToOneBBar.queries.find(id));
  }

  public static OneToOneBBarBuilder theOneToOneBBar(int id) {
    return new OneToOneBBarBuilder(OneToOneBBar.queries.find((long) id));
  }

  public static OneToOneBFooBuilder aOneToOneBFoo() {
    return new OneToOneBFooBuilder(new OneToOneBFoo());
  }

  public static OneToOneBFooBuilder existing(OneToOneBFoo oneToOneBFoo) {
    return new OneToOneBFooBuilder(oneToOneBFoo);
  }

  public static OneToOneBFooBuilder theOneToOneBFoo(long id) {
    return new OneToOneBFooBuilder(OneToOneBFoo.queries.find(id));
  }

  public static OneToOneBFooBuilder theOneToOneBFoo(int id) {
    return new OneToOneBFooBuilder(OneToOneBFoo.queries.find((long) id));
  }

  public static ParentBuilder aParent() {
    return new ParentBuilder(new Parent());
  }

  public static ParentBuilder existing(Parent parent) {
    return new ParentBuilder(parent);
  }

  public static ParentBuilder theParent(long id) {
    return new ParentBuilder(Parent.queries.find(id));
  }

  public static ParentBuilder theParent(int id) {
    return new ParentBuilder(Parent.queries.find((long) id));
  }

  public static ParentBChildBarBuilder aParentBChildBar() {
    return new ParentBChildBarBuilder(new ParentBChildBar());
  }

  public static ParentBChildBarBuilder existing(ParentBChildBar parentBChildBar) {
    return new ParentBChildBarBuilder(parentBChildBar);
  }

  public static ParentBChildBarBuilder theParentBChildBar(long id) {
    return new ParentBChildBarBuilder(ParentBChildBar.queries.find(id));
  }

  public static ParentBChildBarBuilder theParentBChildBar(int id) {
    return new ParentBChildBarBuilder(ParentBChildBar.queries.find((long) id));
  }

  public static ParentBChildFooBuilder aParentBChildFoo() {
    return new ParentBChildFooBuilder(new ParentBChildFoo());
  }

  public static ParentBChildFooBuilder existing(ParentBChildFoo parentBChildFoo) {
    return new ParentBChildFooBuilder(parentBChildFoo);
  }

  public static ParentBChildFooBuilder theParentBChildFoo(long id) {
    return new ParentBChildFooBuilder(ParentBChildFoo.queries.find(id));
  }

  public static ParentBChildFooBuilder theParentBChildFoo(int id) {
    return new ParentBChildFooBuilder(ParentBChildFoo.queries.find((long) id));
  }

  public static ParentBChildZazBuilder aParentBChildZaz() {
    return new ParentBChildZazBuilder(new ParentBChildZaz());
  }

  public static ParentBChildZazBuilder existing(ParentBChildZaz parentBChildZaz) {
    return new ParentBChildZazBuilder(parentBChildZaz);
  }

  public static ParentBChildZazBuilder theParentBChildZaz(long id) {
    return new ParentBChildZazBuilder(ParentBChildZaz.queries.find(id));
  }

  public static ParentBChildZazBuilder theParentBChildZaz(int id) {
    return new ParentBChildZazBuilder(ParentBChildZaz.queries.find((long) id));
  }

  public static ParentBParentBuilder aParentBParent() {
    return new ParentBParentBuilder(new ParentBParent());
  }

  public static ParentBParentBuilder existing(ParentBParent parentBParent) {
    return new ParentBParentBuilder(parentBParent);
  }

  public static ParentBParentBuilder theParentBParent(long id) {
    return new ParentBParentBuilder(ParentBParent.queries.find(id));
  }

  public static ParentBParentBuilder theParentBParent(int id) {
    return new ParentBParentBuilder(ParentBParent.queries.find((long) id));
  }

  public static ParentCBarBuilder aParentCBar() {
    return new ParentCBarBuilder(new ParentCBar());
  }

  public static ParentCBarBuilder existing(ParentCBar parentCBar) {
    return new ParentCBarBuilder(parentCBar);
  }

  public static ParentCBarBuilder theParentCBar(long id) {
    return new ParentCBarBuilder(ParentCBar.queries.find(id));
  }

  public static ParentCBarBuilder theParentCBar(int id) {
    return new ParentCBarBuilder(ParentCBar.queries.find((long) id));
  }

  public static ParentCFooBuilder aParentCFoo() {
    return new ParentCFooBuilder(new ParentCFoo());
  }

  public static ParentCFooBuilder existing(ParentCFoo parentCFoo) {
    return new ParentCFooBuilder(parentCFoo);
  }

  public static ParentCFooBuilder theParentCFoo(long id) {
    return new ParentCFooBuilder(ParentCFoo.queries.find(id));
  }

  public static ParentCFooBuilder theParentCFoo(int id) {
    return new ParentCFooBuilder(ParentCFoo.queries.find((long) id));
  }

  public static ParentDBuilder aParentD() {
    return new ParentDBuilder(new ParentD());
  }

  public static ParentDBuilder existing(ParentD parentD) {
    return new ParentDBuilder(parentD);
  }

  public static ParentDBuilder theParentD(long id) {
    return new ParentDBuilder(ParentD.queries.find(id));
  }

  public static ParentDBuilder theParentD(int id) {
    return new ParentDBuilder(ParentD.queries.find((long) id));
  }

  public static ParentDChildABuilder aParentDChildA() {
    return new ParentDChildABuilder(new ParentDChildA());
  }

  public static ParentDChildABuilder existing(ParentDChildA parentDChildA) {
    return new ParentDChildABuilder(parentDChildA);
  }

  public static ParentDChildABuilder theParentDChildA(long id) {
    return new ParentDChildABuilder(ParentDChildA.queries.find(id));
  }

  public static ParentDChildABuilder theParentDChildA(int id) {
    return new ParentDChildABuilder(ParentDChildA.queries.find((long) id));
  }

  public static ParentDChildBBuilder aParentDChildB() {
    return new ParentDChildBBuilder(new ParentDChildB());
  }

  public static ParentDChildBBuilder existing(ParentDChildB parentDChildB) {
    return new ParentDChildBBuilder(parentDChildB);
  }

  public static ParentDChildBBuilder theParentDChildB(long id) {
    return new ParentDChildBBuilder(ParentDChildB.queries.find(id));
  }

  public static ParentDChildBBuilder theParentDChildB(int id) {
    return new ParentDChildBBuilder(ParentDChildB.queries.find((long) id));
  }

  public static ParentDChildCBuilder aParentDChildC() {
    return new ParentDChildCBuilder(new ParentDChildC());
  }

  public static ParentDChildCBuilder existing(ParentDChildC parentDChildC) {
    return new ParentDChildCBuilder(parentDChildC);
  }

  public static ParentDChildCBuilder theParentDChildC(long id) {
    return new ParentDChildCBuilder(ParentDChildC.queries.find(id));
  }

  public static ParentDChildCBuilder theParentDChildC(int id) {
    return new ParentDChildCBuilder(ParentDChildC.queries.find((long) id));
  }

  public static ParentDToChildCBuilder aParentDToChildC() {
    return new ParentDToChildCBuilder(new ParentDToChildC());
  }

  public static ParentDToChildCBuilder existing(ParentDToChildC parentDToChildC) {
    return new ParentDToChildCBuilder(parentDToChildC);
  }

  public static ParentDToChildCBuilder theParentDToChildC(long id) {
    return new ParentDToChildCBuilder(ParentDToChildC.queries.find(id));
  }

  public static ParentDToChildCBuilder theParentDToChildC(int id) {
    return new ParentDToChildCBuilder(ParentDToChildC.queries.find((long) id));
  }

  public static ParentEBuilder aParentE() {
    return new ParentEBuilder(new ParentE());
  }

  public static ParentEBuilder existing(ParentE parentE) {
    return new ParentEBuilder(parentE);
  }

  public static ParentEBuilder theParentE(long id) {
    return new ParentEBuilder(ParentE.queries.find(id));
  }

  public static ParentEBuilder theParentE(int id) {
    return new ParentEBuilder(ParentE.queries.find((long) id));
  }

  public static ParentFBuilder aParentF() {
    return new ParentFBuilder(new ParentF());
  }

  public static ParentFBuilder existing(ParentF parentF) {
    return new ParentFBuilder(parentF);
  }

  public static ParentFBuilder theParentF(long id) {
    return new ParentFBuilder(ParentF.queries.find(id));
  }

  public static ParentFBuilder theParentF(int id) {
    return new ParentFBuilder(ParentF.queries.find((long) id));
  }

  public static ParentGBuilder aParentG() {
    return new ParentGBuilder(new ParentG());
  }

  public static ParentGBuilder existing(ParentG parentG) {
    return new ParentGBuilder(parentG);
  }

  public static ParentGBuilder theParentG(long id) {
    return new ParentGBuilder(ParentG.queries.find(id));
  }

  public static ParentGBuilder theParentG(int id) {
    return new ParentGBuilder(ParentG.queries.find((long) id));
  }

  public static ParentHBuilder aParentH() {
    return new ParentHBuilder(new ParentH());
  }

  public static ParentHBuilder existing(ParentH parentH) {
    return new ParentHBuilder(parentH);
  }

  public static ParentHBuilder theParentH(long id) {
    return new ParentHBuilder(ParentH.queries.find(id));
  }

  public static ParentHBuilder theParentH(int id) {
    return new ParentHBuilder(ParentH.queries.find((long) id));
  }

  public static ParentIBuilder aParentI() {
    return new ParentIBuilder(new ParentI());
  }

  public static ParentIBuilder existing(ParentI parentI) {
    return new ParentIBuilder(parentI);
  }

  public static ParentIBuilder theParentI(long id) {
    return new ParentIBuilder(ParentI.queries.find(id));
  }

  public static ParentIBuilder theParentI(int id) {
    return new ParentIBuilder(ParentI.queries.find((long) id));
  }

  public static PrimitivesBuilder aPrimitives() {
    return new PrimitivesBuilder(new Primitives());
  }

  public static PrimitivesBuilder existing(Primitives primitives) {
    return new PrimitivesBuilder(primitives);
  }

  public static PrimitivesBuilder thePrimitives(long id) {
    return new PrimitivesBuilder(Primitives.queries.find(id));
  }

  public static PrimitivesBuilder thePrimitives(int id) {
    return new PrimitivesBuilder(Primitives.queries.find((long) id));
  }

  public static PrimitivesBBuilder aPrimitivesB() {
    return new PrimitivesBBuilder(new PrimitivesB());
  }

  public static PrimitivesBBuilder existing(PrimitivesB primitivesB) {
    return new PrimitivesBBuilder(primitivesB);
  }

  public static PrimitivesBBuilder thePrimitivesB(long id) {
    return new PrimitivesBBuilder(PrimitivesB.queries.find(id));
  }

  public static PrimitivesBBuilder thePrimitivesB(int id) {
    return new PrimitivesBBuilder(PrimitivesB.queries.find((long) id));
  }

  public static PrimitivesCBuilder aPrimitivesC() {
    return new PrimitivesCBuilder(new PrimitivesC());
  }

  public static PrimitivesCBuilder existing(PrimitivesC primitivesC) {
    return new PrimitivesCBuilder(primitivesC);
  }

  public static PrimitivesCBuilder thePrimitivesC(long id) {
    return new PrimitivesCBuilder(PrimitivesC.queries.find(id));
  }

  public static PrimitivesCBuilder thePrimitivesC(int id) {
    return new PrimitivesCBuilder(PrimitivesC.queries.find((long) id));
  }

  public static UserTypesAFooBuilder aUserTypesAFoo() {
    return new UserTypesAFooBuilder(new UserTypesAFoo());
  }

  public static UserTypesAFooBuilder existing(UserTypesAFoo userTypesAFoo) {
    return new UserTypesAFooBuilder(userTypesAFoo);
  }

  public static UserTypesAFooBuilder theUserTypesAFoo(long id) {
    return new UserTypesAFooBuilder(UserTypesAFoo.queries.find(id));
  }

  public static UserTypesAFooBuilder theUserTypesAFoo(int id) {
    return new UserTypesAFooBuilder(UserTypesAFoo.queries.find((long) id));
  }

  public static ValidationAFooBuilder aValidationAFoo() {
    return new ValidationAFooBuilder(new ValidationAFoo());
  }

  public static ValidationAFooBuilder existing(ValidationAFoo validationAFoo) {
    return new ValidationAFooBuilder(validationAFoo);
  }

  public static ValidationAFooBuilder theValidationAFoo(long id) {
    return new ValidationAFooBuilder(ValidationAFoo.queries.find(id));
  }

  public static ValidationAFooBuilder theValidationAFoo(int id) {
    return new ValidationAFooBuilder(ValidationAFoo.queries.find((long) id));
  }

  public static ValuesABuilder aValuesA() {
    return new ValuesABuilder(new ValuesA());
  }

  public static ValuesABuilder existing(ValuesA valuesA) {
    return new ValuesABuilder(valuesA);
  }

  public static ValuesABuilder theValuesA(long id) {
    return new ValuesABuilder(ValuesA.queries.find(id));
  }

  public static ValuesABuilder theValuesA(int id) {
    return new ValuesABuilder(ValuesA.queries.find((long) id));
  }

  public static ValuesBBuilder aValuesB() {
    return new ValuesBBuilder(new ValuesB());
  }

  public static ValuesBBuilder existing(ValuesB valuesB) {
    return new ValuesBBuilder(valuesB);
  }

  public static ValuesBBuilder theValuesB(long id) {
    return new ValuesBBuilder(ValuesB.queries.find(id));
  }

  public static ValuesBBuilder theValuesB(int id) {
    return new ValuesBBuilder(ValuesB.queries.find((long) id));
  }

}
