package fqme.column.common.numeric;

/**
 * A column that stores real numbers.
 */
public class RealColumn extends NumericColumn<Float> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    protected RealColumn(String name) {
        super(name, Float.class);
    }

    /**
     * Factory method for creating a column.
     */
    public static RealColumn of(String name) {
        return new RealColumn(name);
    }
}
