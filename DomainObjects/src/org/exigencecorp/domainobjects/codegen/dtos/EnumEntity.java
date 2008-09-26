package org.exigencecorp.domainobjects.codegen.dtos;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.codegen.Codegen;

public class EnumEntity extends Entity {

    private List<EnumCode> codes = new ArrayList<EnumCode>();

    public EnumEntity(Codegen codegen, String tableName) {
        super(codegen, tableName);
    }

    @Override
    public boolean isEnum() {
        return true;
    }

    public void addCode(String id, String code, String name) {
        this.codes.add(new EnumCode(id, code, name));
    }

    public List<EnumCode> getCodes() {
        return this.codes;
    }

}
