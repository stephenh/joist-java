package features.domain;

import joist.domain.orm.AliasRegistry;

public class Aliases {

    public static final ChildAlias child = new ChildAlias("c");
    public static final CodeADomainObjectAlias codeADomainObject = new CodeADomainObjectAlias("cado");
    public static final GrandChildAlias grandChild = new GrandChildAlias("gc");
    public static final InheritanceABaseAlias inheritanceABase = new InheritanceABaseAlias("iab");
    public static final InheritanceASubOneAlias inheritanceASubOne = new InheritanceASubOneAlias("iaso");
    public static final InheritanceASubTwoAlias inheritanceASubTwo = new InheritanceASubTwoAlias("iast");
    public static final InheritanceBBottomAlias inheritanceBBottom = new InheritanceBBottomAlias("ibb");
    public static final InheritanceBMiddleAlias inheritanceBMiddle = new InheritanceBMiddleAlias("ibm");
    public static final InheritanceBRootAlias inheritanceBRoot = new InheritanceBRootAlias("ibr");
    public static final InheritanceBRootChildAlias inheritanceBRootChild = new InheritanceBRootChildAlias("ibrc");
    public static final InheritanceCAlias inheritanceC = new InheritanceCAlias("ic");
    public static final InheritanceCFoo1Alias inheritanceCFoo1 = new InheritanceCFoo1Alias("icf");
    public static final InheritanceCFoo2Alias inheritanceCFoo2 = new InheritanceCFoo2Alias("icf");
    public static final ManyToManyABarAlias manyToManyABar = new ManyToManyABarAlias("mtmab");
    public static final ManyToManyAFooAlias manyToManyAFoo = new ManyToManyAFooAlias("mtmaf");
    public static final ManyToManyAFooToBarAlias manyToManyAFooToBar = new ManyToManyAFooToBarAlias("mtmaftb");
    public static final ManyToManyBBarAlias manyToManyBBar = new ManyToManyBBarAlias("mtmbb");
    public static final ManyToManyBFooAlias manyToManyBFoo = new ManyToManyBFooAlias("mtmbf");
    public static final ManyToManyBFooToBarAlias manyToManyBFooToBar = new ManyToManyBFooToBarAlias("mtmbftb");
    public static final OneToOneABarAlias oneToOneABar = new OneToOneABarAlias("otoab");
    public static final OneToOneAFooAlias oneToOneAFoo = new OneToOneAFooAlias("otoaf");
    public static final OneToOneBBarAlias oneToOneBBar = new OneToOneBBarAlias("otobb");
    public static final OneToOneBFooAlias oneToOneBFoo = new OneToOneBFooAlias("otobf");
    public static final ParentAlias parent = new ParentAlias("p");
    public static final ParentBChildBarAlias parentBChildBar = new ParentBChildBarAlias("pbcb");
    public static final ParentBChildFooAlias parentBChildFoo = new ParentBChildFooAlias("pbcf");
    public static final ParentBParentAlias parentBParent = new ParentBParentAlias("pbp");
    public static final ParentCBarAlias parentCBar = new ParentCBarAlias("pcb");
    public static final ParentCFooAlias parentCFoo = new ParentCFooAlias("pcf");
    public static final ParentDAlias parentD = new ParentDAlias("pd");
    public static final ParentDChildAAlias parentDChildA = new ParentDChildAAlias("pdca");
    public static final ParentDChildBAlias parentDChildB = new ParentDChildBAlias("pdcb");
    public static final ParentDChildCAlias parentDChildC = new ParentDChildCAlias("pdcc");
    public static final ParentDToChildCAlias parentDToChildC = new ParentDToChildCAlias("pdtcc");
    public static final PrimitivesAlias primitives = new PrimitivesAlias("p");
    public static final PrimitivesBAlias primitivesB = new PrimitivesBAlias("pb");
    public static final PrimitivesCAlias primitivesC = new PrimitivesCAlias("pc");
    public static final UserTypesAFooAlias userTypesAFoo = new UserTypesAFooAlias("utaf");
    public static final ValidationAFooAlias validationAFoo = new ValidationAFooAlias("vaf");

    static {
        AliasRegistry.register(Child.class, child);
        AliasRegistry.register(CodeADomainObject.class, codeADomainObject);
        AliasRegistry.register(GrandChild.class, grandChild);
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
