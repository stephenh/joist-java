package features.domain.builders;

import features.domain.Child;
import features.domain.CodeADomainObject;
import features.domain.GrandChild;
import features.domain.InheritanceABase;
import features.domain.InheritanceASubOne;
import features.domain.InheritanceASubTwo;
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
import features.domain.ParentBParent;
import features.domain.ParentCBar;
import features.domain.ParentCFoo;
import features.domain.ParentD;
import features.domain.ParentDChildA;
import features.domain.ParentDChildB;
import features.domain.ParentDChildC;
import features.domain.ParentDToChildC;
import features.domain.Primitives;
import features.domain.PrimitivesB;
import features.domain.PrimitivesC;
import features.domain.UserTypesAFoo;
import features.domain.ValidationAFoo;

public class Builders {

    public static ChildBuilder aChild() {
        return new ChildBuilder(new Child());
    }

    public static ChildBuilder existing(Child child) {
        return new ChildBuilder(child);
    }

    public static CodeADomainObjectBuilder aCodeADomainObject() {
        return new CodeADomainObjectBuilder(new CodeADomainObject());
    }

    public static CodeADomainObjectBuilder existing(CodeADomainObject codeADomainObject) {
        return new CodeADomainObjectBuilder(codeADomainObject);
    }

    public static GrandChildBuilder aGrandChild() {
        return new GrandChildBuilder(new GrandChild());
    }

    public static GrandChildBuilder existing(GrandChild grandChild) {
        return new GrandChildBuilder(grandChild);
    }

    public static InheritanceABaseBuilder aInheritanceABase() {
        return new InheritanceABaseBuilder(new InheritanceABase());
    }

    public static InheritanceABaseBuilder existing(InheritanceABase inheritanceABase) {
        return new InheritanceABaseBuilder(inheritanceABase);
    }

    public static InheritanceASubOneBuilder aInheritanceASubOne() {
        return new InheritanceASubOneBuilder(new InheritanceASubOne());
    }

    public static InheritanceASubOneBuilder existing(InheritanceASubOne inheritanceASubOne) {
        return new InheritanceASubOneBuilder(inheritanceASubOne);
    }

    public static InheritanceASubTwoBuilder aInheritanceASubTwo() {
        return new InheritanceASubTwoBuilder(new InheritanceASubTwo());
    }

    public static InheritanceASubTwoBuilder existing(InheritanceASubTwo inheritanceASubTwo) {
        return new InheritanceASubTwoBuilder(inheritanceASubTwo);
    }

    public static InheritanceBBottomBuilder aInheritanceBBottom() {
        return new InheritanceBBottomBuilder(new InheritanceBBottom());
    }

    public static InheritanceBBottomBuilder existing(InheritanceBBottom inheritanceBBottom) {
        return new InheritanceBBottomBuilder(inheritanceBBottom);
    }

    public static InheritanceBMiddleBuilder aInheritanceBMiddle() {
        return new InheritanceBMiddleBuilder(new InheritanceBMiddle());
    }

    public static InheritanceBMiddleBuilder existing(InheritanceBMiddle inheritanceBMiddle) {
        return new InheritanceBMiddleBuilder(inheritanceBMiddle);
    }

    public static InheritanceBRootBuilder aInheritanceBRoot() {
        return new InheritanceBRootBuilder(new InheritanceBRoot());
    }

    public static InheritanceBRootBuilder existing(InheritanceBRoot inheritanceBRoot) {
        return new InheritanceBRootBuilder(inheritanceBRoot);
    }

    public static InheritanceBRootChildBuilder aInheritanceBRootChild() {
        return new InheritanceBRootChildBuilder(new InheritanceBRootChild());
    }

    public static InheritanceBRootChildBuilder existing(InheritanceBRootChild inheritanceBRootChild) {
        return new InheritanceBRootChildBuilder(inheritanceBRootChild);
    }

    public static InheritanceCBuilder aInheritanceC() {
        return new InheritanceCBuilder(new InheritanceC());
    }

    public static InheritanceCBuilder existing(InheritanceC inheritanceC) {
        return new InheritanceCBuilder(inheritanceC);
    }

