package Part_5;

import Part_1.AbstractDataFrame;

import java.util.ArrayList;

public class MinVisitor extends Visitor {
    private int minimum = 1000000000;

    /**
     * Sets the label of the column to be treated.
     * @param label
     */
    public MinVisitor(String label) {this.setLabel(label);}

    /**
     * Returns the minimum value of the column.
     * @return The value.
     */
    public int getMinimum(){
        return minimum;
    }

    /**
     * Implements the behavior of the visitor when visiting.
     * @param df The DataFrame to be visited
     */
    @Override
    public void visit(AbstractDataFrame df) {
        ArrayList<ArrayList<String>> data;
        data = df.getData();
        int col = df.getColByLabel(getLabel());
        int val,j;
        for(j=1;j<(data.get(0).size());j++){
            val = Integer.parseInt(data.get(col).get(j));
            if(val < minimum) minimum = val;
        }
    }
}
