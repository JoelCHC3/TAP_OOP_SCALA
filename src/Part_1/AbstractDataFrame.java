package Part_1;

import Part_5.Visitor;
import Visitor.ScalaVisitor;

import java.util.*;
import java.util.function.Predicate;

public abstract class AbstractDataFrame implements Iterable<String>, Part_2.DataFrame {

    ArrayList<ArrayList<String>> data;  //The information of the source file will be stored in data
    int columns, rows;
    String path;

    //-------------------------
    //--- Structure methods ---
    //-------------------------
    /**
     * Method to open the file and storage the information in the DataFrame. Implemented for each file on its
     * corresponding class.
     * @throws Exception Exception thrown.
     */
    public abstract void openDataFrame() throws Exception;

    /**
     * This method is only used by a CompositeDataFrame, in order to handle the logic relating to the number of columns.
     * @param x The value to be assigned to columns.
     */
    public void setColumns(int x) {
        this.columns = x;
    }

    //-----------------------
    //--- Main operations ---
    //-----------------------
    /**
     * @param row The row where the value is.
     * @param label The label where the value is.
     * @return The value located at a given row and label.
     */
    public String at (int row, String label) {
        return iat(row, getColByLabel(label)-1);
    }

    /**
     * @param row The row where the value is.
     * @param column The column where the value is.
     * @return The value located at a given row and column.
     */
    public String iat (int row, int column) {
        return (data.get(column+1).get(row+1));
    }

    /**
     * @return The columns of the DataFrame.
     */
    public int columns () {return columns;}

    /**
     * @return The size (rows) of the DataFrame.
     */
    public int size () {return rows;}

    /**
     * Makes a copy of a column of the DataFrame and sorts it.
     * @param label The label of the column to be sorted.
     * @param comparator The comparator to be used to sort the column.
     * @return The copy of the column, sorted according to the comparator.
     */
    public List<String> sort (String label, Comparator<String> comparator) {
        List<String> copy = new ArrayList<>();
        int column = getColByLabel(label);
        if(column != -1) {
            for(int i=1; i<this.data.get(column).size(); i++) {
                copy.add(this.data.get(column).get(i));
            }
            copy.sort(comparator);
        } else System.out.println("The label does not exist for this DataFrame");

        return copy;
    }

    /**
     * With streams, a column given by a label is filtered with a predicate to obtain the IDs of the coincidences and
     *  print the rows of each ID calling the method {@link #getRowId(List, int) getRowId}.
     * @param p The predicate used in the filter.
     * @param label The label of the column that has to be treated.
     */
    public List<String> query(Predicate<String> p, String label) {
        List<Integer> l = new ArrayList<>();
        l.add(0);   //ID of the labels row is added to print the row
        int column = getColByLabel(label);
        ArrayList<String> data2 = getListCol(column);  //Copy of the column to be treated. It will have modifications

        data.get(column).remove(0);     //First element (label) is removed to avoid problems
        data.get(column).stream().filter(p).forEach(coincidence -> addId(l, data2, data2.indexOf(coincidence)));
        data.get(column).add(0,label);  //The label is added where it was previously

        return getRowId(l, -1);
    }

    /**
     * It works the same way as the previous method, but it handles de global IDs of the rows, instead of the local ones.
     * @param p The predicate used in the filter.
     * @param label he label of the column that has to be treated.
     * @param id The ID of the last row of the previous file, if there was any.
     */
    public List<String> query(Predicate<String> p, String label, int id) {
        List<Integer> l = new ArrayList<>();
        if(id==0) l.add(0);   //ID of the labels row is added to print the row
        int column = getColByLabel(label);
        ArrayList<String> data2 = getListCol(column); //Copy of the column to be treated. It will have modifications

        data.get(column).remove(0); //First element (label) is removed to avoid problems
        data.get(column).stream().filter(p).forEach(coincidence -> addId(l, data2, data2.indexOf(coincidence)));
        data.get(column).add(0,label);  //The label is added where it was previously

        return getRowId(l, id);
    }

    /**
     * Iterator logic to iterate the DataFrame.
     * @return The iterator.
     */
    public Iterator<String> iterator() {
        int struct_size = (rows+1)*(columns+1);

        return new Iterator<>() {
            int index_iter = data.get(0).size();
            @Override
            public boolean hasNext() {
                return index_iter < (struct_size);
            }

            @Override
            public String next() {
                int i,j;
                i = index_iter / data.get(0).size();
                j = index_iter % data.get(0).size();

                index_iter++;
                return data.get(i).get(j);
            }
        };
    }