    public static InheritanceCFoo1Builder aInheritanceCFoo1() {
        return new InheritanceCFoo1Builder(new InheritanceCFoo1());
    }

    public static InheritanceCFoo1Builder existing(InheritanceCFoo1 inheritanceCFoo1) {
        return new InheritanceCFoo1Builder(inheritanceCFoo1);
    }

    public static InheritanceCFoo2Builder aInheritanceCFoo2() {
        return new InheritanceCFoo2Builder(new InheritanceCFoo2());
    }

    public static InheritanceCFoo2Builder existing(InheritanceCFoo2 inheritanceCFoo2) {
        return new InheritanceCFoo2Builder(inheritanceCFoo2);
    }

    public static ManyToManyABarBuilder aManyToManyABar() {
        return new ManyToManyABarBuilder(new ManyToManyABar());
    }

    public static ManyToManyABarBuilder existing(ManyToManyABar manyToManyABar) {
        return new ManyToManyABarBuilder(manyToManyABar);
    }

    public static ManyToManyAFooBuilder aManyToManyAFoo() {
        return new ManyToManyAFooBuilder(new ManyToManyAFoo());
    }

    public static ManyToManyAFooBuilder existing(ManyToManyAFoo manyToManyAFoo) {
        return new ManyToManyAFooBuilder(manyToManyAFoo);
    }

    public static ManyToManyAFooToBarBuilder aManyToManyAFooToBar() {
        return new ManyToManyAFooToBarBuilder(new ManyToManyAFooToBar());
    }

    public static ManyToManyAFooToBarBuilder existing(ManyToManyAFooToBar manyToManyAFooToBar) {
        return new ManyToManyAFooToBarBuilder(manyToManyAFooToBar);
    }

    public static ManyToManyBBarBuilder aManyToManyBBar() {
        return new ManyToManyBBarBuilder(new ManyToManyBBar());
    }

    public static ManyToManyBBarBuilder existing(ManyToManyBBar manyToManyBBar) {
        return new ManyToManyBBarBuilder(manyToManyBBar);
    }

    public static ManyToManyBFooBuilder aManyToManyBFoo() {
        return new ManyToManyBFooBuilder(new ManyToManyBFoo());
    }

    public static ManyToManyBFooBuilder existing(ManyToManyBFoo manyToManyBFoo) {
        return new ManyToManyBFooBuilder(manyToManyBFoo);
    }

    public static ManyToManyBFooToBarBuilder aManyToManyBFooToBar() {
        return new ManyToManyBFooToBarBuilder(new ManyToManyBFooToBar());
    }

    public static ManyToManyBFooToBarBuilder existing(ManyToManyBFooToBar manyToManyBFooToBar) {
        return new ManyToManyBFooToBarBuilder(manyToManyBFooToBar);
    }

    public static OneToOneABarBuilder aOneToOneABar() {
        return new OneToOneABarBuilder(new OneToOneABar());
    }

    public static OneToOneABarBuilder existing(OneToOneABar oneToOneABar) {
        return new OneToOneABarBuilder(oneToOneABar);
    }

    public static OneToOneAFooBuilder aOneToOneAFoo() {
        return new OneToOneAFooBuilder(new OneToOneAFoo());
    }

    public static OneToOneAFooBuilder existing(OneToOneAFoo oneToOneAFoo) {
        return new OneToOneAFooBuilder(oneToOneAFoo);
    }

    public static OneToOneBBarBuilder aOneToOneBBar() {
        return new OneToOneBBarBuilder(new OneToOneBBar());
    }

    public static OneToOneBBarBuilder existing(OneToOneBBar oneToOneBBar) {
        return new OneToOneBBarBuilder(oneToOneBBar);
    }

    public static OneToOneBFooBuilder aOneToOneBFoo() {
        return new OneToOneBFooBuilder(new OneToOneBFoo());
    }

    public static OneToOneBFooBuilder existing(OneToOneBFoo oneToOneBFoo) {
        return new OneToOneBFooBuilder(oneToOneBFoo);
    }

    public static ParentBuilder aParent() {
        return new ParentBuilder(new Parent());
    }

    public static ParentBuilder existing(Parent parent) {
        return new ParentBuilder(parent);
    }

