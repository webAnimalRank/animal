package com.example.animal.config;

import com.example.animal.type.VillagerType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component; // Import for @Component

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component // Make it a Spring component for auto-registration
@MappedTypes(VillagerType.class)
@MappedJdbcTypes(JdbcType.TINYINT) // Maps to TINYINT in the database
public class VillagerTypeHandler extends BaseTypeHandler<VillagerType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, VillagerType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public VillagerType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return VillagerType.fromCode(code);
    }

    @Override
    public VillagerType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return VillagerType.fromCode(code);
    }

    @Override
    public VillagerType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return VillagerType.fromCode(code);
    }
}
