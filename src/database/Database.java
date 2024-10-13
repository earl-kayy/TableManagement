package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Database {
    private static final Set<Table> tables = new HashSet<>();

    public static void showTables() {
        for(Table table:tables){
            System.out.println(table.getName());
        }
    }

    /**
     * Creates a table from a file and adds it to the table.
     *
     * @param csv Assumes the file has a .csv extension.
     *            The file name becomes the table name.
     *            The first row of the CSV file is used as the column names.
     *            Assumes column names are unique.
     *            Determines the column data types as either int or String.
     *            Assumes String data does not contain ("), ('), or (,).
     */

    public static void createTable(File csv) throws FileNotFoundException {
        if(csv.exists()) {
            Table table = new TableImpl(csv);
            tables.add(table);
        }
        else
            throw new FileNotFoundException();
    }

    public static Table getTable(String tableName) {
        for(Table table: tables){
            if(table.getName().equals(tableName))
                return table;
        }
        return null;
    }

    /**
     * @return Returns a new sorted Table object. The original Table passed as the first parameter is not modified.
     * @param byIndexOfColumn The column index to sort by. Throws an exception if the column index is invalid.
     */
    public static Table sort(Table table, int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {
        Table sortedTable = table.head(table.getRowCount());

        int begin=1, end = sortedTable.getRowCount();
        if(isNullFirst == true){
            int writeIndex = 0;
            for(int i=1; i<=sortedTable.getRowCount(); i++){
                if(sortedTable.getColumn(byIndexOfColumn).getValue(i-1).equals("Null")){
                    writeIndex++;
                    for(int j=0; j<sortedTable.getColumnCount(); j++) {
                        String temp = sortedTable.getColumn(j).getValue(writeIndex-1);
                        sortedTable.getColumn(j).setValue(writeIndex-1, sortedTable.getColumn(j).getValue(i-1));
                        sortedTable.getColumn(j).setValue(i-1, temp);
                    }
                }
            }
        }
        if(isNullFirst == false){
            int writeIndex = sortedTable.getRowCount();
            for(int i=sortedTable.getRowCount(); i>=1; i--) {
                if (sortedTable.getColumn(byIndexOfColumn).getValue(i-1).equals("Null")) {
                    for (int j = 0; j < sortedTable.getColumnCount(); j++) {
                        String temp = sortedTable.getColumn(j).getValue(writeIndex-1);
                        sortedTable.getColumn(j).setValue(writeIndex-1, sortedTable.getColumn(j).getValue(i-1));
                        sortedTable.getColumn(j).setValue(i-1, temp);
                    }
                    writeIndex--;
                }
            }
        }
        if(isAscending == true){
            for(int i=begin; i<end; i++){
                for(int j=begin; j<=end-i; j++){
                    if(!sortedTable.getColumn(byIndexOfColumn).getValue(j-1).equals("Null") && !sortedTable.getColumn(byIndexOfColumn).getValue(j).equals("Null")) {
                        if (sortedTable.getColumn(byIndexOfColumn).getValue(j-1).compareTo(sortedTable.getColumn(byIndexOfColumn).getValue(j)) > 0) {
                            for (int k = 0; k < sortedTable.getColumnCount(); k++) {
                                String temp = sortedTable.getColumn(k).getValue(j-1);
                                sortedTable.getColumn(k).setValue(j-1, sortedTable.getColumn(k).getValue(j));
                                sortedTable.getColumn(k).setValue(j, temp);
                            }
                        }
                    }
                }
            }
        }
        else{
            for(int i=begin; i<end; i++){
                for(int j=begin; j<=end-i; j++){
                    if(!sortedTable.getColumn(byIndexOfColumn).getValue(j-1).equals("Null") && !sortedTable.getColumn(byIndexOfColumn).getValue(j).equals("Null")) {
                        if (sortedTable.getColumn(byIndexOfColumn).getValue(j-1).compareTo(sortedTable.getColumn(byIndexOfColumn).getValue(j)) < 0) {
                            for (int k = 0; k < sortedTable.getColumnCount(); k++) {
                                String temp = sortedTable.getColumn(k).getValue(j-1);
                                sortedTable.getColumn(k).setValue(j-1, sortedTable.getColumn(k).getValue(j));
                                sortedTable.getColumn(k).setValue(j, temp);
                            }
                        }
                    }
                }
            }
        }
        return sortedTable;
    }
}
