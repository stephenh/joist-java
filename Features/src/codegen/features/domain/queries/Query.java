package features.domain.queries;

public class Query {

    public static final features.domain.queries.PrimitivesQueries primitives = new PrimitivesQueries();
    public static final features.domain.queries.ParentQueries parent = new ParentQueries();
    public static final features.domain.queries.ChildQueries child = new ChildQueries();
    public static final features.domain.queries.InheritanceABaseQueries inheritanceABase = new InheritanceABaseQueries();
    public static final features.domain.queries.InheritanceASubOneQueries inheritanceASubOne = new InheritanceASubOneQueries();
    public static final features.domain.queries.InheritanceASubTwoQueries inheritanceASubTwo = new InheritanceASubTwoQueries();
    public static final features.domain.queries.CodeADomainObjectQueries codeADomainObject = new CodeADomainObjectQueries();
    public static final features.domain.queries.ParentBParentQueries parentBParent = new ParentBParentQueries();
    public static final features.domain.queries.ParentBChildFooQueries parentBChildFoo = new ParentBChildFooQueries();
    public static final features.domain.queries.ParentBChildBarQueries parentBChildBar = new ParentBChildBarQueries();
    public static final features.domain.queries.InheritanceBRootQueries inheritanceBRoot = new InheritanceBRootQueries();
    public static final features.domain.queries.InheritanceBRootChildQueries inheritanceBRootChild = new InheritanceBRootChildQueries();
    public static final features.domain.queries.InheritanceBMiddleQueries inheritanceBMiddle = new InheritanceBMiddleQueries();
    public static final features.domain.queries.InheritanceBBottomQueries inheritanceBBottom = new InheritanceBBottomQueries();
    public static final features.domain.queries.ManyToManyAFooQueries manyToManyAFoo = new ManyToManyAFooQueries();
    public static final features.domain.queries.ManyToManyABarQueries manyToManyABar = new ManyToManyABarQueries();
    public static final features.domain.queries.ManyToManyAFooToBarQueries manyToManyAFooToBar = new ManyToManyAFooToBarQueries();
    public static final features.domain.queries.UserTypesAFooQueries userTypesAFoo = new UserTypesAFooQueries();
    public static final features.domain.queries.ValidationAFooQueries validationAFoo = new ValidationAFooQueries();

}