    /**
     * Prints the DataFrame. Intented to be used for a unique AbstractDataFrame with its own IDs for each row.
     */
    public void printDataFrame() {
        int i,j;
        StringBuilder sb = new StringBuilder();
        for(j=0;j<(data.get(0).size());j++) {
            sb.delete(0,sb.length());
            sb.append("\t");
            for(i=0;i<(columns+1);i++) {
                sb.append(data.get(i).get(j));
                sb.append(" \t|\t");
            }
            System.out.println(sb);
        }
    }

    /**
     * Prints the DataFrame. Intented to be called by a CompositeDataFrame. For each row it prints the global ID in
     *  the CompositeDataFrame, instead of the local ID from the file.
     * @param x The first row's ID for this file. If this is the first file, x will be 0.
     * @return The first row's ID for the next file.
     */
    public int printDataFrame(int x) {
        int i,j;
        StringBuilder sb = new StringBuilder();
        for(j=1;j<(data.get(0).size());j++) {
            sb.append("\t");
            sb.append(x);
            sb.append(" \t|\t");
            for(i=1;i<(columns+1);i++) {
                sb.append(data.get(i).get(j));
                sb.append(" \t|\t");
            }
            x++;
            System.out.println(sb);
            sb.delete(0,sb.length());
        }
        return x;
    }

    //----------------------------
    //--- Visitor logic method ---
    //----------------------------
    /**
     * Method to accept a Java implemented visitor.
     * @param v The visitor.
     */
    public void accept(Visitor v){v.visit(this);}

    /**
     * Method to accept a Scala implemented visitor.
     * @param v The visitor.
     */
    public void accept(ScalaVisitor v){v.visit(this);}

    //------------------------
    //--- Auxiliar methods ---
    //------------------------
    /**
     * Prints the whole row of every coincidence found in a query, plus the labels row.
     * @param ids List that contains the local IDs of the rows to be printed.
     * @param prev_id The ID of the last row of the previous file, if there was any. In that case, the ID printed for
     *                the rows must be the global one, which would be the local ID + the previous ID. In other case,
     *                the method should be called with -1 as the prev_id.
     */
    public List<String> getRowId(List<Integer> ids, int prev_id){
        int i;
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(Integer id:ids) {   //Every element of ids list is treated
            sb.delete(0,sb.length());
            sb.append("\t");
            for(i=0;i<data.size();i++){
                if(i==0 && id != 0){    //The ID column is being treated (i==0), and the current id is not the labels one (id!=0)
                    if(prev_id == -1) sb.append(data.get(i).get(id));    //There was no previous file. The ID printed
                        // must be the local one.
                    else {  //There was a previous file. The ID printed must the global one.
                        int val = (prev_id + Integer.parseInt(data.get(i).get(id)));
                        sb.append(val);
                    }
                } else sb.append(data.get(i).get(id));  //The column treated is not the IDs one.
                sb.append(" \t|\t");
            }
            list.add(sb.toString());
        }
        return list;
    }

    /**
     * Method used to save the IDs of the coincidences found when working with streams in queries. It saves on a list
     * the ID (the index) that a value has in a list of data. Then the value in the index id of the data is replaced
     * with a "-1" to avoid repeating coincidences.
     * @param l List where the IDs of the coincidences will be saved.
     * @param d List of data where the coincidence has been found.
     * @param id Id of the coincidence in the list d.
     */
    public void addId(List<Integer> l, ArrayList<String> d, Integer id) {
        l.add(id);
        d.add(id,"-1");
        d.remove(id+1);
    }

    /**
     * Returns the data of the DataFrame.
     * @return Arraylist of String ArrayLists containing the data.
     */
    public ArrayList<ArrayList<String>> getData(){return data;}

    /**
     * Makes a copy of a whole column of the DataFrame.
     * @param column The desired column.
     * @return The copy of the column.
     */
    public ArrayList<String> getListCol(int column) {
        ArrayList<String> copy = new ArrayList<>();
        int i;
        for(i=0;i<data.get(column).size();i++){
            copy.add(data.get(column).get(i));
        }
        return copy;
    }

    /**
     * Finds the corresponding column of a certain label.
     * @param label The label which column is to be found.
     * @return The column number. -1 If the label does not exist.
     */
    public int getColByLabel(String label) {
        int column = -1;
        for(int i=0;i<(columns+1);i++) {
            if(data.get(i).get(0).compareTo(label) == 0) {
                column = i;
            }
        }
        return column;
    }

    /**
     * Returns the labels of the DataFrame
     * @return List with the labels.
     */
    public List<String> getLabels() {
        List<String> list = new ArrayList<>();
        for(int i=1;i<(columns+1);i++) {
            list.add(data.get(i).get(0));
        }
        return list;
    }
}

