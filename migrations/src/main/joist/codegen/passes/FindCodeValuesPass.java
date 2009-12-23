package joist.codegen.passes;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.codegen.Codegen;
import joist.codegen.dtos.CodeEntity;
import joist.codegen.dtos.Entity;
import joist.jdbc.Jdbc;
import joist.jdbc.RowMapper;

public class FindCodeValuesPass implements Pass {

    public void pass(Codegen codegen) {
        for (Entity entity : codegen.getEntities().values()) {
            if (entity.isCodeEntity()) {
                final CodeEntity e = (CodeEntity) entity;
                Jdbc.query(codegen.getDataSource(), "select id, code, name from " + e.getTableName() + " order by id", new RowMapper() {
                    public void mapRow(ResultSet rs) throws SQLException {
                        e.addCode(rs.getString("id"), rs.getString("code"), rs.getString("name"));
                    }
                });
            }
        }
    }

}
