package Part_5;

import Part_1.AbstractDataFrame;

import java.util.ArrayList;

public class AverageVisitor extends Visitor {
    private float avg = 0;
    private int sum = 0;
    float r = 0;

    /**
     * Sets the label of the column to be treated.
     * @param label The label.
     */
    public AverageVisitor(String label) {setLabel(label);}

    /**
     * Returns the average value of the column.
     * @return The value.
     */
    public float getAverage(){
        return avg;
    }

    /**
     * Implements the behavior of the visitor when visiting.
     * @param df The DataFrame to be visited
     */
    @Override
    public void visit(AbstractDataFrame df) {
        ArrayList<ArrayList<String>> data;
        data = df.getData();
        float aux = 0;
        int col = df.getColByLabel(getLabel());
        int val,j;
        for(j=1;j<(data.get(0).size());j++){
            val = Integer.parseInt(data.get(col).get(j));
            aux += val;
            r++;
        }
        sum += aux;
        avg = sum/r;
    }
}
