package features.domain;

import org.exigencecorp.domainobjects.uow.UoW;

public class ManyToManyAFoo extends ManyToManyAFooCodegen {

    public void removeManyToManyABar(ManyToManyABar o) {
        for (ManyToManyAFooToBar a : this.getManyToManyAFooToBars()) {
            if (a.getManyToManyABar().equals(o)) {
                a.setManyToManyAFoo(null);
                a.setManyToManyABar(null);
                UoW.getCurrent().delete(a);
            }
        }
    }

}
