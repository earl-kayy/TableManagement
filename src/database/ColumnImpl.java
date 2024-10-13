package database;
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;

class ColumnImpl implements Column {
    private ArrayList<String> col = new ArrayList<>();
    ColumnImpl(File csv, int i){
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        String temp = line.split(",")[i];
                        col.add(temp);
                    }
                    catch(IndexOutOfBoundsException e){
                        col.add("Null");
                    }
                }
        }
        catch(Exception e){
        }
    }
    ColumnImpl(File csv, int i, int row, String reverse){
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            if(reverse.equals("N")) {
                for (int k = 0; k <= row; k++) {
                    try {
                        col.add(br.readLine().split(",")[i]);
                    }
                    catch(IndexOutOfBoundsException e){
                        col.add("Null");
                    }
                }
            }
            else{//tail()
                ArrayList<String> tmpCol = new ArrayList<>();
                String line="";
                while ((line = br.readLine()) != null) {
                    try {
                        String temp = line.split(",")[i];
                        tmpCol.add(temp);
                    }
                    catch(IndexOutOfBoundsException e){
                        tmpCol.add("Null");
                    }
                }
                col.add(tmpCol.get(0));
                for(int j=tmpCol.size()-row; j<tmpCol.size(); j++){
                    col.add(tmpCol.get(j));
                }
            }
        }
        catch(Exception e){
        }
    }
    ColumnImpl(File csv, int i, int beginIndex, int endIndex){
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            String line;
            ArrayList<String> tmpCol = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                try {
                    String temp = line.split(",")[i];
                    tmpCol.add(temp);
                }
                catch(IndexOutOfBoundsException e){
                    tmpCol.add("Null");
                }
            }
            col.add(tmpCol.get(0));
            for(int j = beginIndex+1; j<=endIndex; j++){
                col.add(tmpCol.get(j));
            }
        }
        catch(IOException e){
        }
    }
    ColumnImpl(File csv, int i, int[] indices){
        try(BufferedReader br = new BufferedReader(new FileReader(csv)))
        {
            ArrayList<String> tmpCol = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String temp = line.split(",")[i];
                    tmpCol.add(temp);
                }
                catch(IndexOutOfBoundsException e){
                    tmpCol.add("Null");
                }
            }
            col.add(tmpCol.get(0));
            for(int index: indices)
                col.add(tmpCol.get(index+1));
        }
        catch(IOException e){
        }
    }
    ColumnImpl(int i, Column c, int[] indices){
        col.add(c.getHeader());
        for(int index: indices){
            col.add(c.getValue(index));
        }
    }
    ColumnImpl(File csv, int i, int rightRowCount){
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            col.add(br.readLine().split(",")[i]);
            String line;
            while ((line = br.readLine())!=null) {
                try {
                    String temp = line.split(",")[i];
                    for (int j = 0; j < rightRowCount; j++)
                        col.add(temp);
                } catch (IndexOutOfBoundsException e) {
                    for (int j = 0; j < rightRowCount; j++)
                        col.add("Null");
                }
            }
        } catch (IOException e) {
        }
    }
    @Override
    public String getHeader(){
        return col.get(0);
    }
    @Override
    public String getValue(int index){
        return col.get(index+1);
    }
    @Override
    public void setValue(int index, String value){
        if(index+1<col.size())
            this.col.set(index+1, value);
        else
            this.col.add(value);
    }
    @Override
    public int count(){
        return col.size()-1;
    }
    @Override
    public void show(){
        for(int i=0; i<col.size(); i++)
            System.out.printf("%15s |", col.get(i));
    }
    @Override
    public boolean isNumericColumn(){
        for(int i=1; i<col.size(); i++){
            if(!col.get(i).equals("Null")){
                try{
                    Integer.parseInt(col.get(i));
                    return true;
                }
                catch(NumberFormatException e){
                    break;
                }
            }
        }
        return false;
    }
    @Override
    public <T extends Number> T getValue(int index, Class<T> t){
        if(this.getValue(index).equals("Null"))
            return null;
        String type = t.getSimpleName();
        if(type.equals("Double")) {
            Double d = Double.valueOf(this.getValue(index));
            return (T)d;
        }
        else if(type.equals("Long")){
            Long l = Long.valueOf(this.getValue(index));
            return (T)l;
        }
        else if(type.equals("Integer")){
            Integer i = Integer.valueOf(this.getValue(index));
            return (T)i;
        }
        return null;
    }
    @Override
    public long getNullCount(){
        long count = 0;
        for(int i=1; i<col.size(); i++){
            if(col.get(i).equals("Null"))
                count++;
        }
        return count;
    }
    @Override
    public void setValue(int index, int value){
        col.set(index+1, Integer.toString(value));
    }
}
