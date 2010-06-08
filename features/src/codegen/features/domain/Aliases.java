package features.domain;

import joist.domain.orm.AliasRegistry;

public class Aliases {

    public static final ChildAlias child = new ChildAlias("a");
    public static final CodeADomainObjectAlias codeADomainObject = new CodeADomainObjectAlias("a");
    public static final InheritanceABaseAlias inheritanceABase = new InheritanceABaseAlias("a");
    public static final InheritanceASubOneAlias inheritanceASubOne = new InheritanceASubOneAlias("a");
    public static final InheritanceASubTwoAlias inheritanceASubTwo = new InheritanceASubTwoAlias("a");
    public static final InheritanceBBottomAlias inheritanceBBottom = new InheritanceBBottomAlias("a");
    public static final InheritanceBMiddleAlias inheritanceBMiddle = new InheritanceBMiddleAlias("a");
    public static final InheritanceBRootAlias inheritanceBRoot = new InheritanceBRootAlias("a");
    public static final InheritanceBRootChildAlias inheritanceBRootChild = new InheritanceBRootChildAlias("a");
    public static final InheritanceCAlias inheritanceC = new InheritanceCAlias("a");
    public static final InheritanceCFoo1Alias inheritanceCFoo1 = new InheritanceCFoo1Alias("a");
    public static final InheritanceCFoo2Alias inheritanceCFoo2 = new InheritanceCFoo2Alias("a");
    public static final ManyToManyABarAlias manyToManyABar = new ManyToManyABarAlias("a");
    public static final ManyToManyAFooAlias manyToManyAFoo = new ManyToManyAFooAlias("a");
    public static final ManyToManyAFooToBarAlias manyToManyAFooToBar = new ManyToManyAFooToBarAlias("a");
    public static final ManyToManyBBarAlias manyToManyBBar = new ManyToManyBBarAlias("a");
    public static final ManyToManyBFooAlias manyToManyBFoo = new ManyToManyBFooAlias("a");
    public static final ManyToManyBFooToBarAlias manyToManyBFooToBar = new ManyToManyBFooToBarAlias("a");
    public static final OneToOneABarAlias oneToOneABar = new OneToOneABarAlias("a");
    public static final OneToOneAFooAlias oneToOneAFoo = new OneToOneAFooAlias("a");
    public static final OneToOneBBarAlias oneToOneBBar = new OneToOneBBarAlias("a");
    public static final OneToOneBFooAlias oneToOneBFoo = new OneToOneBFooAlias("a");
    public static final ParentAlias parent = new ParentAlias("a");
    public static final ParentBChildBarAlias parentBChildBar = new ParentBChildBarAlias("a");
    public static final ParentBChildFooAlias parentBChildFoo = new ParentBChildFooAlias("a");
    public static final ParentBParentAlias parentBParent = new ParentBParentAlias("a");
    public static final ParentCBarAlias parentCBar = new ParentCBarAlias("a");
    public static final ParentCFooAlias parentCFoo = new ParentCFooAlias("a");
    public static final ParentDAlias parentD = new ParentDAlias("a");
    public static final ParentDChildAAlias parentDChildA = new ParentDChildAAlias("a");
    public static final ParentDChildBAlias parentDChildB = new ParentDChildBAlias("a");
    public static final ParentDChildCAlias parentDChildC = new ParentDChildCAlias("a");
    public static final ParentDToChildCAlias parentDToChildC = new ParentDToChildCAlias("a");
    public static final PrimitivesAlias primitives = new PrimitivesAlias("a");
    public static final PrimitivesBAlias primitivesB = new PrimitivesBAlias("a");
    public static final PrimitivesCAlias primitivesC = new PrimitivesCAlias("a");
    public static final UserTypesAFooAlias userTypesAFoo = new UserTypesAFooAlias("a");
    public static final ValidationAFooAlias validationAFoo = new ValidationAFooAlias("a");

    static {
        AliasRegistry.register(Child.class, child);
        AliasRegistry.register(CodeADomainObject.class, codeADomainObject);
        AliasRegistry.register(InheritanceABase.class, inheritanceABase);
        AliasRegistry.register(InheritanceASubOne.class, inheritanceASubOne);
        AliasRegistry.register(InheritanceASubTwo.class, inheritanceASubTwo);
        AliasRegistry.register(InheritanceBBottom.class, inheritanceBBottom);
        AliasRegistry.register(InheritanceBMiddle.class, inheritanceBMiddle);
        AliasRegistry.register(InheritanceBRoot.class, inheritanceBRoot);
        AliasRegistry.register(InheritanceBRootChild.class, inheritanceBRootChild);
        AliasRegistry.register(InheritanceC.class, inheritanceC);
        AliasRegistry.register(InheritanceCFoo1.class, inheritanceCFoo1);
        AliasRegistry.register(InheritanceCFoo2.class, inheritanceCFoo2);
        AliasRegistry.register(ManyToManyABar.class, manyToManyABar);
        AliasRegistry.register(ManyToManyAFoo.class, manyToManyAFoo);
        AliasRegistry.register(ManyToManyAFooToBar.class, manyToManyAFooToBar);
        AliasRegistry.register(ManyToManyBBar.class, manyToManyBBar);
        AliasRegistry.register(ManyToManyBFoo.class, manyToManyBFoo);
        AliasRegistry.register(ManyToManyBFooToBar.class, manyToManyBFooToBar);
        AliasRegistry.register(OneToOneABar.class, oneToOneABar);
        AliasRegistry.register(OneToOneAFoo.class, oneToOneAFoo);
        AliasRegistry.register(OneToOneBBar.class, oneToOneBBar);
        AliasRegistry.register(OneToOneBFoo.class, oneToOneBFoo);
        AliasRegistry.register(Parent.class, parent);
        AliasRegistry.register(ParentBChildBar.class, parentBChildBar);
        AliasRegistry.register(ParentBChildFoo.class, parentBChildFoo);
        AliasRegistry.register(ParentBParent.class, parentBParent);
        AliasRegistry.register(ParentCBar.class, parentCBar);
        AliasRegistry.register(ParentCFoo.class, parentCFoo);
        AliasRegistry.register(ParentD.class, parentD);
        AliasRegistry.register(ParentDChildA.class, parentDChildA);
        AliasRegistry.register(ParentDChildB.class, parentDChildB);
        AliasRegistry.register(ParentDChildC.class, parentDChildC);
        AliasRegistry.register(ParentDToChildC.class, parentDToChildC);
        AliasRegistry.register(Primitives.class, primitives);
        AliasRegistry.register(PrimitivesB.class, primitivesB);
        AliasRegistry.register(PrimitivesC.class, primitivesC);
        AliasRegistry.register(UserTypesAFoo.class, userTypesAFoo);
        AliasRegistry.register(ValidationAFoo.class, validationAFoo);
    }

    public static void init() {
    }

}
