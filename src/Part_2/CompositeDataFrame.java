package Part_2;

import Part_5.Visitor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositeDataFrame implements DataFrame {

    private final List<DataFrame> children;
    int columns;

    //-------------------------
    //--- Structure methods ---
    //-------------------------
    /**
     * This method is used in order to handle the logic relating to the number of columns.
     * @param x The value to be assigned to columns.
     */
    public void setColumns(int x) {
        this.columns = x;
    }

    /**
     * Constructor. Initializes the children list and sets columns to -1.
     * Columns being -1 points out that the number of columns is not yet defined.
     */
    public CompositeDataFrame() {
        children = new LinkedList<>(); columns = -1;
    }

    /**
     * Adds a DataFrame (child) to the list of children if it has a correct number of columns.
     * @param child The DataFrame to be added.
     * @throws ColumnException The number of columns of the child does not correspond the columns value of the
     * CompositeDataFrame.
     */
    public void addChild(DataFrame child) throws ColumnException {
        if(columns == -1) {     //The number of columns has not been defined yet.
            children.add(child);
            if(child.columns() != -1) this.columns = child.columns(); //The number of columns is set to be the number of
            //    columns of the first child.
        } else if (child.columns() == -1) { //The number of columns is already defined, but the new child is a
            //  CompositeDataFrame with the columns not defined.
            children.add(child);
            child.setColumns(this.columns);
        } else if(child.columns() == this.columns) { //The number of columns is already defined, and it is the same
            // as the child's number, so it can be added without modifications.
            children.add(child);
        } else {    //The number of columns is already defined, but the child's number of columns is different.
            throw new ColumnException("Error adding children: ");
        }
    }

    /**
     * Removes a child from the list.
     * @param child The child to be removed.
     */
    public void removeChild(DataFrame child) {children.remove(child);}

    /**
     * @return The List of children.
     */
    public List<DataFrame> getChildren(){return children;}


    //-----------------------
    //--- Main operations ---
    //-----------------------
    /**
     * It calls the method at respect the global amount of data.
     * @param row The global row where the value is.
     * @param label The label where the value is.
     * @return The value.
     */
    public String at (int row, String label) {
        return iat(row,getColByLabel(label)-1);
    }

    /**
     * It calls the method iat respect the global amount of data.
     * @param row The global row where the value is.
     * @param column The column where the value is.
     * @return The value.
     */
    public String iat (int row, int column) {
        int global_size = 0; int prev_size;
        boolean found = false;
        String result = "";
        for(DataFrame child:children) {
            if(!found) {
                prev_size = global_size;
                global_size += child.size();
                if(global_size > row) {
                    found = true;
                    result = child.iat((row - prev_size), column);
                }
            }
        }
        return result;
    }

    /**
     * @return The number of columns of the Composite, which is the children's column number.
     */
    public int columns () {return columns;}

    /**
     * @return The size of the DataFrame, which is the sum of all the children's size.
     */
    public int size () {
        int result = 0;
        for(DataFrame child:children) {
            result += child.size();
        }
        return result;
    }

    /**
     * Makes a list that stores all the values of a given column from each child of the Composite. Then the list is sorted.
     * @param label The label of the column to be sorted.
     * @param comparator The comparator to be used to sort the column.
     * @return The copy containing the column of each DataFrame, sorted according to the comparator.
     */
    public List<String> sort (String label, Comparator<String> comparator) {
        List<String> copy = new ArrayList<>();
        List<String> copy2;
        for(DataFrame child:children) {
            copy2 = child.getListCol(child.getColByLabel(label));
            copy2.remove(0);    //the label is removed
            copy = Stream.concat(copy.stream(),copy2.stream()).collect(Collectors.toList());
        }
        copy.sort(comparator);
        return copy;
    }

    /**
     * It applies a query with a predicate to a determined label of the data. It works with a value to print the
     *  correct global ID for each row.
     * @param p Predicate that applies to the filter in the method.
     * @param label The label to be treated.
     */
    public List<String> query(Predicate<String> p, String label) {
        int id = 0;
        List<List<String>> listOfLists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for(DataFrame child:children) {
            listOfLists.add(child.query(p, label, id));
            id += child.size();
        }
        for (List<String> listOfList : listOfLists) {
            list.addAll(listOfList);
        }
        return list;
    }

    /**
     * This method is called when a Composite makes a query to a child which is also a Composite. It works the same way
     * as {@link #query(Predicate, String) query2}}
     * It applies a query with a predicate to a determined label of the data. It works with a value to print the
     *  correct global ID for each row.
     * @param p Predicate that applies to the filter in the method.
     * @param label The label to be treated.
     */
    public List<String> query(Predicate<String> p, String label, int id) {
        List<List<String>> listOfLists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for(DataFrame child:children) {
            listOfLists.add(child.query(p, label, id));
            id += child.size();
        }
        for (List<String> listOfList : listOfLists) {
            list.addAll(listOfList);
        }
        return list;
    }

    /**
     * Iterator logic to iterate the DataFrame.
     * @return The iterator.
     */
    public Iterator<String> iterator() {
        return new Iterator<>() {
            int j = 0;
            DataFrame currentChild = children.get(j);
            Iterator<String> iter = currentChild.iterator();
            @Override
            public boolean hasNext() {
                if(iter.hasNext()) return true;
                else {
                    j++;
                    if(j >= children.size()) {
                        return false;
                    } else {
                        currentChild = children.get(j);
                        iter = currentChild.iterator();
                        return true;
                    }
                }
            }
            @Override
            public String next() {
                return iter.next();
            }
        };
    }

    /**
     * Iterates over the children.
     */
    public void iterate() {
        for(DataFrame child:children) {
            child.forEach(System.out::println);
        }
    }

    /**
     * Prints the DataFrames contained by the Composite. It initializes a value to work with the global IDs.
     */
    public void printDataFrame() {
        int id = 0;
        System.out.print("\t");
        for(DataFrame child:children) {
            if(id==0) {
                child.getLabels().forEach(e -> System.out.print("\t|\t"+e)); //print the labels row
                System.out.println();
            }
            id = child.printDataFrame(id);
        }
    }

    /**
     * This method is called when a Composite tries to print a child which is also a Composite. It works the same way as
     * {@link #printDataFrame() printDataFrame}
     * @param id The id of the final row of the previous file.
     * @return The first row's ID for the next file.
     */
    public int printDataFrame(int id) {
        for(DataFrame child:children) {
            id = child.printDataFrame(id);
        }
        return id;
    }

    //----------------------------
    //--- Visitor logic method ---
    //----------------------------
    /**
     * Method to accept a Java implemented visitor.
     * @param v The visitor.
     */
    public void accept(Visitor v) {v.visit(this);}

    //------------------------
    //--- Auxiliar methods ---
    //------------------------
    /**
     * Returns the labels of the DataFrame
     * @return List with the labels.
     */
    public List<String> getLabels() {return children.get(0).getLabels();}

    /**
     * Finds the corresponding column of a certain label.
     * @param label The label which column is to be found.
     * @return The column number.
     */
    public int getColByLabel(String label) {return children.get(0).getColByLabel(label);}

    /**
     * Makes a copy of a whole column of the DataFrame.
     * @param column The desired column.
     * @return The copy of the column.
     */
    public List<String> getListCol(int column) {
        List<List<String>> listOfLists = new ArrayList<>();
        List<String> list = new ArrayList<>();

        for(DataFrame child:children) {
            listOfLists.add(child.getListCol(column));
        }

        for(int i=1;i<listOfLists.size();i++){
            listOfLists.get(i).remove(0);   //the label is removed from every list except for the first one
        }

        for (List<String> listOfList : listOfLists) {
            list.addAll(listOfList);
        }

        return list;
    }
}
