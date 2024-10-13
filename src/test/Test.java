package test;

import database.Database;
import database.JoinColumn;
import database.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
//        1) Creating Table object from csv
        Database.createTable(new File("rsc/authors.csv"));
        Database.createTable(new File("rsc/editors.csv"));
        Database.createTable(new File("rsc/translators.csv"));
        Database.createTable(new File("rsc/books.csv"));

//        2) print lists of tables
//        Database.showTables();

//        3) get table using the name of the table
        Table books = Database.getTable("books");
        Table authors = Database.getTable("authors");
        Table editors = Database.getTable("editors");
        Table translators = Database.getTable("translators");
//
        Table testTable = books;

//        4) print table
//        testTable.show();

//        5) print description of the table
        //<database.Table@59922c9>
        //RangeIndex : 8 entries, 0 to 7
        //Data columns (total 6 columns):
        //#|    Column|Non-Null Count | Dtype
        //0
        //1
        //...
        //dtypes: int(4), String(2)
//        testTable.describe();

        Table headTable;

////        5) print first 5 rows
//        testTable.head().show();
//        headTable = testTable.head();
//        System.out.println("identity test for head(): " + (testTable.equals(headTable) ? "Fail" : "Pass"));

//        6) print the first n rows
//        testTable.head(10).show();
//        headTable = testTable.head(10);
//        System.out.println("identity test for head(n): " + (testTable.equals(headTable) ? "Fail" : "Pass"));

        Table tailTable;

//        7) print last 5 rows
//        testTable.tail().show();
//        tailTable = testTable.tail();
//        System.out.println("identity test for tail(): " + (testTable.equals(tailTable) ? "Fail" : "Pass"));

//        8) print last n rows
//        testTable.tail(10).show();
//        tailTable = testTable.tail(10);
//        System.out.println("identity test for tail(n): " + (testTable.equals(tailTable) ? "Fail" : "Pass"));

        Table selectedRowsTable;

//        9) print selected range of rows
//        testTable.selectRows(1, 3).show();
//        selectedRowsTable = testTable.selectRows(0, 5);
//        System.out.println("identity test for selectRows(range): " + (testTable.equals(selectedRowsTable) ? "Fail" : "Pass"));

//        10) print selected rows
//        testTable.selectRowsAt(7, 0, 4).show();
//        selectedRowsTable = testTable.selectRowsAt(7, 0, 4);
//        System.out.println("identity test for selectRowsAt(indices): " + (testTable.equals(selectedRowsTable) ? "Fail" : "Pass"));

        Table selectedColumnsTable;

//        11) print selected range of columns
//        testTable.selectColumns(0, 4).show();
//        selectedColumnsTable = testTable.selectColumns(0, 4);
//        System.out.println("identity test for selectColumns(range): " + (testTable.equals(selectedColumnsTable) ? "Fail" : "Pass"));

//        12) print selected columns
//        testTable.selectColumnsAt(4, 5, 3).show();
//        selectedColumnsTable = testTable.selectColumnsAt(4, 5, 3);
//        System.out.println("identity test for selectColumnsAt(indices): " + (testTable.equals(selectedColumnsTable) ? "Fail" : "Pass"));

        Table sortedTable;

//        13) Sort the table by the key column (index 5), ascending (True), nulls last (False).
//        testTable.sort(5, true, false).show();
//        sortedTable = testTable.sort(5, true, false);
//        System.out.println("identity test for sort(index, asc, nullOrder): " + (!testTable.equals(sortedTable) ? "Fail" : "Pass"));

//        14) Sort the table by the key column (index 5), descending (False), nulls first (True).
//        Database.sort(testTable, 5, false, true).show();
//        sortedTable = Database.sort(testTable, 5, false, true);
//        System.out.println("identity test for Database.sort(index, asc, nullOrder): " + (testTable.equals(sortedTable) ? "Fail" : "Pass"));

        Table rightTable = authors;

//        15) cross join
//        Table crossJoined = testTable.crossJoin(rightTable);
//        crossJoined.show();

//        16) inner join
//        Table innerJoined = testTable.innerJoin(rightTable, List.of(new JoinColumn("author_id", "id")));
//        innerJoined.show();

        rightTable = translators;

//        17) outer join
//        Table outerJoined = testTable.outerJoin(rightTable, List.of(new JoinColumn("translator_id", "id")));
//        outerJoined.show();

//        18) full outer join
//        Table fullOuterJoined = testTable.fullOuterJoin(rightTable, List.of(new JoinColumn("translator_id", "id")));
//        fullOuterJoined.show();

//        19) Return rows that satisfy the condition.
//        testTable.selectRowsBy("title", (String x) -> x.contains("Your")).show();
//        testTable.selectRowsBy("author_id", (Integer x) -> x < 15).show();
//        testTable.selectRowsBy("title", (String x) -> x.length() < 8).show();
//        testTable.selectRowsBy("translator_id", (Object x) -> x == null).show();
//
//        ****************************** test for Column ******************************
        int selectedColumnIndex;
        int selectedRowIndex;
        String selectedColumnName;

//        20) Compare before and after calling setValue(int index, int value) or setValue(int index, String value)
//        System.out.println("*** before setValue ***");
//        selectedColumnIndex = (int) (Math.random() * testTable.getColumnCount());
//        selectedRowIndex = (int) (Math.random() * testTable.getColumn(selectedColumnIndex).count());
//        selectedColumnName = testTable.getColumn(selectedColumnIndex).getHeader();
//        System.out.println("Selected Column: " + selectedColumnName);
//        testTable.selectRowsAt(selectedRowIndex).show();
//        testTable.describe();
//        if (testTable.getColumn(selectedColumnIndex).isNumericColumn())
//            testTable.getColumn(selectedColumnName).setValue(selectedRowIndex, "Sample");
//        else
//            testTable.getColumn(selectedColumnName).setValue(selectedRowIndex, "2023");
//        System.out.println("Column " + selectedColumnName + " has been changed");
//        System.out.println("*** after setValue ***");
//        testTable.selectRowsAt(selectedRowIndex).show();
//        testTable.describe();

//        21) Compare before and after calling T getValue(int index, Class<T> t) or String getValue(int index)
        System.out.println("*** before getValue ***");
        selectedColumnIndex = (int) (Math.random() * testTable.getColumnCount());
        selectedRowIndex = (int) (Math.random() * testTable.getColumn(selectedColumnIndex).count());
        selectedColumnName = testTable.getColumn(selectedColumnIndex).getHeader();
        System.out.println("Selected Column: " + selectedColumnName);
        testTable.selectRowsAt(selectedRowIndex).show();
        if (testTable.getColumn(selectedColumnIndex).isNumericColumn()) {
            // if cell is null, exception
            double value = testTable.getColumn(selectedColumnName).getValue(selectedRowIndex, Double.class);
            System.out.println("The numeric value in (" + selectedRowIndex + ", " + selectedColumnIndex + ") is " + value);
        } else {
            String value = testTable.getColumn(selectedColumnName).getValue(selectedRowIndex);
            System.out.println("The string value in (" + selectedRowIndex + ", " + selectedColumnIndex + ") is " + value);
        }
    }
}
