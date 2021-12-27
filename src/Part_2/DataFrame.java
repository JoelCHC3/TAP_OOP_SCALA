package Part_2;

import Part_5.Visitor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public interface DataFrame extends Iterable<String> {
    String at (int row, String label);
    String iat (int row, int column);
    int columns ();
    int size ();
    List<String> sort (String label, Comparator<String> comparator);
    List<String> query(Predicate<String> p, String label);
    List<String> query(Predicate<String> p, String label, int id);
    void printDataFrame();
    int printDataFrame(int x);
    Iterator<String> iterator();

    void setColumns(int x);
    void accept(Visitor v);
    List<String> getLabels();
    int getColByLabel(String label);
    List<String> getListCol(int column);
}
