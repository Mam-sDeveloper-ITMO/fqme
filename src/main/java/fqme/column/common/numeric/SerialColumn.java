package fqme.column.common.numeric;

import java.sql.PreparedStatement;

/**
 * Column realization for serial values.
 */
public class SerialColumn extends IntegerColumn {
    /**
     * Define if the column can be null.
     *
     * Nullable columns, skipped in the insert queries
     * if the value is null.
     */
    public Boolean isNullable() {
        return true;
    }

    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    protected SerialColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static SerialColumn of(String name) {
        return new SerialColumn(name);
    }

    /**
     * Return sql type of the column.
     *
     * @return SERIAL.
     */
    @Override
    public String _getSqlDefinition() {
        return "SERIAL";
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value expect Integer
     * @return value converted to the java Integer type.
     */
    @Override
    public Integer fromSqlType(Object value) {
        return (Integer) value;
    }

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     expect Integer value.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value) throws Exception {
        statement.setInt(index, (Integer) value);
    }
}
