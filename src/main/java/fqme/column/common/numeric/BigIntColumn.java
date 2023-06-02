package fqme.column.common.numeric;

/**
 * A column that stores bigint numbers.
 */
public class BigIntColumn extends NumericColumn<Long> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    private BigIntColumn(String name) {
        super(name, Long.class);
    }

    /**
     * Factory method for creating a column.
     */
    public static BigIntColumn of(String name) {
        return new BigIntColumn(name);
    }
}
