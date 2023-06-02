package fqme.column.common.numeric;

/**
 * A column that stores integer numbers.
 */
public class IntegerColumn extends NumericColumn<Integer> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    protected IntegerColumn(String name) {
        super(name, Integer.class);
    }

    /**
     * Factory method for creating a column.
     */
    public static IntegerColumn of(String name) {
        return new IntegerColumn(name);
    }
}
