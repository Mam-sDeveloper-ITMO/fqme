package fqme.column.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;

public class ByteArrayColumn extends Column<ByteArrayColumn, byte[]> {
    public ByteArrayColumn(String name) {
        super(name);
    }

    public static ByteArrayColumn of(String name) {
        return new ByteArrayColumn(name);
    }

    @Override
    protected String _getSqlDefinition() {
        return "BYTEA";
    }

    @Override
    public byte[] fromSqlType(Object value) throws UnsupportedSqlType {
        // TODO Auto-generated method stub
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        throw new UnsupportedSqlType(String.format("Expected byte[] got %s instead.", value.getClass().getName()));
    }

    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null.");
            }
            statement.setNull(index, java.sql.Types.BINARY);
        } else if (value instanceof byte[]) {
            statement.setBytes(index, (byte[]) value);
        } else {
            throw new UnsupportedValueType(
                    String.format("Expected byte[] got %s instead.", value.getClass().getName()));
        }

    }
}
