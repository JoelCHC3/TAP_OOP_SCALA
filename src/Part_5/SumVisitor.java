package Part_5;

import Part_1.AbstractDataFrame;

import java.util.ArrayList;

public class SumVisitor extends Visitor {
    private int sum = 0;

    /**
     * Sets the label of the column to be treated.
     * @param label The label.
     */
    public SumVisitor(String label) {setLabel(label);}

    /**
     * Returns the sum value of the column.
     * @return The value.
     */
    public int getSum(){
        return sum;
    }

    /**
     * Implements the behavior of the visitor when visiting.
     * @param df The DataFrame to be visited
     */
    @Override
    public void visit(AbstractDataFrame df) {
        ArrayList<ArrayList<String>> data;
        data = df.getData();
        int aux = 0;
        int col = df.getColByLabel(getLabel());
        int val,j;
        for(j=1;j<(data.get(0).size());j++){
            val = Integer.parseInt(data.get(col).get(j));
            aux += val;
        }
        sum += aux;
    }
}
