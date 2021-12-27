package Part_5;

import Part_1.*;

import java.util.ArrayList;

public class MaxVisitor extends Visitor {
    private int maximum = 0;

    /**
     * Sets the label of the column to be treated.
     * @param label The label.
     */
    public MaxVisitor(String label) {setLabel(label);}

    /**
     * Returns the maximum value of the column.
     * @return The value.
     */
    public int getMaximum(){
        return maximum;
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
            if(val > maximum) maximum = val;
        }
    }
}
