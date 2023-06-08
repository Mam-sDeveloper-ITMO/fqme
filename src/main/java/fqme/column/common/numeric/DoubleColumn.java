package fqme.column.common.numeric;

/**
 * A column that stores double numbers.
 */
public class DoubleColumn extends NumericColumn<Double> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public DoubleColumn(String name) {
        super(name, Double.class);
    }

    /**
     * Factory method for creating a column.
     */
    public static DoubleColumn of(String name) {
        return new DoubleColumn(name);
    }
}
