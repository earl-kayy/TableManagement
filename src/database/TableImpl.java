package database;
import java.io.File;
import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.function.Predicate;

class TableImpl implements Table{
    private File file;
    private String fileName;
    private int columnLen=0;
    private final ArrayList<Column> columns = new ArrayList<>();
    TableImpl(File csv){
        file = csv;
        fileName = csv.getName();
        int columnCount=0;
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            String[] rowNum = br.readLine().split(",");
            columnCount = rowNum.length;
            while(br.readLine() != null){
                columnLen += 1;
            }
        }
        catch(IOException e){
            System.out.println("존재하지 않는 파일입니다.");
        }
        for(int i=0; i<columnCount; i++) {
            Column column = new ColumnImpl(csv, i);
            columns.add(column);
        }
    }
    TableImpl(File csv, int row, String reverse){
        int columnCount=0;
        file = csv;
        fileName = csv.getName();
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            String[] rowNum = br.readLine().split(",");
            columnCount = rowNum.length;
            while(br.readLine() != null){
                columnLen += 1;
            }
        }
        catch(IOException e){
            System.out.println("존재하지 않는 파일입니다.");
        }
        for (int i = 0; i < columnCount; i++) {
            Column column = new ColumnImpl(csv, i, row, reverse);
            columns.add(column);
        }
        columnLen = row;
    }
    TableImpl(File csv, int beginIndex, int endIndex){
        int columnCount=0;
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            String[] rowNum = br.readLine().split(",");
            columnCount = rowNum.length;
            while(br.readLine() != null){
                columnLen += 1;
            }
        }
        catch(IOException e){
            System.out.println("존재하지 않는 파일입니다.");
        }
        for(int i=0; i<columnCount; i++) {
            Column column = new ColumnImpl(csv, i, beginIndex, endIndex);
            columns.add(column);
        }
        columnLen = endIndex-beginIndex;
    }
    TableImpl(File csv, Table table, int[] indices){
        int columnCount=0;
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            String[] rowNum = br.readLine().split(",");
            columnCount = rowNum.length;
            while(br.readLine() != null){
                columnLen += 1;
            }
        }
        catch(IOException e){
            System.out.println("존재하지 않는 파일입니다.");
        }
        for(int i=0; i<columnCount; i++) {
            Column column = new ColumnImpl(i, table.getColumn(i), indices);
            columns.add(column);
        }
        columnLen = indices.length + 1;
    }
    TableImpl(File csv, int beginIndex, int endIndex, String col){
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            while(br.readLine() != null){
                columnLen += 1;
            }
            columnLen--;
        }
        catch(IOException e){
            System.out.println("존재하지 않는 파일입니다.");
        }
        for(int i=beginIndex; i<endIndex; i++) {
            Column column = new ColumnImpl(csv, i);
            columns.add(column);
        }
    }
    TableImpl(File csv, int[] indices, String col){
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            while(br.readLine() != null){
                columnLen += 1;
            }
            columnLen--;
        }
        catch(IOException e){
            System.out.println("존재하지 않는 파일입니다.");
        }
        for(int index: indices){
            Column column = new ColumnImpl(csv, index);
            columns.add(column);
        }
    }
    TableImpl(File csv, Table table, int leftRowCount, int rightRowCount){
        file = csv;
        fileName = csv.getName();
        int columnCount=0;
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            String[] rowNum = br.readLine().split(",");
            columnCount = rowNum.length;
        }
        catch(IOException e){
            System.out.println("존재하지 않는 파일입니다.");
        }
        for(int i=0; i<columnCount; i++) {
            Column column = new ColumnImpl(csv, i, rightRowCount);
            columns.add(column);
        }
        for(int i=0; i<table.getColumnCount(); i++) {
            int row = table.getRowCount();
            for (int j = 0; j < leftRowCount; j++) {
                for (int k = 1; k <=rightRowCount; k++) {
                    row++;
                    table.getColumn(i).setValue(row-1, table.getColumn(i).getValue(k-1));
                }
            }
            columns.add(table.getColumn(i));
        }
        for(int j=0; j<columns.size(); j++){
            if(j<columnCount)
                this.getColumn(j).setValue(-1, this.getName()+"."+this.getColumn(j).getValue(-1));
            else
                this.getColumn(j).setValue(-1, table.getName()+"."+this.getColumn(j).getValue(-1));
        }
        columnLen = leftRowCount*rightRowCount;
    }
    TableImpl(File csv, Table leftTable, Table rightTable, List<JoinColumn> joinColumns){
        String leftString, rightString;
        ArrayList<Integer> leftIndex = new ArrayList<>();
        ArrayList<Integer> rightIndex = new ArrayList<>();
        for (int i = 1; i <= leftTable.getRowCount(); i++) {
            for (int j = 1; j <= rightTable.getRowCount(); j++) {
                int signal = 0;

                for(int w=0; w<joinColumns.size(); w++) {
                    leftString = joinColumns.get(w).getColumnOfThisTable();
                    rightString = joinColumns.get(w).getColumnOfAnotherTable();
                    if (leftTable.getColumn(leftString).getValue(i-1).equals(rightTable.getColumn(rightString).getValue(j-1))&&!leftTable.getColumn(leftString).getValue(i-1).equals("Null"))
                        signal++;
                }
                if(signal == joinColumns.size()) {
                    leftIndex.add(i);
                    rightIndex.add(j);
                }
            }
        }
        int[] leftIndexArr = new int[leftIndex.size()];
        int[] rightIndexArr = new int[rightIndex.size()];
        for(int i=0; i<leftIndex.size(); i++) {
            leftIndexArr[i] = leftIndex.get(i)-1;
            rightIndexArr[i] = rightIndex.get(i)-1;
        }
        for(int i=0; i< leftTable.getColumnCount()+rightTable.getColumnCount(); i++){
            if(i<leftTable.getColumnCount()){
                Column column = new ColumnImpl(csv, i, leftIndexArr);
                columns.add(column);
            }
            else{
                Table tmpTable = rightTable.selectRowsAt(rightIndexArr);
                columns.add(tmpTable.getColumn(i-leftTable.getColumnCount()));
            }
        }
        for(int j=0; j<columns.size(); j++){
            if(j<leftTable.getColumnCount())
                this.getColumn(j).setValue(-1, leftTable.getName()+"."+this.getColumn(j).getValue(-1));
            else
                this.getColumn(j).setValue(-1, rightTable.getName()+"."+this.getColumn(j).getValue(-1));
        }
        columnLen = leftIndexArr.length;
    }
    TableImpl(File csv, Table leftTable, Table rightTable, List<JoinColumn> joinColumns, boolean full){
        String leftString, rightString;
        ArrayList<Integer> leftIndex = new ArrayList<>();
        ArrayList<Integer> rightIndex = new ArrayList<>();
        for (int i = 1; i <= leftTable.getRowCount(); i++) {
            for (int j = 1; j <= rightTable.getRowCount(); j++) {
                int signal = 0;
                for(int w=0; w<joinColumns.size(); w++) {
                    leftString = joinColumns.get(w).getColumnOfThisTable();
                    rightString = joinColumns.get(w).getColumnOfAnotherTable();
                    if (leftTable.getColumn(leftString).getValue(i-1).equals(rightTable.getColumn(rightString).getValue(j-1))&&!leftTable.getColumn(leftString).getValue(i-1).equals("Null"))
                        signal++;
                }
                if(signal == joinColumns.size()) {
                    leftIndex.add(i);
                    rightIndex.add(j);
                }
            }
        }
        int[] leftIndexArr = new int[leftIndex.size()];
        int[] rightIndexArr = new int[rightIndex.size()];
        for(int i=0; i<leftIndex.size(); i++) {
            leftIndexArr[i] = leftIndex.get(i)-1;
            rightIndexArr[i] = rightIndex.get(i)-1;
        }
        for(int i=0; i< leftTable.getColumnCount()+rightTable.getColumnCount(); i++){
            if(i<leftTable.getColumnCount()){
                Column column = new ColumnImpl(csv, i, leftIndexArr);
                columns.add(column);
            }
            else{
                Table tmpTable = rightTable.selectRowsAt(rightIndexArr);
                columns.add(tmpTable.getColumn(i-leftTable.getColumnCount()));
            }
        }
        int index = 0;
        int[] remainIndex = new int[leftTable.getRowCount()-leftIndexArr.length];
        for(int i=0; i<leftTable.getRowCount(); i++){
            int count = 0;
            for(int j=0; j<leftIndexArr.length; j++){
                if(i != leftIndexArr[j])
                    count++;
            }
            if(count == leftIndexArr.length){
                remainIndex[index] = i;
                index++;
            }
        }

        for(int i=0; i<leftTable.getColumnCount(); i++){
            int row = leftIndexArr.length;
            for(int j=0; j<remainIndex.length; j++) {
                row++;
                this.getColumn(i).setValue(row-1, leftTable.getColumn(i).getValue(remainIndex[j]));
            }
        }
        for(int i=leftTable.getColumnCount(); i<this.getColumnCount(); i++){
            int row = rightIndexArr.length;
            for(int j=0; j<remainIndex.length; j++){
                row++;
                this.getColumn(i).setValue(row-1, "Null");
            }
        }
        columnLen = leftTable.getRowCount();

        if(full == true){
            int indexR = 0;
            int[] remainIndexR = new int[rightTable.getRowCount()-rightIndexArr.length];
            for(int i=0; i<rightTable.getRowCount(); i++){
                int count = 0;
                for(int j=0; j<rightIndexArr.length; j++){
                    if(i != rightIndexArr[j])
                        count++;
                }
                if(count == rightIndexArr.length){
                    remainIndexR[indexR] = i;
                    indexR++;
                }
            }
            for(int i=0; i<leftTable.getColumnCount(); i++){
                int row = leftTable.getRowCount();
                for(int j=0; j<remainIndexR.length; j++){
                    row++;
                    this.getColumn(i).setValue(row-1, "Null");
                }
            }
            for(int i=leftTable.getColumnCount(); i<this.getColumnCount(); i++){
                int row = leftTable.getRowCount();
                for(int j=0; j<remainIndexR.length; j++) {
                    row++;
                    this.getColumn(i).setValue(row-1, rightTable.getColumn(i-leftTable.getColumnCount()).getValue(remainIndexR[j]));
                }
            }
            columnLen = leftTable.getRowCount() + rightTable.getRowCount() - leftIndexArr.length;
        }
        for(int j=0; j<columns.size(); j++){
            if(j<leftTable.getColumnCount())
                this.getColumn(j).setValue(-1, leftTable.getName()+"."+this.getColumn(j).getValue(-1));
            else
                this.getColumn(j).setValue(-1, rightTable.getName()+"."+this.getColumn(j).getValue(-1));
        }
    }
    <T> TableImpl(File csv, Table table, String columnName, Predicate<T> predicate){

        String realType = "";
        for(int i=1; i<=table.getRowCount(); i++){
            if(!table.getColumn(columnName).getValue(i-1).equals("Null")) {
                realType = realDataType(table.getColumn(columnName).getValue(i-1));
                break;
            }
        }
        ArrayList<Object> forInt = new ArrayList<>();
        ArrayList<Object> forString = new ArrayList<>();
        ArrayList<Integer> passIndex = new ArrayList<>();
        if(realType.equals("int")){//기준 열이 int 형일 때
            for(int i=1; i<=table.getRowCount(); i++){
                if(table.getColumn(columnName).getValue(i-1).equals("Null"))//검사 통해 Null이면
                    forInt.add(null);
                else
                    forInt.add(Integer.parseInt(table.getColumn(columnName).getValue(i-1)));//검사 했는데 null 아니면
            }
            for(int i=0; i<forInt.size(); i++) {
                boolean test = predicate.test((T)forInt.get(i));
                if(test == true)
                    passIndex.add(i);
            }
        }
        else{
            for(int i=1; i<=table.getRowCount(); i++){
                if(table.getColumn(columnName).getValue(i-1).equals("Null"))
                    forString.add(null);
                else
                    forString.add(table.getColumn(columnName).getValue(i-1));
            }
            for(int i=0; i<forString.size(); i++) {
                boolean test = predicate.test((T)forString.get(i));
                if(test == true)
                    passIndex.add(i);
            }
        }
        int[] passIndexArr = new int[passIndex.size()];
        for(int i=0; i<passIndex.size(); i++)
            passIndexArr[i] = passIndex.get(i);
        Table predicateTable = table.selectRowsAt(passIndexArr);
        for(int i=0; i<predicateTable.getColumnCount(); i++){
            columns.add(predicateTable.getColumn(i));
        }
        columnLen = passIndexArr.length + 1;
    }
    @Override
    public String getName(){
        return fileName.substring(0, fileName.lastIndexOf("."));
    }
    @Override
    public void show(){
        for(Column c: columns)
            System.out.printf("%20s |", c.getHeader());
        System.out.println();
        for(int i=1; i<=this.getRowCount(); i++){
            for(int j=0; j<this.getColumnCount(); j++){
                try {
                    System.out.printf("%20s |", this.getColumn(j).getValue(i-1));
                }
                catch(IndexOutOfBoundsException e){
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    @Override
    public String toString(){
        return "<" + getClass().getInterfaces()[0].getName() + "@" + Integer.toHexString(hashCode()) + ">";
    }
    private static String realDataType(String value){
        try{
            Integer.parseInt(value);
            return "int";
        }catch(NumberFormatException e){
            return "String";
        }
    }
    @Override
    public void describe(){
        System.out.println(toString());
        System.out.printf("RangeIndex : %d entries, 0 to %d\n",columnLen, columnLen-1);
        System.out.printf("Data columns (total %d columns): \n", columns.size());
        int intCount = 0;
        System.out.printf("# |%15s |%15s |%-8s\n", "Column", "Non-Null Count", "Dtype");
        for(int i=0; i<columns.size(); i++){
            String type = "";
            int count = 0;
            int typeCount = 0;
            for(int j=1; j<=this.getRowCount(); j++){
                if(!this.getColumn(i).getValue(j-1).equals("Null")){
                    type = realDataType(getColumn(i).getValue(j-1));
                    if(type.equals("int"))
                        typeCount++;
                }
            }
            if(type.equals("int")) {
                if ((columns.get(i).count()-(int)columns.get(i).getNullCount()) != typeCount)
                    type = "String";
                else {
                    type = "int";
                    intCount++;
                }
            }
            System.out.printf("%d |%15s |%6d non-null |%-8s\n", i, columns.get(i).getHeader(), columns.get(i).count()-(int)columns.get(i).getNullCount(), type);
        }
        System.out.printf("dtypes: int(%d), String(%d)\n", intCount, columns.size()-intCount);
    }
    @Override
    public Table head(){//testTable.head().show()했을 때 결과
        Table tmpTable = new TableImpl(this.file, 5, "N");
        return tmpTable;
    }
    @Override
    public Table head(int lineCount){
        if(lineCount>this.getRowCount())
            lineCount = this.getRowCount();
        Table tmpTable = new TableImpl(this.file, lineCount, "N");
        return tmpTable;
    }
    @Override
    public Table tail(){
        Table tmpTable = new TableImpl(this.file, 5, "Y");
        return tmpTable;
    }
    @Override
    public Table tail(int lineCount){
        if(lineCount>this.getRowCount())
            lineCount = this.getRowCount();
        Table tmpTable = new TableImpl(this.file, lineCount, "Y");
        return tmpTable;
    }
    @Override
    public Table selectRows(int beginIndex, int endIndex){
        Table tmpTable = new TableImpl(this.file, beginIndex, endIndex);
        return tmpTable;
    }
    @Override
    public Table selectRowsAt(int ...indices){
        Table tmpTable = new TableImpl(this.file, this, indices);
        return tmpTable;
    }
    @Override
    public Table selectColumns(int beginIndex, int endIndex){
        Table tmpTable = new TableImpl(this.file, beginIndex, endIndex, "col");
        return tmpTable;
    }
    @Override
    public Table selectColumnsAt(int ...indices){
        Table tmpTable = new TableImpl(this.file, indices, "col");
        return tmpTable;
    }
    @Override
    public Column getColumn(int index){//원본에 대해 수정 가하고 싶을 때
        return this.columns.get(index);
    }
    @Override
    public Column getColumn(String name){
        for(Column c: columns){
            if (c.getHeader().equals(name))
                return c;
        }
        return null;
    }
    @Override
    public Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst){
        int begin=1, end = this.getRowCount();
        if(isNullFirst == true) {
            int writeIndex=0;
            for (int i = 1; i <= this.getRowCount(); i++) {
                if (this.getColumn(byIndexOfColumn).getValue(i-1).equals("Null")) {
                    writeIndex++;
                    for (Column c : columns) {
                        String temp = c.getValue(writeIndex-1);
                        c.setValue(writeIndex-1, c.getValue(i-1));
                        c.setValue(i-1, temp);
                    }
                }
            }
        }
        if(isNullFirst == false){
            int writeIndex = this.getRowCount();
            for(int i=this.getRowCount(); i>=1; i--){
                if(this.getColumn(byIndexOfColumn).getValue(i-1).equals("Null")){
                    for(Column c : columns){
                        String temp = c.getValue(writeIndex-1);
                        c.setValue(writeIndex-1, c.getValue(i-1));
                        c.setValue(i-1, temp);
                    }
                    writeIndex--;
                }
            }
        }
        if(isAscending == true){
            for(int i=begin; i<end; i++){
                for(int j=begin; j<=end-i; j++){
                    if(!this.getColumn(byIndexOfColumn).getValue(j-1).equals("Null") && !this.getColumn(byIndexOfColumn).getValue(j).equals("Null")) {
                        if (this.getColumn(byIndexOfColumn).getValue(j-1).compareTo(this.getColumn(byIndexOfColumn).getValue(j)) > 0) {
                            for (int k = 0; k < columns.size(); k++) {
                                String temp = this.getColumn(k).getValue(j-1);
                                this.getColumn(k).setValue(j-1, this.getColumn(k).getValue(j));
                                this.getColumn(k).setValue(j, temp);
                            }
                        }
                    }
                }
            }
        }
        else{//내림차순일때,
            for(int i=begin; i<end; i++){
                for(int j=begin; j<=end-i; j++){
                    if(!this.getColumn(byIndexOfColumn).getValue(j-1).equals("Null") && !this.getColumn(byIndexOfColumn).getValue(j).equals("Null")) {
                        if (this.getColumn(byIndexOfColumn).getValue(j-1).compareTo(this.getColumn(byIndexOfColumn).getValue(j)) < 0) {
                            for (int k = 0; k < columns.size(); k++) {
                                String temp = this.getColumn(k).getValue(j-1);
                                this.getColumn(k).setValue(j-1, this.getColumn(k).getValue(j));
                                this.getColumn(k).setValue(j, temp);
                            }
                        }
                    }
                }
            }
        }
        return this;
    }
    @Override
    public int getRowCount(){
        return columnLen;//헤더 제외
    }
    @Override
    public int getColumnCount(){
        return columns.size();
    }
    @Override
    public Table crossJoin(Table rightTable){
        Table tmpTable = rightTable.head(rightTable.getRowCount());
        Table crossTable = new TableImpl(this.file, tmpTable, this.getRowCount(), tmpTable.getRowCount());
        return crossTable;
    }
    @Override
    public Table innerJoin(Table rightTable, List<JoinColumn> joinColumns){
        Table tmp = new TableImpl(this.file, this, rightTable,joinColumns);
        return tmp;
    }
    @Override
    public Table outerJoin(Table rightTable, List<JoinColumn> joinColumns){
        Table tmp = new TableImpl(this.file, this, rightTable, joinColumns, false);
        return tmp;
    }
    @Override
    public Table fullOuterJoin(Table rightTable, List<JoinColumn> joinColumns){
        Table tmp = new TableImpl(this.file, this, rightTable, joinColumns, true);
        return tmp;
    }
    @Override
    public <T> Table selectRowsBy(String columnName, Predicate<T> predicate){
        Table tmp = new TableImpl(this.file, this, columnName, predicate);
        return tmp;
    }
}
