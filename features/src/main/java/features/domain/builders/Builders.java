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

    public static CodeADomainObjectBuilder aCodeADomainObject() {
        return new CodeADomainObjectBuilder(new CodeADomainObject());
    }

    public static GrandChildBuilder aGrandChild() {
        return new GrandChildBuilder(new GrandChild());
    }

    public static InheritanceABaseBuilder aInheritanceABase() {
        return new InheritanceABaseBuilder(new InheritanceABase());
    }

    public static InheritanceASubOneBuilder aInheritanceASubOne() {
        return new InheritanceASubOneBuilder(new InheritanceASubOne());
    }

    public static InheritanceASubTwoBuilder aInheritanceASubTwo() {
        return new InheritanceASubTwoBuilder(new InheritanceASubTwo());
    }

    public static InheritanceBBottomBuilder aInheritanceBBottom() {
        return new InheritanceBBottomBuilder(new InheritanceBBottom());
    }

    public static InheritanceBMiddleBuilder aInheritanceBMiddle() {
        return new InheritanceBMiddleBuilder(new InheritanceBMiddle());
    }

    public static InheritanceBRootBuilder aInheritanceBRoot() {
        return new InheritanceBRootBuilder(new InheritanceBRoot());
    }

    public static InheritanceBRootChildBuilder aInheritanceBRootChild() {
        return new InheritanceBRootChildBuilder(new InheritanceBRootChild());
    }

    public static InheritanceCBuilder aInheritanceC() {
        return new InheritanceCBuilder(new InheritanceC());
    }

    public static InheritanceCFoo1Builder aInheritanceCFoo1() {
        return new InheritanceCFoo1Builder(new InheritanceCFoo1());
    }

    public static InheritanceCFoo2Builder aInheritanceCFoo2() {
        return new InheritanceCFoo2Builder(new InheritanceCFoo2());
    }

    public static ManyToManyABarBuilder aManyToManyABar() {
        return new ManyToManyABarBuilder(new ManyToManyABar());
    }

    public static ManyToManyAFooBuilder aManyToManyAFoo() {
        return new ManyToManyAFooBuilder(new ManyToManyAFoo());
    }

    public static ManyToManyAFooToBarBuilder aManyToManyAFooToBar() {
        return new ManyToManyAFooToBarBuilder(new ManyToManyAFooToBar());
    }

    public static ManyToManyBBarBuilder aManyToManyBBar() {
        return new ManyToManyBBarBuilder(new ManyToManyBBar());
    }

    public static ManyToManyBFooBuilder aManyToManyBFoo() {
        return new ManyToManyBFooBuilder(new ManyToManyBFoo());
    }

    public static ManyToManyBFooToBarBuilder aManyToManyBFooToBar() {
        return new ManyToManyBFooToBarBuilder(new ManyToManyBFooToBar());
    }

    public static OneToOneABarBuilder aOneToOneABar() {
        return new OneToOneABarBuilder(new OneToOneABar());
    }

    public static OneToOneAFooBuilder aOneToOneAFoo() {
        return new OneToOneAFooBuilder(new OneToOneAFoo());
    }

    public static OneToOneBBarBuilder aOneToOneBBar() {
        return new OneToOneBBarBuilder(new OneToOneBBar());
    }

    public static OneToOneBFooBuilder aOneToOneBFoo() {
        return new OneToOneBFooBuilder(new OneToOneBFoo());
    }

    public static ParentBuilder aParent() {
        return new ParentBuilder(new Parent());
    }

    public static ParentBChildBarBuilder aParentBChildBar() {
        return new ParentBChildBarBuilder(new ParentBChildBar());
    }

    public static ParentBChildFooBuilder aParentBChildFoo() {
        return new ParentBChildFooBuilder(new ParentBChildFoo());
    }

    public static ParentBParentBuilder aParentBParent() {
        return new ParentBParentBuilder(new ParentBParent());
    }

    public static ParentCBarBuilder aParentCBar() {
        return new ParentCBarBuilder(new ParentCBar());
    }

    public static ParentCFooBuilder aParentCFoo() {
        return new ParentCFooBuilder(new ParentCFoo());
    }

    public static ParentDBuilder aParentD() {
        return new ParentDBuilder(new ParentD());
    }

    public static ParentDChildABuilder aParentDChildA() {
        return new ParentDChildABuilder(new ParentDChildA());
    }

    public static ParentDChildBBuilder aParentDChildB() {
        return new ParentDChildBBuilder(new ParentDChildB());
    }

    public static ParentDChildCBuilder aParentDChildC() {
        return new ParentDChildCBuilder(new ParentDChildC());
    }

    public static ParentDToChildCBuilder aParentDToChildC() {
        return new ParentDToChildCBuilder(new ParentDToChildC());
    }

    public static PrimitivesBuilder aPrimitives() {
        return new PrimitivesBuilder(new Primitives());
    }

    public static PrimitivesBBuilder aPrimitivesB() {
        return new PrimitivesBBuilder(new PrimitivesB());
    }

    public static PrimitivesCBuilder aPrimitivesC() {
        return new PrimitivesCBuilder(new PrimitivesC());
    }

    public static UserTypesAFooBuilder aUserTypesAFoo() {
        return new UserTypesAFooBuilder(new UserTypesAFoo());
    }

    public static ValidationAFooBuilder aValidationAFoo() {
        return new ValidationAFooBuilder(new ValidationAFoo());
    }

}
