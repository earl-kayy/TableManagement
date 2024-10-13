package database;

import java.util.function.Predicate;

public interface Table extends Joinable {
    // Return name of the table
    String getName();

    // Print the table header and contents.
    void show();

    // Print the description of the table
    void describe();

    /**
     * @return Creates and returns a new Table consisting of the first (up to) 5 rows.
     */
    Table head();
    /**
     * @return Creates and returns a new Table consisting of the first (up to) lineCount rows.
     */
    Table head(int lineCount);

    /**
     * @return Creates and returns a new Table consisting of the last (up to) 5 rows.
     */
    Table tail();
    /**
     * @return Creates and returns a new Table consisting of the last (up to) lineCount rows.
     */
    Table tail(int lineCount);

    /**
     * @param beginIndex Inclusive (greater than or equal to)
     * @param endIndex Exclusive (less than)
     * @return Creates and returns a new Table consisting of rows within the specified range.
     *         Throws an exception if a non-existent row index is provided.
     */
    Table selectRows(int beginIndex, int endIndex);

    /**
     * @return Creates and returns a new Table consisting of rows corresponding to the specified indices.
     *         The order of rows in the result table follows the order of the provided indices.
     *         Throws an exception if a non-existent row index is provided.
     */
    Table selectRowsAt(int ...indices);

    /**
     * @param beginIndex Inclusive (greater than or equal to)
     * @param endIndex Exclusive (less than)
     * @return Creates and returns a new Table consisting of columns within the specified range.
     *         Throws an exception if a non-existent column index is provided.
     */
    Table selectColumns(int beginIndex, int endIndex);

    /**
     * @return Creates and returns a new Table consisting of columns corresponding to the specified indices.
     *         The order of columns in the result table follows the order of the provided indices.
     *         Throws an exception if a non-existent column index is provided.
     */
    Table selectColumnsAt(int ...indices);

    /**
     * @param
     * @return Creates and returns a new Table consisting of rows that match the search conditions.
     *         Attempt implementing this last.
     */
    <T> Table selectRowsBy(String columnName, Predicate<T> predicate);

    /**
     * @return The original Table is sorted and returned.
     * @param byIndexOfColumn The column index to sort by. Throws an exception if the column index is invalid.
     */
    Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst);

    int getRowCount();
    int getColumnCount();

    /**
     * @return The original Column is returned. Any modification to the returned Column will affect the original Table.
     */
    Column getColumn(int index);
    /**
     * @return The original Column is returned. Therefore, any modification to the returned Column will affect the original Table.
     */
    Column getColumn(String name);
}