    public static ParentBChildBarBuilder aParentBChildBar() {
        return new ParentBChildBarBuilder(new ParentBChildBar());
    }

    public static ParentBChildBarBuilder existing(ParentBChildBar parentBChildBar) {
        return new ParentBChildBarBuilder(parentBChildBar);
    }

    public static ParentBChildFooBuilder aParentBChildFoo() {
        return new ParentBChildFooBuilder(new ParentBChildFoo());
    }

    public static ParentBChildFooBuilder existing(ParentBChildFoo parentBChildFoo) {
        return new ParentBChildFooBuilder(parentBChildFoo);
    }

    public static ParentBParentBuilder aParentBParent() {
        return new ParentBParentBuilder(new ParentBParent());
    }

    public static ParentBParentBuilder existing(ParentBParent parentBParent) {
        return new ParentBParentBuilder(parentBParent);
    }

    public static ParentCBarBuilder aParentCBar() {
        return new ParentCBarBuilder(new ParentCBar());
    }

    public static ParentCBarBuilder existing(ParentCBar parentCBar) {
        return new ParentCBarBuilder(parentCBar);
    }

    public static ParentCFooBuilder aParentCFoo() {
        return new ParentCFooBuilder(new ParentCFoo());
    }

    public static ParentCFooBuilder existing(ParentCFoo parentCFoo) {
        return new ParentCFooBuilder(parentCFoo);
    }

    public static ParentDBuilder aParentD() {
        return new ParentDBuilder(new ParentD());
    }

    public static ParentDBuilder existing(ParentD parentD) {
        return new ParentDBuilder(parentD);
    }

    public static ParentDChildABuilder aParentDChildA() {
        return new ParentDChildABuilder(new ParentDChildA());
    }

    public static ParentDChildABuilder existing(ParentDChildA parentDChildA) {
        return new ParentDChildABuilder(parentDChildA);
    }

    public static ParentDChildBBuilder aParentDChildB() {
        return new ParentDChildBBuilder(new ParentDChildB());
    }

    public static ParentDChildBBuilder existing(ParentDChildB parentDChildB) {
        return new ParentDChildBBuilder(parentDChildB);
    }

    public static ParentDChildCBuilder aParentDChildC() {
        return new ParentDChildCBuilder(new ParentDChildC());
    }

    public static ParentDChildCBuilder existing(ParentDChildC parentDChildC) {
        return new ParentDChildCBuilder(parentDChildC);
    }

    public static ParentDToChildCBuilder aParentDToChildC() {
        return new ParentDToChildCBuilder(new ParentDToChildC());
    }

    public static ParentDToChildCBuilder existing(ParentDToChildC parentDToChildC) {
        return new ParentDToChildCBuilder(parentDToChildC);
    }

    public static PrimitivesBuilder aPrimitives() {
        return new PrimitivesBuilder(new Primitives());
    }

    public static PrimitivesBuilder existing(Primitives primitives) {
        return new PrimitivesBuilder(primitives);
    }

    public static PrimitivesBBuilder aPrimitivesB() {
        return new PrimitivesBBuilder(new PrimitivesB());
    }

    public static PrimitivesBBuilder existing(PrimitivesB primitivesB) {
        return new PrimitivesBBuilder(primitivesB);
    }

    public static PrimitivesCBuilder aPrimitivesC() {
        return new PrimitivesCBuilder(new PrimitivesC());
    }

    public static PrimitivesCBuilder existing(PrimitivesC primitivesC) {
        return new PrimitivesCBuilder(primitivesC);
    }

    public static UserTypesAFooBuilder aUserTypesAFoo() {
        return new UserTypesAFooBuilder(new UserTypesAFoo());
    }

    public static UserTypesAFooBuilder existing(UserTypesAFoo userTypesAFoo) {
        return new UserTypesAFooBuilder(userTypesAFoo);
    }

    public static ValidationAFooBuilder aValidationAFoo() {
        return new ValidationAFooBuilder(new ValidationAFoo());
    }

    public static ValidationAFooBuilder existing(ValidationAFoo validationAFoo) {
        return new ValidationAFooBuilder(validationAFoo);
    }

}
