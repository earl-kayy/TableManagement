package database;

public interface Column {
    String getHeader();

    /* cell value -> String */
    String getValue(int index);

    /**
     * @param index
     * @param t Double.class, Long.class, Integer.class
     * @return Returns the cell value as type t, or null if the cell value is null.
     *         Throws an exception if the value cannot be converted to type t.
     */
    <T extends Number> T getValue(int index, Class<T> t);

    void setValue(int index, String value);

    /**
     * @param value Used when passing an int literal as the cell value at the specified index.
     */
    void setValue(int index, int value);

    /**
     * @return Returns the total number of cells, including null values.
     */
    int count();

    void show();

    /**
     * @return Returns true if the column consists of (int or null) or (double or null) values.
     */
    boolean isNumericColumn();

    long getNullCount();
}
