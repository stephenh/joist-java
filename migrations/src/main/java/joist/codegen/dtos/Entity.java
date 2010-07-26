package joist.codegen.dtos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import joist.codegen.Codegen;
import joist.codegen.CodegenConfig;
import joist.util.Copy;
import joist.util.Inflector;

import org.apache.commons.lang.StringUtils;

/** Represents a domain object we will generate code for. */
public class Entity {

    protected CodegenConfig config;
    private String tableName;
    private Entity baseEntity;
    private List<Entity> subEntities = new ArrayList<Entity>();
    private List<PrimitiveProperty> primitiveProperties = new ArrayList<PrimitiveProperty>();
    private List<ManyToOneProperty> manyToOneProperties = new ArrayList<ManyToOneProperty>();
    private List<OneToManyProperty> oneToManyProperties = new ArrayList<OneToManyProperty>();
    private List<ManyToManyProperty> manyToManyProperties = new ArrayList<ManyToManyProperty>();
    private Set<String> imports = new TreeSet<String>();

    public Entity(Codegen codegen, String tableName) {
        this.config = codegen.getConfig();
        this.tableName = tableName;
    }

    public String getParentClassName() {
        if (this.isSubclass()) {
            return this.baseEntity.getClassName();
        } else {
            return this.config.getDomainObjectBaseClass();
        }
    }

    public boolean hasSubclasses() {
        return this.subEntities.size() > 0;
    }

    public CodegenConfig getConfig() {
        return this.config;
    }

    public boolean isRoot() {
        return this.baseEntity == null && !this.isCodeEntity();
    }

    public boolean isAbstract() {
        return this.hasSubclasses() && !this.config.isNotAbstractEvenThoughSubclassed(this.tableName);
    }

    public boolean isConcrete() {
        return !this.isAbstract();
    }

    public boolean isSubclass() {
        return this.getBaseEntity() != null;
    }

    public boolean isCodeEntity() {
        return false;
    }

    public String getClassName() {
        return Inflector.camelize(this.getTableName());
    }

    public String getAliasName() {
        return this.getClassName() + "Alias";
    }

    public String getVariableName() {
        return StringUtils.uncapitalize(this.getClassName());
    }

    public String getCodegenClassName() {
        return this.getClassName() + "Codegen";
    }

    public String getQueriesCodegenClassName() {
        return this.getClassName() + "QueriesCodegen";
    }

    public String getFullCodegenClassName() {
        return this.config.getDomainObjectPackage() + "." + this.getCodegenClassName();
    }

    public String getFullAliasClassName() {
        return this.config.getDomainObjectPackage() + "." + this.getClassName() + "Alias";
    }

    public String getFullClassName() {
        return this.config.getDomainObjectPackage() + "." + this.getClassName();
    }

    public String getFullQueriesClassName() {
        return this.config.getQueriesPackage() + "." + this.getClassName() + "Queries";
    }

    public String getFullQueriesCodegenClassName() {
        return this.config.getQueriesPackage() + "." + this.getQueriesCodegenClassName();
    }

    public List<PrimitiveProperty> getPrimitiveProperties() {
        return this.primitiveProperties;
    }

    public List<ManyToOneProperty> getManyToOneProperties() {
        return this.manyToOneProperties;
    }

    public List<OneToManyProperty> getOneToManyProperties() {
        return this.oneToManyProperties;
    }

    public List<ManyToManyProperty> getManyToManyProperties() {
        return this.manyToManyProperties;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Set<String> getImports() {
        return this.imports;
    }

    public Entity getBaseEntity() {
        return this.baseEntity;
    }

    public Entity getRootEntity() {
        Entity current = this;
        while (current.getBaseEntity() != null) {
            current = current.getBaseEntity();
        }
        return current;
    }

    public void setBaseEntity(Entity baseObject) {
        this.baseEntity = baseObject;
        // In Class Table Inheritance, the id column overlaps in each table, but we only want it in the root class
        for (Iterator<PrimitiveProperty> i = this.getPrimitiveProperties().iterator(); i.hasNext();) {
            if (i.next().getColumnName().equals("id")) {
                i.remove();
            }
        }
    }

    public void addSubEntity(Entity subEntity) {
        this.subEntities.add(subEntity);
    }

    public List<Entity> getSubEntities() {
        return this.subEntities;
    }

    public List<Entity> getSubEntitiesRecursively() {
        List<Entity> ret = new ArrayList<Entity>();
        List<Entity> walk = Copy.list(this.getSubEntities());
        while (walk.size() != 0) {
            Entity sub = walk.remove(0);
            ret.add(sub);
            walk.addAll(sub.getSubEntities());
        }
        return ret;
    }

    public String toString() {
        return this.getClassName();
    }

}
