package fqme.column.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.model.Model;
import fqme.model.reflection.ModelReflection;

/**
 * Column realization for foreign key values.
 * Allow to reference only to one column of integer type.
 */
public class ForeignColumn extends Column<ForeignColumn, Integer> {
    /**
     * Name of the table to reference.
     */
    private final String tableName;

    /**
     * Name of the column to reference.
     */
    private final String columnName;

    /**
     * Default constructor.
     *
     * @param name       name of the column.
     * @param modelClass class of the model to reference.
     * @param column     column to reference.
     */
    public <M extends Model<M>> ForeignColumn(String name, Class<M> modelClass, Column<?, Integer> column) {
        super(name);

        this.tableName = ModelReflection.buildTableName(modelClass);
        this.columnName = column.getName();
    }

    /**
     * Factory method for creating a column.
     */
    public static <M extends Model<M>> ForeignColumn of(String name, Class<M> modelClass,
            Column<?, Integer> column) {
        return new ForeignColumn(name, modelClass, column);
    }

    /**
     * Return sql query for this column.
     *
     * @return reference for specified table and column
     */
    @Override
    protected String _getSqlDefinition() {
        return String.format("INTEGER REFERENCES %s (%s)", tableName, columnName);
    }

    /**
     * Return sql query for this column.
     *
     * @return reference for specified table and column
     */
    @Override
    public Integer fromSqlType(Object value) throws UnsupportedSqlType {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else
        if (value instanceof Integer) {
            return (Integer) value;
        }
        throw new UnsupportedSqlType(String.format("Cannot convert value '%s' to Integer", value));
    }

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     value to set.
     * @throws UnsupportedValueType if value cannot be converted.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null");
            }
            statement.setNull(index, java.sql.Types.INTEGER);
        } else if (value instanceof Integer) {
            statement.setInt(index, (Integer) value);
        } else {
            throw new UnsupportedValueType(String.format("Cannot convert value '%s' to Integer", value));
        }
    }
}
